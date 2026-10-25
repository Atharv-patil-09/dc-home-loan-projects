import java.io.*;
import java.net.*;
import java.util.*;

public class LoanClient {

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("localhost", 5000);

        BufferedReader serverInput =
                new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));

        PrintWriter out =
                new PrintWriter(
                        socket.getOutputStream(), true);

        Scanner sc = new Scanner(System.in);

        System.out.println("================================");
        System.out.println(" DISTRIBUTED HOME LOAN CLIENT");
        System.out.println("================================");

        while (true) {

            System.out.println("\n1. Apply for Home Loan");
            System.out.println("2. Check Loan Status");
            System.out.println("3. View Loan Details");
            System.out.println("4. Exit");

            System.out.print("Enter choice: ");

            int choice = Integer.parseInt(sc.nextLine());

            if (choice == 1) {

                System.out.print("Customer ID: ");
                String customerId = sc.nextLine();

                System.out.print("Customer Name: ");
                String customerName = sc.nextLine();

                System.out.print("Loan Amount: ");
                double amount = Double.parseDouble(sc.nextLine());

                System.out.print("Loan Tenure (years): ");
                int tenure = Integer.parseInt(sc.nextLine());

                System.out.print("Interest Rate (%): ");
                double interest = Double.parseDouble(sc.nextLine());

                String request =
                        "SUBMIT|"
                        + customerId + "|"
                        + customerName + "|"
                        + amount + "|"
                        + tenure + "|"
                        + interest;

                out.println(request);

                System.out.println("\nServer Response:");
                System.out.println(serverInput.readLine());
            }

            else if (choice == 2) {

                System.out.print("Enter Loan ID: ");
                String loanId = sc.nextLine();

                out.println("STATUS|" + loanId);

                System.out.println("\nServer Response:");
                System.out.println(serverInput.readLine());
            }

            else if (choice == 3) {

                System.out.print("Enter Loan ID: ");
                String loanId = sc.nextLine();

                out.println("DETAILS|" + loanId);

                System.out.println("\nServer Response:");
                System.out.println(serverInput.readLine());
            }

            else if (choice == 4) {

                System.out.println("Exiting Home Loan Client...");
                break;

            }

            else {

                System.out.println("Invalid choice.");

            }
        }

        socket.close();
        sc.close();
    }
}
