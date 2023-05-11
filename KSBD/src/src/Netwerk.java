import java.util.ArrayList;

public class Netwerk {
    private ArrayList<NetwerkDevice> netwerkDevices = new ArrayList<>();

    public Netwerk() {
        NetwerkDevice g1 = new NetwerkDevice("HAll", "192.285.34.248", true);
        NetwerkDevice g2 = new NetwerkDevice("HAll4555", "192.285.34.267", true);
        netwerkDevices.add(g1);
        netwerkDevices.add(g2);
    }

    public ArrayList<NetwerkDevice> getDevices() {
        return netwerkDevices;
    }

    public NetwerkDevice getDevice(int entry){
        return netwerkDevices.get(entry);
    }
}
