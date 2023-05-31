import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

public class Netwerk {
    private final ArrayList<NetwerkDevice> netwerkDevices = new ArrayList<>();

    public Netwerk() {
        NetwerkDevice Webserver1 = new NetwerkDevice("Webserver 1", "192.168.1.101", pingServers("192.168.1.101"));
        NetwerkDevice Webserver2 = new NetwerkDevice("Webserver 2", "192.168.1.105", pingServers("192.168.1.105"));
        NetwerkDevice Database1 = new NetwerkDevice("Database 1", "192.168.1.102", pingServers("192.168.1.102"));
        NetwerkDevice Database2 = new NetwerkDevice("Database 2", "192.168.1.103", pingServers("192.168.1.103"));
        netwerkDevices.add(Webserver2);
        netwerkDevices.add(Webserver1);
        netwerkDevices.add(Database1);
        netwerkDevices.add(Database2);


    }

    private static boolean pingServers(String gekozenServer) { //code voor het pingen van servers en de status online (true) of offline (false)

        try {
            InetAddress inetAddress = InetAddress.getByName(gekozenServer);
            boolean isReachable = inetAddress.isReachable(5000); // Timeout set to 5 seconds
            if (isReachable) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            return false;
        }
    }

    public ArrayList<NetwerkDevice> getDevices() {
        return netwerkDevices;
    }

    public NetwerkDevice getDevice(int entry) {
        return netwerkDevices.get(entry);
    }
}