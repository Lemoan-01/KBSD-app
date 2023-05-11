import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.io.*;

public class Functions {

    static ArrayList<Integer> gekozenInt = new ArrayList<Integer>();
    static ArrayList<Object> combo = new ArrayList<>();
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

        if (index >= 6) {
            index--;
        } else {
            double fragmentA = 1;


            if (index == 0) {
                for (int i = 0; i < leverancierdevices.size() - 1; i++) {
                    gekozenInt.add(0);
                }
            }


            if (index == leverancierdevices.size() - 1) {
                for (int i = 0; i < gekozenInt.size(); i++) {
                    double fragmentB = (Math.pow((1 - (leverancierdevices.get(i + 1).getUptime() / 100)), gekozenInt.get(i)));

                    fragmentA = fragmentB * fragmentA;
                }

                double beschikbaarheidServers = 1 - fragmentA;
                double beschikbaarheidTotaal = beschikbaarheidServers * firewall;


                if (beschikbaarheidTotaal >= gewensteBeschikbaarheid && beschikbaarheidTotaal < gewensteBeschikbaarheid + 0.001 && numDbServers > 0 && numWebServers > 0 && numTotal < 1) { // check if target availability is met

                    numTotal++;
                    System.out.println("Totale beschikbaarheid: " + (beschikbaarheidTotaal * 100 + "%"));
                    System.out.println("Webserver 1: " + gekozenInt.get(0));
                    System.out.println("Webserver 2: " + gekozenInt.get(1));
                    System.out.println("Webserver 3: " + gekozenInt.get(2));
                    System.out.println("DBserver 1: " + gekozenInt.get(3));
                    System.out.println("Dbserver 2: " + gekozenInt.get(4));
                    System.out.println("DBserver 3: " + gekozenInt.get(5));

                    for (int i = 0; i < gekozenInt.size() - 1; i++) {
                        if (gekozenInt.get(i) > 0) {
                            for (int p = 1; p <= gekozenInt.get(i); p++) { //checks amount
                                combo.add(leverancierdevices.get(i).getNaam());
                            }
                        }
                    }


                }
                return;
            }
            for (int i = 0; i < 3; i++) {
                gekozenInt.set(index, i);
                if (index < 3) { // web server
                    calculateServers(leverancierdevices, gekozenInt, index + 1, firewall, gewensteBeschikbaarheid, numWebServers, numDbServers);
                    numWebServers++;
                } else if (index < 6) { // database
                    calculateServers(leverancierdevices, gekozenInt, index + 1, firewall, gewensteBeschikbaarheid, numWebServers, numDbServers);
                    numDbServers++;
                } else if (index > 6) {
                    index = 0;
                    calculateServers(leverancierdevices, gekozenInt, index, firewall, gewensteBeschikbaarheid, numWebServers, numDbServers);

                }
            }
        }

    } // functions

} //class

