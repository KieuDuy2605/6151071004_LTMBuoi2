import java.io.*;
import java.net.*;

public class EchoClient {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java EchoClient <host> <port>");
            return;
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);

        try (
            Socket socket = new Socket(host, port);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        ) {
            System.out.println("Connected to server " + host + ":" + port);
            String text;

            while (true) {
                System.out.print("You: ");
                text = console.readLine();
                if (text == null || text.equalsIgnoreCase("exit")) {
                    System.out.println("Disconnected.");
                    break;
                }

                output.println(text);
                String response = input.readLine();
                if (response == null) break;
                System.out.println("Server: " + response);
            }

        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}