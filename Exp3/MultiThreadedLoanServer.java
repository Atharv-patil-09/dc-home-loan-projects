import java.io.*;
import java.net.*;
import java.util.*;

public class MultiThreadedLoanServer {

    private static final int PORT = 5000;
    private static Map<PrintWriter, Socket> clientSockets = new HashMap<>();
    private static Map<String, LoanRecord> loanDatabase = new HashMap<>();
    private static int loanCounter = 1001;

    // Helper class for loan records
    static class LoanRecord {
        String loanId;
        String customerId;
        String customerName;
        double totalAmount;
        double remainingBalance;
        int tenureYears;
        double interestRate;
        String status;

        LoanRecord(String loanId, String customerId, String customerName, double amount, int tenureYears, double interestRate) {
            this.loanId = loanId;
            this.customerId = customerId;
            this.customerName = customerName;
            this.totalAmount = amount;
            this.remainingBalance = amount;
            this.tenureYears = tenureYears;
            this.interestRate = interestRate;
            this.status = "UNDER_REVIEW";
        }

        @Override
        public String toString() {
            return "Loan ID: " + loanId +
                   " | Customer: " + customerName +
                   " (ID: " + customerId + ")" +
                   " | Total: ₹" + totalAmount +
                   " | Balance: ₹" + remainingBalance +
                   " | Tenure: " + tenureYears + " yrs" +
                   " | Interest: " + interestRate + "%" +
                   " | Status: " + status;
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("==========================================================================");
        System.out.println(" MULTITHREADED HOME LOAN SERVER WITH CLOCK SYNC ON PORT " + PORT);
        System.out.println(" Algorithms Active: Cristian's (Client-Pull) & Berkeley (Server-Push Daemon)");
        System.out.println(" Waiting for client connections...");
        System.out.println("==========================================================================");

        // --------------------------------------------------------------------------
        // BERKELEY ALGORITHM TIME DAEMON THREAD
        // Periodically polls connected clients, collects timestamps, computes average offset
        // --------------------------------------------------------------------------
        new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(12000); // Poll interval (12 seconds)

                    synchronized (clientSockets) {
                        if (clientSockets.isEmpty()) continue;

                        long serverTime = System.currentTimeMillis();
                        System.out.println("\n[BERKELEY TIME DAEMON] Starting Time Synchronization Pulse...");
                        System.out.println("[BERKELEY TIME DAEMON] Server Time (Master): " + serverTime + " ms");
                        
                        List<PrintWriter> activeWriters = new ArrayList<>(clientSockets.keySet());
                        System.out.println("[BERKELEY TIME DAEMON] Polling " + activeWriters.size() + " active clients for local timestamps...");

                        // Broadcast POLL_TIME to active clients
                        for (PrintWriter writer : activeWriters) {
                            writer.println("POLL_TIME");
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("[BERKELEY ERROR] " + e.getMessage());
            }
        }).start();

        // Server socket accept loop
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("[SERVER LOG] New Client Connected: " + clientSocket.getRemoteSocketAddress());
            new Thread(new ClientHandler(clientSocket)).start();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                synchronized (clientSockets) {
                    clientSockets.put(out, socket);
                }

                String request;
                while ((request = in.readLine()) != null) {
                    if (request.startsWith("TIME_REPLY|")) {
                        // Handle Berkeley Algorithm time reply from client
                        long clientSimulatedTime = Long.parseLong(request.split("\\|")[1]);
                        long serverTime = System.currentTimeMillis();
                        long offset = clientSimulatedTime - serverTime;
                        System.out.println("[BERKELEY DAEMON] Received TIME_REPLY from " + socket.getRemoteSocketAddress() + 
                                           " -> Client Time: " + clientSimulatedTime + " ms | Calculated Offset: " + offset + " ms");
                        
                        // Send recommended offset correction back to client
                        long correction = -offset / 2; // Offset adjustment
                        out.println("BERKELEY_ADJUST|" + correction);
                        continue;
                    }

                    System.out.println("[THREAD " + Thread.currentThread().getId() + "] Request: " + request);
                    String response = processRequest(request);
                    out.println("RESPONSE|" + response);
                }

            } catch (IOException e) {
                System.out.println("[SERVER LOG] Client disconnected: " + socket.getRemoteSocketAddress());
            } finally {
                synchronized (clientSockets) {
                    if (out != null) {
                        clientSockets.remove(out);
                    }
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    // Ignore close exception
                }
            }
        }

        private String processRequest(String request) {
            try {
                String[] data = request.split("\\|");
                String command = data[0];

                // ------------------------------------------------------------------
                // CRISTIAN'S ALGORITHM: Return current server system time in ms
                // ------------------------------------------------------------------
                if (command.equals("SYNC_CRISTIAN")) {
                    long serverTime = System.currentTimeMillis();
                    return String.valueOf(serverTime);
                }
                else if (command.equals("SUBMIT")) {
                    String customerId = data[1];
                    String customerName = data[2];
                    double amount = Double.parseDouble(data[3]);
                    int tenure = Integer.parseInt(data[4]);
                    double interest = Double.parseDouble(data[5]);

                    String loanId;
                    LoanRecord record;

                    synchronized (MultiThreadedLoanServer.class) {
                        loanId = "HL" + loanCounter++;
                        record = new LoanRecord(loanId, customerId, customerName, amount, tenure, interest);
                        loanDatabase.put(loanId, record);
                    }

                    broadcast("[NOTIFICATION] New Loan Application Registered: " + loanId + " by " + customerName);
                    return "Loan Application Submitted Successfully! " + record.toString();
                } 
                else if (command.equals("PAY_EMI")) {
                    String loanId = data[1];
                    double paymentAmount = Double.parseDouble(data[2]);

                    synchronized (MultiThreadedLoanServer.class) {
                        if (!loanDatabase.containsKey(loanId)) {
                            return "ERROR: Loan ID " + loanId + " not found.";
                        }
                        LoanRecord record = loanDatabase.get(loanId);
                        if (record.remainingBalance <= 0) {
                            return "Loan ID " + loanId + " is already FULLY_PAID!";
                        }

                        record.remainingBalance -= paymentAmount;
                        if (record.remainingBalance <= 0) {
                            record.remainingBalance = 0;
                            record.status = "CLOSED_FULLY_PAID";
                        } else {
                            record.status = "ACTIVE_APPROVED";
                        }

                        broadcast("[NOTIFICATION] Payment of ₹" + paymentAmount + " processed for " + loanId + ". Remaining: ₹" + record.remainingBalance);
                        return "Payment Received! Updated Record: " + record.toString();
                    }
                }
                else if (command.equals("STATUS")) {
                    String loanId = data[1];
                    synchronized (MultiThreadedLoanServer.class) {
                        if (loanDatabase.containsKey(loanId)) {
                            LoanRecord record = loanDatabase.get(loanId);
                            return "Loan ID: " + record.loanId + " | Status: " + record.status + " | Balance: ₹" + record.remainingBalance;
                        } else {
                            return "ERROR: Loan ID " + loanId + " not found.";
                        }
                    }
                }
                else if (command.equals("DETAILS")) {
                    String loanId = data[1];
                    synchronized (MultiThreadedLoanServer.class) {
                        if (loanDatabase.containsKey(loanId)) {
                            return loanDatabase.get(loanId).toString();
                        } else {
                            return "ERROR: Loan ID " + loanId + " not found.";
                        }
                    }
                }
                else {
                    return "ERROR: Invalid command.";
                }

            } catch (Exception e) {
                return "ERROR processing request: " + e.getMessage();
            }
        }

        private void broadcast(String message) {
            synchronized (clientSockets) {
                for (PrintWriter writer : clientSockets.keySet()) {
                    writer.println("BROADCAST|" + message);
                }
            }
        }
    }
}
