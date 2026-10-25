// PrimaryBackupLoanServer.java
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class PrimaryBackupLoanServer {
    static final int CLIENT_PORT = 5000;
    static final int REPL_PORT = 6000;
    static final int HEARTBEAT_INTERVAL_MS = 2000;
    static final int HEARTBEAT_TIMEOUT_MS = 6000;

    static volatile String role; // "PRIMARY" or "BACKUP"
    static String backupHost; // used only when role == PRIMARY
    static Map<String, String> loanDatabase = new ConcurrentHashMap<>();
    static int loanCounter = 1001;
    static volatile long lastHeartbeatReceived = System.currentTimeMillis();

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Usage: java PrimaryBackupLoanServer <PRIMARY|BACKUP> [backupHost]");
            return;
        }
        role = args[0].toUpperCase(); // PRIMARY / BACKUP
        backupHost = args.length > 1 ? args[1] : "localhost";

        System.out.println("=================================================");
        System.out.println(" FAULT-TOLERANT HOME LOAN SERVER | ROLE: " + role);
        System.out.println("=================================================");

        if (role.equals("PRIMARY")) {
            new Thread(PrimaryBackupLoanServer::sendHeartbeats).start();
            startClientListener();
        } else {
            new Thread(PrimaryBackupLoanServer::runReplicationListener).start();
            new Thread(PrimaryBackupLoanServer::monitorPrimaryFailure).start();
        }
    }

    // ---------- CLIENT-FACING (active only on the current PRIMARY) ----------
    static void startClientListener() throws IOException {
        ServerSocket clientServerSocket = new ServerSocket(CLIENT_PORT);
        System.out.println("[PRIMARY] Listening for clients on port " + CLIENT_PORT);
        while (true) {
            Socket s = clientServerSocket.accept();
            new Thread(() -> handleClient(s)).start();
        }
    }

    static void handleClient(Socket s) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
             PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {
            String req;
            while ((req = in.readLine()) != null) {
                String response = processRequest(req);
                out.println(response);
                if (req.startsWith("SUBMIT") || req.startsWith("PAY_EMI")) {
                    replicateToBackup(req); // propagate write to backup
                }
            }
        } catch (IOException e) {
            System.out.println("[CLIENT DISCONNECTED] " + e.getMessage());
        }
    }

    static synchronized String processRequest(String req) {
        String[] p = req.split("\\|");
        switch (p[0]) {
            case "SUBMIT":
                String loanId = "HL" + (loanCounter++);
                loanDatabase.put(loanId, req);
                return "Loan Application Submitted | Loan ID: " + loanId;
            case "PAY_EMI":
                String payLoanId = p[1];
                if (loanDatabase.containsKey(payLoanId)) {
                    loanDatabase.put(payLoanId, loanDatabase.get(payLoanId) + " | PAID_EMI:" + p[2]);
                    return "Payment Received for Loan ID: " + payLoanId;
                } else {
                    return "Loan ID not found.";
                }
            case "STATUS":
                return loanDatabase.containsKey(p[1])
                    ? "Loan ID: " + p[1] + " | Status: UNDER_REVIEW"
                    : "Loan ID not found.";
            case "DETAILS":
                return loanDatabase.containsKey(p[1])
                    ? "Loan Details: " + loanDatabase.get(p[1])
                    : "Loan ID not found.";
            default:
                return "Invalid request.";
        }
    }

    // ---------- REPLICATION ----------
    static void replicateToBackup(String req) {
        try (Socket s = new Socket(backupHost, REPL_PORT);
             PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {
            out.println("REPLICATE|" + req);
            System.out.println("[REPLICATED] -> Backup: " + req);
        } catch (IOException e) {
            System.out.println("[REPLICATION FAILED] Backup unreachable: " + e.getMessage());
        }
    }

    static void runReplicationListener() {
        try (ServerSocket repl = new ServerSocket(REPL_PORT)) {
            while (true) {
                Socket s = repl.accept();
                new Thread(() -> {
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()))) {
                        String line = in.readLine();
                        if (line == null) return;
                        if (line.equals("PING")) {
                            lastHeartbeatReceived = System.currentTimeMillis();
                        } else if (line.startsWith("REPLICATE|")) {
                            processRequest(line.substring(10)); // apply same write locally
                            System.out.println("[SYNCED] Backup applied: " + line);
                        }
                    } catch (IOException ignored) {}
                }).start();
            }
        } catch (IOException e) {
            System.out.println("[REPLICATION LISTENER ERROR] " + e.getMessage());
        }
    }

    // ---------- HEARTBEAT ----------
    static void sendHeartbeats() {
        while (true) {
            try (Socket s = new Socket(backupHost, REPL_PORT);
                 PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {
                out.println("PING");
            } catch (IOException ignored) {}
            sleep(HEARTBEAT_INTERVAL_MS);
        }
    }

    static void monitorPrimaryFailure() {
        while (true) {
            sleep(1000);
            long since = System.currentTimeMillis() - lastHeartbeatReceived;
            if (since > HEARTBEAT_TIMEOUT_MS) {
                System.out.println("[FAILOVER] No heartbeat for " + since + "ms -- Primary presumed DOWN.");
                promoteToPrimary();
                return;
            }
        }
    }

    static void promoteToPrimary() {
        role = "PRIMARY";
        System.out.println("=================================================");
        System.out.println(" [PROMOTION] This node is now the ACTIVE PRIMARY ");
        System.out.println(" Loan database entries recovered: " + loanDatabase.size());
        System.out.println("=================================================");
        try {
            startClientListener();
        } catch (IOException e) {
            System.out.println("[PROMOTION FAILED] " + e.getMessage());
        }
    }

    static void sleep(int ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}
