import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class LoanClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 5000;
    
    // Simulate local clock drift (+150ms) for demonstration on localhost
    private static long simulatedDriftMs = 150;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            System.out.println("==========================================================================");
            System.out.println(" MULTITHREADED HOME LOAN CLIENT WITH CLOCK SYNCHRONIZATION");
            System.out.println("==========================================================================");

            // MULTITHREADED BACKGROUND LISTENER THREAD
            // Listens for server responses, real-time broadcasts, and Berkeley Algorithm POLL_TIME prompts
            Thread listenerThread = new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        if (serverMessage.startsWith("POLL_TIME")) {
                            // BERKELEY ALGORITHM RESPONSE
                            long simulatedLocalTime = System.currentTimeMillis() + simulatedDriftMs;
                            System.out.println("\n[BERKELEY DAEMON PULSE] Server polled local time.");
                            System.out.println("[BERKELEY DAEMON PULSE] Sending simulated local time: " + simulatedLocalTime + " ms (Drift: +" + simulatedDriftMs + "ms)");
                            out.println("TIME_REPLY|" + simulatedLocalTime);
                            System.out.print("Enter choice: ");
                        } 
                        else if (serverMessage.startsWith("BERKELEY_ADJUST|")) {
                            long adjustment = Long.parseLong(serverMessage.split("\\|")[1]);
                            simulatedDriftMs += adjustment;
                            System.out.println("\n[BERKELEY DAEMON ADJUST] Received time correction: " + adjustment + " ms. New Adjusted Drift: +" + simulatedDriftMs + "ms");
                            System.out.print("Enter choice: ");
                        }
                        else if (serverMessage.startsWith("BROADCAST|")) {
                            System.out.println("\n[REAL-TIME BROADCAST] " + serverMessage.substring(10));
                            System.out.print("Enter choice: ");
                        } else if (serverMessage.startsWith("RESPONSE|")) {
                            String responseBody = serverMessage.substring(9);
                            // Check if this response is for Cristian's Algorithm
                            try {
                                long tServer = Long.parseLong(responseBody.trim());
                                // Handled in main thread Cristian sync block
                            } catch (NumberFormatException e) {
                                System.out.println("\n[SERVER RESPONSE] " + responseBody);
                            }
                        } else {
                            System.out.println("\n[SERVER MESSAGE] " + serverMessage);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("\n[DISCONNECTED] Server connection closed.");
                }
            });
            listenerThread.setDaemon(true);
            listenerThread.start();

            // Main thread: CLI menu and request handler
            while (true) {
                printMenu();
                String choice = scanner.nextLine().trim();

                if (choice.equals("1")) {
                    System.out.print("Customer ID: ");
                    String customerId = scanner.nextLine().trim();

                    System.out.print("Customer Name: ");
                    String customerName = scanner.nextLine().trim();

                    System.out.print("Loan Amount (₹): ");
                    double amount = Double.parseDouble(scanner.nextLine().trim());

                    System.out.print("Loan Tenure (years): ");
                    int tenure = Integer.parseInt(scanner.nextLine().trim());

                    System.out.print("Interest Rate (%): ");
                    double interest = Double.parseDouble(scanner.nextLine().trim());

                    String request = "SUBMIT|" + customerId + "|" + customerName + "|" + amount + "|" + tenure + "|" + interest;
                    out.println(request);
                    Thread.sleep(300);

                } else if (choice.equals("2")) {
                    System.out.print("Enter Loan ID: ");
                    String loanId = scanner.nextLine().trim();

                    System.out.print("Enter Payment / EMI Amount (₹): ");
                    double amount = Double.parseDouble(scanner.nextLine().trim());

                    String request = "PAY_EMI|" + loanId + "|" + amount;
                    out.println(request);
                    Thread.sleep(300);

                } else if (choice.equals("3")) {
                    System.out.print("Enter Loan ID: ");
                    String loanId = scanner.nextLine().trim();

                    String request = "STATUS|" + loanId;
                    out.println(request);
                    Thread.sleep(300);

                } else if (choice.equals("4")) {
                    System.out.print("Enter Loan ID: ");
                    String loanId = scanner.nextLine().trim();

                    String request = "DETAILS|" + loanId;
                    out.println(request);
                    Thread.sleep(300);

                } else if (choice.equals("5")) {
                    // ------------------------------------------------------------------
                    // CRISTIAN'S ALGORITHM: Client-Pull Clock Synchronization
                    // ------------------------------------------------------------------
                    long t0 = System.currentTimeMillis(); // T0: Send time
                    out.println("SYNC_CRISTIAN");

                    String serverResponse = in.readLine();
                    long t1 = System.currentTimeMillis(); // T1: Receive time

                    if (serverResponse != null && serverResponse.startsWith("RESPONSE|")) {
                        long tServer = Long.parseLong(serverResponse.substring(9).trim());
                        long rtt = t1 - t0;
                        long synchronizedTime = tServer + (rtt / 2);

                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
                        System.out.println("\n--------------------------------------------------------------------------");
                        System.out.println(" CRISTIAN'S ALGORITHM CLOCK SYNCHRONIZATION RESULTS");
                        System.out.println("--------------------------------------------------------------------------");
                        System.out.println("T0 (Client Send Time)          : " + t0 + " ms (" + sdf.format(new Date(t0)) + ")");
                        System.out.println("T_server (Server System Time)   : " + tServer + " ms (" + sdf.format(new Date(tServer)) + ")");
                        System.out.println("T1 (Client Receive Time)       : " + t1 + " ms (" + sdf.format(new Date(t1)) + ")");
                        System.out.println("Round Trip Time (RTT)          : " + rtt + " ms");
                        System.out.println("Network One-Way Delay (RTT / 2): " + (rtt / 2.0) + " ms");
                        System.out.println("SYNCHRONIZED PHYSICAL TIME     : " + synchronizedTime + " ms (" + sdf.format(new Date(synchronizedTime)) + ")");
                        System.out.println("--------------------------------------------------------------------------");
                    }
                    Thread.sleep(300);

                } else if (choice.equals("6")) {
                    System.out.println("Exiting Distributed Home Loan Client...");
                    socket.close();
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter 1-6.");
                }
            }

            scanner.close();

        } catch (Exception e) {
            System.err.println("Error connecting to server: " + e.getMessage());
        }
    }

    private static void printMenu() {
        System.out.println("\n--------------------------------------------------");
        System.out.println("1. Apply for Home Loan (SUBMIT)");
        System.out.println("2. Make EMI / Account Payment (PAY_EMI)");
        System.out.println("3. Check Loan Status (STATUS)");
        System.out.println("4. View Loan Details (DETAILS)");
        System.out.println("5. Synchronize Physical Clock (Cristian's Algorithm)");
        System.out.println("6. Exit");
        System.out.print("Enter choice: ");
    }
}
