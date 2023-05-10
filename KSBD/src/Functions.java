import java.util.ArrayList;

public class Functions {

    public static double beschikbaarheidOntwerp(ArrayList<Object> uptimes, boolean w, boolean db) {

        double fragmentA = 1;
        double firewall = 1;

        if (w && db) {
            for (Object uptime : uptimes) {
                double i = ((double) uptime);
                double fragmentB = (1 - (i / 100));

                fragmentA = fragmentB * fragmentA;
                System.out.println("fB" + fragmentB);
            }


            System.out.println("fA" + fragmentA);
            double beschikbaarheidServers = 1 - fragmentA;
            double beschikbaarheidTotaal = (beschikbaarheidServers * firewall) * 100;
            if (beschikbaarheidTotaal != 0) {
                return beschikbaarheidTotaal;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }
}
