import java.util.ArrayList;

public class Leverancier {
    private ArrayList<LeverancierDevice> leverancierDevices = new ArrayList<>();

    public Leverancier(){

        LeverancierDevice firwall1 = new LeverancierDevice("pfSense", "firewall", 99.99, 4000);
        leverancierDevices.add(firwall1); //0

        LeverancierDevice webserver1 = new LeverancierDevice("Hal9001W", "webserver", 80.00, 2200);
        leverancierDevices.add(webserver1); //1
        LeverancierDevice webserver2 = new LeverancierDevice("Hal9002W", "webserver", 90.00, 3200);
        leverancierDevices.add(webserver2); //2
        LeverancierDevice webserver3 = new LeverancierDevice("Hal9003W", "webserver", 95.00, 5100);
        leverancierDevices.add(webserver3); //3

        LeverancierDevice database1 = new LeverancierDevice("Hal9001DB", "database", 90.00, 5100);
        leverancierDevices.add(database1); //4
        LeverancierDevice database2 = new LeverancierDevice("Hal9002DB", "database", 95.00, 7700);
        leverancierDevices.add(database2); //5
        LeverancierDevice database3 = new LeverancierDevice("Hal9003DB", "database", 98.00, 12200);
        leverancierDevices.add(database3); //6


    }

    public ArrayList<LeverancierDevice> getLeverancierDevices() {
        return leverancierDevices;
    }
}
