import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class LoanServer
        extends UnicastRemoteObject
        implements LoanInterface {

    private ArrayList<String> loans =
            new ArrayList<>();

    private int loanCounter = 1001;

    public LoanServer() throws RemoteException {
        super();
    }

    @Override
    public String submitLoan(
            String customerId,
            String customerName,
            double amount,
            int tenure,
            double interestRate)
            throws RemoteException {

        String loanId = "HL" + loanCounter++;

        String loan =
                loanId
                + " | Customer ID: " + customerId
                + " | Customer: " + customerName
                + " | Amount: ₹" + amount
                + " | Tenure: " + tenure + " years"
                + " | Interest: " + interestRate + "%"
                + " | Status: UNDER_REVIEW";

        loans.add(loan);

        System.out.println(
                "New Loan Application Received");

        System.out.println(loan);

        return "Loan Application Submitted\n" + loan;
    }

    @Override
    public String getLoanStatus(
            String loanId)
            throws RemoteException {

        for (String loan : loans) {

            if (loan.startsWith(loanId + " ")) {

                return "Loan ID: "
                        + loanId
                        + " | Status: UNDER_REVIEW";
            }
        }

        return "Loan ID not found.";
    }

    @Override
    public String getLoanDetails(
            String loanId)
            throws RemoteException {

        for (String loan : loans) {

            if (loan.startsWith(loanId + " ")) {

                return loan;
            }
        }

        return "Loan ID not found.";
    }
}
