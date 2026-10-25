import java.io.*;
import java.net.*;
import java.util.*;

public class LoanServer {

    static Vector<PrintWriter> clients = new Vector<>();
    static int loanCounter = 1001;

    public static void main(String[] args) throws Exception {

        ServerSocket server = new ServerSocket(5000);

        System.out.println("Home Loan Server Started...");
        System.out.println("Waiting for clients...");

        while (true) {

            Socket socket = server.accept();

            System.out.println("New Loan Client Connected");

            ClientHandler handler = new ClientHandler(socket);
            handler.start();
        }
    }

    static class ClientHandler extends Thread {

        Socket socket;
        BufferedReader in;
        PrintWriter out;

        ClientHandler(Socket socket) throws Exception {

            this.socket = socket;

            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(
                    socket.getOutputStream(), true);

            clients.add(out);
        }

        public void run() {

            try {

                String request;

                while ((request = in.readLine()) != null) {

                    System.out.println("Request: " + request);

                    String response = processRequest(request);

                    out.println(response);
                }

            } catch (Exception e) {

                System.out.println("Client disconnected.");

            } finally {

                clients.remove(out);
            }
        }

        private String processRequest(String request) {

            try {

                String[] data = request.split("\\|");

                String operation = data[0];

                // Submit Loan
                if (operation.equals("SUBMIT")) {

                    String customerId = data[1];
                    String customerName = data[2];
                    double amount = Double.parseDouble(data[3]);
                    int tenure = Integer.parseInt(data[4]);
                    double interest = Double.parseDouble(data[5]);

                    String loanId = "HL" + loanCounter++;

                    System.out.println(
                            "New Loan Application: " + loanId);

                    return "Loan Application Submitted | Loan ID: "
                            + loanId
                            + " | Customer: "
                            + customerName
                            + " | Amount: ₹"
                            + amount
                            + " | Tenure: "
                            + tenure
                            + " years"
                            + " | Interest: "
                            + interest
                            + "%"
                            + " | Status: UNDER_REVIEW";
                }

                // Check Loan Status
                else if (operation.equals("STATUS")) {

                    String loanId = data[1];

                    return "Loan ID: "
                            + loanId
                            + " | Status: UNDER_REVIEW";
                }

                // Get Loan Details
                else if (operation.equals("DETAILS")) {

                    String loanId = data[1];

                    return "Loan Details | Loan ID: "
                            + loanId
                            + " | Status: UNDER_REVIEW";
                }

                else {

                    return "Invalid Request";

                }

            } catch (Exception e) {

                return "Error processing loan request";

            }
        }
    }
}
