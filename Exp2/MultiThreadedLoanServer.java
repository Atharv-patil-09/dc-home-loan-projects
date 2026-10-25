import java.io.*;
import java.net.*;
import java.util.*;

public class MultiThreadedLoanServer {

    private static final int PORT = 5000;
    private static Set<PrintWriter> clientWriters = new HashSet<>();
    private static Map<String, LoanRecord> loanDatabase = new HashMap<>();
    private static int loanCounter = 1001;

    // Helper class for stored loan records
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
        System.out.println("==================================================");
        System.out.println(" Multithreaded Home Loan Server Started on Port " + PORT);
        System.out.println(" Waiting for client connections...");
        System.out.println("==================================================");

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

                synchronized (clientWriters) {
                    clientWriters.add(out);
                }

                String request;
                while ((request = in.readLine()) != null) {
                    System.out.println("[THREAD " + Thread.currentThread().getId() + "] Request: " + request);
                    String response = processRequest(request);
                    out.println("RESPONSE|" + response);
                }

            } catch (IOException e) {
                System.out.println("[SERVER LOG] Client disconnected: " + socket.getRemoteSocketAddress());
            } finally {
                synchronized (clientWriters) {
                    if (out != null) {
                        clientWriters.remove(out);
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

                if (command.equals("SUBMIT")) {
                    String customerId = data[1];
                    String customerName = data[2];
                    double amount = Double.parseDouble(data[3]);
                    int tenure = Integer.parseInt(data[4]);
                    double interest = Double.parseDouble(data[5]);

                    String loanId;
                    LoanRecord record;

                    // CONCURRENCY CONTROL: Synchronize ID generation & DB insertion
                    synchronized (MultiThreadedLoanServer.class) {
                        loanId = "HL" + loanCounter++;
                        record = new LoanRecord(loanId, customerId, customerName, amount, tenure, interest);
                        loanDatabase.put(loanId, record);
                    }

                    // Broadcast notification to all active clients
                    broadcast("[NOTIFICATION] New Loan Application Registered: " + loanId + " by " + customerName);

                    return "Loan Application Submitted Successfully! " + record.toString();
                } 
                else if (command.equals("PAY_EMI")) {
                    String loanId = data[1];
                    double paymentAmount = Double.parseDouble(data[2]);

                    // CONCURRENCY CONTROL: Synchronize transaction processing per loan ID
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
            synchronized (clientWriters) {
                for (PrintWriter writer : clientWriters) {
                    writer.println("BROADCAST|" + message);
                }
            }
        }
    }
}
