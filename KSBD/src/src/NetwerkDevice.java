public class NetwerkDevice {
    private String naam;
    private String ipv4;
    private boolean status;

    public NetwerkDevice(String naam, String ipv4, boolean status) {
        this.naam = naam;
        this.ipv4 = ipv4;
        this.status = status;
    }

    public String getNaam() {
        return naam;
    }

    public String getIpv4() {
        return ipv4;
    }

    public String isStatus() {
        if (status) {
            return "Online";
        }
        else {
            return "offline";
        }
    }
}
