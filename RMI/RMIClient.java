import java.rmi.*;
import java.util.*;

public class RMIClient {

    public static void main(String[] args)
            throws Exception {

        LoanInterface loan =
                (LoanInterface) Naming.lookup(
                        "rmi://localhost/LoanService");

        Scanner sc = new Scanner(System.in);

        System.out.println(
                "================================");

        System.out.println(
                " DISTRIBUTED HOME LOAN RMI CLIENT");

        System.out.println(
                "================================");

        while (true) {

            System.out.println("\n1. Apply for Home Loan");
            System.out.println("2. Check Loan Status");
            System.out.println("3. View Loan Details");
            System.out.println("4. Exit");

            System.out.print("Enter choice: ");

            int choice =
                    Integer.parseInt(sc.nextLine());

            if (choice == 1) {

                System.out.print("Customer ID: ");
                String customerId =
                        sc.nextLine();

                System.out.print("Customer Name: ");
                String customerName =
                        sc.nextLine();

                System.out.print("Loan Amount: ");
                double amount =
                        Double.parseDouble(
                                sc.nextLine());

                System.out.print(
                        "Loan Tenure (years): ");

                int tenure =
                        Integer.parseInt(
                                sc.nextLine());

                System.out.print(
                        "Interest Rate (%): ");

                double interest =
                        Double.parseDouble(
                                sc.nextLine());

                String result =
                        loan.submitLoan(
                                customerId,
                                customerName,
                                amount,
                                tenure,
                                interest);

                System.out.println("\nServer Response:");
                System.out.println(result);
            }

            else if (choice == 2) {

                System.out.print(
                        "Enter Loan ID: ");

                String loanId =
                        sc.nextLine();

                String result =
                        loan.getLoanStatus(
                                loanId);

                System.out.println(
                        "\nServer Response:");

                System.out.println(result);
            }

            else if (choice == 3) {

                System.out.print(
                        "Enter Loan ID: ");

                String loanId =
                        sc.nextLine();

                String result =
                        loan.getLoanDetails(
                                loanId);

                System.out.println(
                        "\nServer Response:");

                System.out.println(result);
            }

            else if (choice == 4) {

                System.out.println(
                        "Exiting RMI Client...");

                break;
            }

            else {

                System.out.println(
                        "Invalid choice.");
            }
        }

        sc.close();
    }
}
