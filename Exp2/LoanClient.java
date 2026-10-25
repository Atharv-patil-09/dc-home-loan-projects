import java.io.*;
import java.net.*;
import java.util.*;

public class LoanClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 5000;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            System.out.println("==================================================");
            System.out.println(" MULTITHREADED DISTRIBUTED HOME LOAN CLIENT");
            System.out.println("==================================================");

            // MULTITHREADING: Background listener thread for server responses and real-time broadcasts
            Thread listenerThread = new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        if (serverMessage.startsWith("BROADCAST|")) {
                            System.out.println("\n[REAL-TIME BROADCAST] " + serverMessage.substring(10));
                            System.out.print("Enter choice: ");
                        } else if (serverMessage.startsWith("RESPONSE|")) {
                            System.out.println("\n[SERVER RESPONSE] " + serverMessage.substring(9));
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

            // Main thread: Handles CLI menu and sends requests
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
                    System.out.println("Exiting Distributed Home Loan Client...");
                    socket.close();
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter 1-5.");
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
        System.out.println("5. Exit");
        System.out.print("Enter choice: ");
    }
}
