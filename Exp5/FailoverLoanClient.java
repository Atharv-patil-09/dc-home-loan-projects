// FailoverLoanClient.java
import java.io.*;
import java.net.*;
import java.util.*;

public class FailoverLoanClient {
    static final String PRIMARY_HOST = "localhost";
    static final String BACKUP_HOST = "localhost"; // promoted node's host
    static final int PORT = 5000;

    static Socket connectWithFailover() {
        try {
            Socket s = new Socket(PRIMARY_HOST, PORT);
            System.out.println("[CLIENT] Connected to Primary at " + PRIMARY_HOST + ":" + PORT);
            return s;
        } catch (IOException e) {
            System.out.println("[CLIENT] Connection to Primary (" + PRIMARY_HOST + ":" + PORT + ") refused.");
            System.out.println("[CLIENT] Retrying via failover target (former Backup) ...");
            try {
                Socket s = new Socket(BACKUP_HOST, PORT);
                System.out.println("[CLIENT] Connected to new Primary at " + BACKUP_HOST + ":" + PORT);
                return s;
            } catch (IOException ex) {
                throw new RuntimeException("Both Primary and Backup unreachable.", ex);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=============================================================");
        System.out.println(" MULTITHREADED HOME LOAN CLIENT (Failover-enabled)");
        System.out.println("=============================================================");

        while (true) {
            System.out.println("\n1. Apply for Home Loan (SUBMIT)");
            System.out.println("2. Make EMI / Account Payment (PAY_EMI)");
            System.out.println("3. Check Loan Status (STATUS)");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine().trim();
            if (choice.equals("4")) {
                System.out.println("Exiting client.");
                break;
            }

            String req = null;
            if (choice.equals("1")) {
                System.out.print("Customer ID: ");
                String cid = scanner.nextLine().trim();
                System.out.print("Customer Name: ");
                String cname = scanner.nextLine().trim();
                System.out.print("Loan Amount (Rs): ");
                String amount = scanner.nextLine().trim();
                System.out.print("Loan Tenure (years): ");
                String tenure = scanner.nextLine().trim();
                System.out.print("Interest Rate (%): ");
                String rate = scanner.nextLine().trim();
                req = "SUBMIT|" + cid + "|" + cname + "|" + amount + "|" + tenure + "|" + rate;
            } else if (choice.equals("2")) {
                System.out.print("Enter Loan ID: ");
                String lid = scanner.nextLine().trim();
                System.out.print("Enter Payment Amount: ");
                String pamt = scanner.nextLine().trim();
                req = "PAY_EMI|" + lid + "|" + pamt;
            } else if (choice.equals("3")) {
                System.out.print("Enter Loan ID: ");
                String lid = scanner.nextLine().trim();
                req = "STATUS|" + lid;
            } else {
                System.out.println("Invalid choice.");
                continue;
            }

            try (Socket s = connectWithFailover();
                 PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()))) {
                out.println(req);
                String resp = in.readLine();
                System.out.println("\nServer Response: " + resp);
            } catch (Exception e) {
                System.out.println("[ERROR] Failed to communicate: " + e.getMessage());
            }
        }
        scanner.close();
    }
}
