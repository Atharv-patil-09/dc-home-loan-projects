import java.io.*;
import java.net.*;

public class CristianClockServer {

    private static final int PORT = 6000;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("==========================================================================");
            System.out.println(" CRISTIAN'S PHYSICAL CLOCK SYNCHRONIZATION SERVER (Port " + PORT + ")");
            System.out.println(" Central Time Server active... Providing authoritative physical time.");
            System.out.println("==========================================================================");

            while (true) {
                Socket socket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                String request = in.readLine();
                if ("GET_TIME".equals(request)) {
                    long serverTime = System.currentTimeMillis();
                    out.println(serverTime);
                    System.out.printf("[CRISTIAN SERVER] Responded to %s with Server Time: %d ms\n", 
                        socket.getRemoteSocketAddress(), serverTime);
                }
                socket.close();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
}
