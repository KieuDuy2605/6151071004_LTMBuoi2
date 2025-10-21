import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentServer {

    private static final int PORT = 12345; // port server lắng nghe
    private static final int THREAD_POOL_SIZE = 5; // số lượng thread tối đa
    private static AtomicInteger clientCount = new AtomicInteger(0); // số client hiện tại

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server đang chạy trên port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientCount.incrementAndGet();
                System.out.println("Client kết nối: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort() +
                                   " | Số client hiện tại: " + clientCount.get());

                threadPool.submit(() -> handleClient(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String line;
            while ((line = in.readLine()) != null) {
                String response;
                line = line.trim();
                if (line.equalsIgnoreCase("TIME")) {
                    response = new SimpleDateFormat("HH:mm:ss").format(new Date());
                } else if (line.equalsIgnoreCase("COUNT")) {
                    response = String.valueOf(clientCount.get());
                } else if (line.equalsIgnoreCase("QUIT")) {
                    response = "Goodbye!";
                    out.println(response);
                    break; // thoát loop
                } else if (line.toUpperCase().startsWith("ECHO ")) {
                    response = line.substring(5); // bỏ "ECHO "
                } else {
                    response = "Unknown command";
                }
                out.println(response);
            }
        } catch (IOException e) {
            System.out.println("Lỗi client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            int count = clientCount.decrementAndGet();
            System.out.println("Client ngắt kết nối: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort() +
                               " | Số client hiện tại: " + count);
        }
    }
}