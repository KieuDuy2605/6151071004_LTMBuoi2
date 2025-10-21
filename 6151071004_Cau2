import java.net.*;
import java.util.Scanner;

public class IPTool {

    // Chuyển IP -> số nguyên
    public static long toInt(String ip) throws UnknownHostException {
        InetAddress inet = InetAddress.getByName(ip);
        byte[] bytes = inet.getAddress();
        long result = 0;
        for (byte b : bytes) {
            result = (result << 8) | (b & 0xFF);
        }
        return result;
    }

    // Chuyển số nguyên -> IP
    public static String toIP(long value) {
        return String.format("%d.%d.%d.%d",
                (value >> 24) & 0xFF,
                (value >> 16) & 0xFF,
                (value >> 8) & 0xFF,
                value & 0xFF);
    }

    // CIDR -> subnet mask
    public static String maskFromCIDR(int cidr) {
        int mask = 0xFFFFFFFF << (32 - cidr);
        return toIP(mask);
    }

    // Tính địa chỉ mạng
    public static String networkAddress(String ip, int cidr) throws UnknownHostException {
        long ipVal = toInt(ip);
        long mask = 0xFFFFFFFFL << (32 - cidr);
        return toIP(ipVal & mask);
    }

    // Tính broadcast address
    public static String broadcast(String ip, int cidr) throws UnknownHostException {
        long ipVal = toInt(ip);
        long mask = 0xFFFFFFFFL << (32 - cidr);
        long broadcast = ipVal | (~mask & 0xFFFFFFFFL);
        return toIP(broadcast);
    }

    // Kiểm tra cùng subnet
    public static boolean sameSubnet(String ip1, String ip2, int cidr) throws UnknownHostException {
        long mask = 0xFFFFFFFFL << (32 - cidr);
        long net1 = toInt(ip1) & mask;
        long net2 = toInt(ip2) & mask;
        return net1 == net2;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("IPTool ready. Commands:");
        System.out.println("  toInt <ip>");
        System.out.println("  toIP <int>");
        System.out.println("  maskFromCIDR <cidr>");
        System.out.println("  networkAddress <ip> <cidr>");
        System.out.println("  broadcast <ip> <cidr>");
        System.out.println("  sameSubnet <ip1> <ip2> <cidr>");
        System.out.println("  exit");

        while (true) {
            System.out.print("> ");
            String line = sc.nextLine().trim();
            if (line.equalsIgnoreCase("exit")) break;
            String[] parts = line.split("\\s+");

            try {
                switch (parts[0]) {
                    case "toInt":
                        System.out.println(toInt(parts[1]));
                        break;
                    case "toIP":
                        System.out.println(toIP(Long.parseLong(parts[1])));
                        break;
                    case "maskFromCIDR":
                        System.out.println(maskFromCIDR(Integer.parseInt(parts[1])));
                        break;
                    case "networkAddress":
                        System.out.println(networkAddress(parts[1], Integer.parseInt(parts[2])));
                        break;
                    case "broadcast":
                        System.out.println(broadcast(parts[1], Integer.parseInt(parts[2])));
                        break;
                    case "sameSubnet":
                        System.out.println(sameSubnet(parts[1], parts[2], Integer.parseInt(parts[3])));
                        break;
                    default:
                        System.out.println("Unknown command!");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        sc.close();
        System.out.println("Goodbye!");
    }
}
