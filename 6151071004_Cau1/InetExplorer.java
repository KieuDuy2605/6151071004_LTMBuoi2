import java.net.*;
import java.util.Scanner;

public class InetExplorer {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== InetExplorer ===");
        System.out.println("Các lệnh: ");
        System.out.println("  resolve <hostname>");
        System.out.println("  reverse <ip>");
        System.out.println("  local");
        System.out.println("  ping <host/ip> <timeout_ms>");
        System.out.println("  exit");

        while (true) {
            System.out.print("\n> ");
            String line = sc.nextLine().trim();
            if (line.equalsIgnoreCase("exit")) break;
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+");
            String cmd = parts[0].toLowerCase();

            try {
                switch (cmd) {
                    case "resolve":
                        if (parts.length < 2) {
                            System.out.println("Cú pháp: resolve <hostname>");
                            break;
                        }
                        String hostname = parts[1];
                        InetAddress[] addresses = InetAddress.getAllByName(hostname);
                        for (InetAddress addr : addresses) {
                            System.out.println("  " + addr.getHostAddress());
                        }
                        break;

                    case "reverse":
                        if (parts.length < 2) {
                            System.out.println("Cú pháp: reverse <ip>");
                            break;
                        }
                        InetAddress rev = InetAddress.getByName(parts[1]);
                        System.out.println("Hostname: " + rev.getHostName());
                        break;

                    case "local":
                        InetAddress local = InetAddress.getLocalHost();
                        System.out.println("Tên máy: " + local.getHostName());
                        System.out.println("Địa chỉ IP: " + local.getHostAddress());
                        break;

                    case "ping":
                        if (parts.length < 3) {
                            System.out.println("Cú pháp: ping <host/ip> <timeout_ms>");
                            break;
                        }
                        String target = parts[1];
                        int timeout = Integer.parseInt(parts[2]);
                        InetAddress addr = InetAddress.getByName(target);

                        long start = System.currentTimeMillis();
                        boolean reachable = addr.isReachable(timeout);
                        long end = System.currentTimeMillis();

                        if (reachable) {
                            System.out.println("Ping thành công! Thời gian phản hồi: " + (end - start) + " ms");
                        } else {
                            System.out.println("Không ping được (timeout " + timeout + " ms).");
                        }
                        break;

                    default:
                        System.out.println("Lệnh không hợp lệ.");
                }
            } catch (Exception e) {
                System.out.println("Lỗi: " + e.getMessage());
            }
        }

        sc.close();
        System.out.println("Thoát chương trình.");
    }
}
