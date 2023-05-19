import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.io.*;

public class Functions {

    static ArrayList<Integer> gekozenInt = new ArrayList<Integer>();
    static ArrayList<LeverancierDevice> combo = new ArrayList<>();
    static int numTotal = 0;

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

    public static void calculateServers(ArrayList<LeverancierDevice> leverancierdevices, ArrayList<Integer> gekozenInt, int index, double firewall, double gewensteBeschikbaarheid,
                                        int numWebServers, int numDbServers) {

        double uptimeWebservers = 1;
        double uptimeDatabases = 1;

        if (index == 0) {
            for (int i = 0; i < leverancierdevices.size() - 1; i++) {
                gekozenInt.add(0);
            }
        }


        if (index == leverancierdevices.size() - 1) {
            for (int i = 0; i < gekozenInt.size(); i++) {
                if (leverancierdevices.get(i + 1).getFunctie().equals("webserver")) {
                    double enkel = (Math.pow((1 - (leverancierdevices.get(i + 1).getUptime() / 100)), gekozenInt.get(i)));

                    uptimeWebservers = enkel * uptimeWebservers;
                } else if (leverancierdevices.get(i + 1).getFunctie().equals("database")) {
                    double enkel = (Math.pow((1 - (leverancierdevices.get(i + 1).getUptime() / 100)), gekozenInt.get(i)));

                    uptimeDatabases = enkel * uptimeDatabases;
                }
            }

            double beschikbaarheidWeb = 1 - uptimeWebservers;
            double beschikbaarheidDB = 1 - uptimeDatabases;
            double beschikbaarheidTotaal = beschikbaarheidWeb * beschikbaarheidDB * firewall;


            if (beschikbaarheidTotaal >= gewensteBeschikbaarheid && beschikbaarheidTotaal < gewensteBeschikbaarheid + 0.001 && numDbServers > 0 && numWebServers > 0 && numTotal < 1) { // check if target availability is met

                numTotal++;
                System.out.println("Totale beschikbaarheid: " + (beschikbaarheidTotaal * 100 + "%"));

                for (int i = 0; i < gekozenInt.size() - 1; i++) {
                    if (gekozenInt.get(i) > 0) {
                        for (int p = 1; p <= gekozenInt.get(i); p++) { //checks amount
                            LeverancierDevice device1 = new LeverancierDevice(leverancierdevices.get(i).getNaam(), leverancierdevices.get(i).getFunctie(), leverancierdevices.get(i).getUptime(), leverancierdevices.get(i).getKostenEnkel(), p);
                            combo.add(device1);
                        }
                    }
                }
            }
            return;
        }

        for (int i = 0; i < gekozenInt.size() / 2; i++) {
            gekozenInt.set(index, i);
            if (index < gekozenInt.size() / 2) { // web server
                calculateServers(leverancierdevices, gekozenInt, index + 1, firewall, gewensteBeschikbaarheid, numWebServers, numDbServers);
                numWebServers++;
            } else if (index < gekozenInt.size()) { // database
                calculateServers(leverancierdevices, gekozenInt, index + 1, firewall, gewensteBeschikbaarheid, numWebServers, numDbServers);
                numDbServers++;
            }
        }

    }//function
}//class


