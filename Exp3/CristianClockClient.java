import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CristianClockClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 6000;

    public static void main(String[] args) {
        try {
            System.out.println("==========================================================================");
            System.out.println(" CRISTIAN'S PHYSICAL CLOCK SYNCHRONIZATION CLIENT (Banking Branch)");
            System.out.println("==========================================================================");

            // Record client send time T0
            long T0 = System.currentTimeMillis();

            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println("GET_TIME");
            long T_server = Long.parseLong(in.readLine());

            // Record client receive time T1
            long T1 = System.currentTimeMillis();

            // Calculate Round Trip Time (RTT)
            long RTT = T1 - T0;

            // Calculate Synchronized Time using Cristian's formula: T_sync = T_server + (RTT / 2)
            long T_sync = T_server + (RTT / 2);

            // Calculate clock drift/offset between local time and synchronized time
            long drift = T_sync - T1;

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

            System.out.println("T0 (Client Send Request Time)   : " + T0 + " ms (" + sdf.format(new Date(T0)) + ")");
            System.out.println("T_server (Time Server Response) : " + T_server + " ms (" + sdf.format(new Date(T_server)) + ")");
            System.out.println("T1 (Client Receive Time)        : " + T1 + " ms (" + sdf.format(new Date(T1)) + ")");
            System.out.println("Round Trip Time (RTT)           : " + RTT + " ms");
            System.out.println("Network One-Way Delay (RTT / 2) : " + (RTT / 2.0) + " ms");
            System.out.println("--------------------------------------------------------------------------");
            System.out.println("SYNCHRONIZED PHYSICAL TIME      : " + T_sync + " ms (" + sdf.format(new Date(T_sync)) + ")");
            System.out.println("Calculated Clock Drift/Offset   : " + drift + " ms");
            System.out.println("==========================================================================");

            socket.close();

        } catch (Exception e) {
            System.err.println("Error synchronizing clock with server: " + e.getMessage());
        }
    }
}
