import java.rmi.*;
import java.rmi.registry.*;

public class RMIServer {

    public static void main(String[] args)
            throws Exception {

        LocateRegistry.createRegistry(1099);

        LoanInterface loan =
                new LoanServer();

        Naming.rebind(
                "LoanService",
                loan);

        System.out.println(
                "RMI Home Loan Server Started...");

        System.out.println(
                "LoanService is ready.");
    }
}
