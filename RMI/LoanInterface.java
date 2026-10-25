import java.rmi.*;

public interface LoanInterface extends Remote {

    String submitLoan(
            String customerId,
            String customerName,
            double amount,
            int tenure,
            double interestRate
    ) throws RemoteException;

    String getLoanStatus(
            String loanId
    ) throws RemoteException;

    String getLoanDetails(
            String loanId
    ) throws RemoteException;
}
