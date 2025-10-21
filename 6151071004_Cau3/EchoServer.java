import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EchoServer {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java EchoServer <port>");
            return;
        }

        int port = Integer.parseInt(args[0]);
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port " + port + "...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                ) {
                    String line;
                    while ((line = in.readLine()) != null) {
                        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                        String response = time + " - " + line;
                        out.println(response);
                        System.out.println("Echoed: " + response);
                    }
                } catch (IOException e) {
                    System.out.println("Connection error: " + e.getMessage());
                } finally {
                    System.out.println("Client disconnected.");
                    clientSocket.close();
                }
            }

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    System.out.println("Error closing server: " + e.getMessage());
                }
            }
        }
    }
}