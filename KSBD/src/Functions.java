import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.io.*;

public class Functions {
    public static double beschikbaarheidOntwerp(ArrayList<Object> uptimesFirewall, ArrayList<Object> uptimesDb, ArrayList<Object> uptimesWeb) {

        double fragmentADb = 1;
        double fragmentAWeb = 1;

        double firewall = 0.99998;

        for (Object uptimeDb : uptimesDb) {
            double i = ((double) uptimeDb);
            double fragmentB = (1 - (i / 100));

            fragmentADb = fragmentB * fragmentADb;
        }
        for (Object uptimeWeb : uptimesWeb) {
            double i = ((double) uptimeWeb);
            double fragmentB = (1 - (i / 100));

            fragmentAWeb = fragmentB * fragmentAWeb;
        }
        double beschikbaarheidServersDb = 1 - fragmentADb;
        double beschikbaarheidServersWeb = 1 - fragmentAWeb;
        return (beschikbaarheidServersDb * beschikbaarheidServersWeb * firewall) * 100;
    }


    static ArrayList<Object> serversKosten = new ArrayList<>();
    static ArrayList<Object> serversUptime = new ArrayList<>();
    static ArrayList<Object> serversFunctie = new ArrayList<>();
    static ArrayList<Object> serversKosten1 = new ArrayList<>();
    static ArrayList<Object> serversUptime1 = new ArrayList<>();
    static ArrayList<Object> serversFunctie1 = new ArrayList<>();
    static ArrayList<Object> serversNaam = new ArrayList<>();

    static ArrayList<Object> serversNaamFinal = new ArrayList<>();
    static ArrayList<Object> serversKostenFinal = new ArrayList<>();
    static ArrayList<Object> serversFunctieFinal = new ArrayList<>();

    static ArrayList<Object> serversUptimeFinal = new ArrayList<>();


    private static final Leverancier leverancierWilco = new Leverancier();
    public static int maxCost = Integer.MAX_VALUE;
    public static double maxBeschikbaarheid = 0;

    public static void backtrackEerst(double targetAvailability) {
        maxCost = Integer.MAX_VALUE;
        int basisCost = maakBasis1(targetAvailability);

        backtrack(targetAvailability, basisCost);
//        printCheapestResult();
    }

    public static void backtrack(double targetAvailability, int basisCost) {

        int cost = calculateCost1();
        double uptime = calculateAvailability(serversUptime1, serversFunctie1);

        if (cost > basisCost || cost >= maxCost) {
            return;
        }

        if (uptime >= targetAvailability) {
            maxCost = cost;
            maxBeschikbaarheid = uptime;

            serversNaamFinal = new ArrayList<>();
            serversKostenFinal = new ArrayList<>();
            serversUptimeFinal = new ArrayList<>();
            serversFunctieFinal = new ArrayList<>();

            serversNaamFinal.addAll(serversNaam);
            serversKostenFinal.addAll(serversKosten1);
            serversUptimeFinal.addAll(serversUptime1);
            serversFunctieFinal.addAll(serversFunctie1);

            return;
        }

        for (int i = 1; i <= leverancierWilco.getLeverancierDevices().size() - 1; i++) {
            serversKosten1.add(leverancierWilco.getLeverancierDevices().get(i).getKostenEnkel());
            serversUptime1.add(leverancierWilco.getLeverancierDevices().get(i).getUptime());
            serversFunctie1.add(leverancierWilco.getLeverancierDevices().get(i).getFunctie());
            serversNaam.add(leverancierWilco.getLeverancierDevices().get(i).getNaam());
            backtrack(targetAvailability, basisCost);
            int lengte = serversKosten1.size() - 1;
            serversKosten1.remove(lengte);
            serversUptime1.remove(lengte);
            serversFunctie1.remove(lengte);
            serversNaam.remove(lengte);
        }
    }

    private static void serverVoorPrint() {
        serversNaamFinal = serversNaam;
    }


    public static int maakBasis1(double targetAvailability) {
        int cost = calculateCost();
        double uptime = calculateAvailability(serversUptime, serversFunctie);

        if (uptime < targetAvailability) {
            serversKosten.add(leverancierWilco.getLeverancierDevices().get(2).getKostenEnkel());
            serversUptime.add(leverancierWilco.getLeverancierDevices().get(2).getUptime());
            serversFunctie.add(leverancierWilco.getLeverancierDevices().get(2).getFunctie());

            serversKosten.add(leverancierWilco.getLeverancierDevices().get(5).getKostenEnkel());
            serversUptime.add(leverancierWilco.getLeverancierDevices().get(5).getUptime());
            serversFunctie.add(leverancierWilco.getLeverancierDevices().get(5).getFunctie());

            return maakBasis1(targetAvailability);
        }

        if (uptime > targetAvailability) {
            return cost;
        }
        return 0; // Return a default value if no condition is met
    }

    public static int calculateCost() {

        int cost = 0;
        for (int i = 0; i < serversKosten.size(); i++) {
            int kostenServer = (int) serversKosten.get(i);
            cost += kostenServer;
        }
        return cost;
    }

    public static int calculateCost1() {

        int cost = 0;
        for (int i = 0; i < serversKosten1.size(); i++) {
            int kostenServer = (int) serversKosten1.get(i);
            cost += kostenServer;
        }
        return cost;
    }

    public static double calculateAvailability(ArrayList<Object> serversUptime, ArrayList<Object> serversFunctie) {

        double fragmentADb = 1;
        double fragmentAWeb = 1;

        double firewall = 0.99998;

        for (int i = 0; i < serversUptime.size(); i++) {
            if (serversFunctie.get(i) == "database") {
                double f = ((double) serversUptime.get(i));
                double fragmentB = (1 - (f / 100));

                fragmentADb = fragmentB * fragmentADb;
            } else {
                double f = ((double) serversUptime.get(i));
                double fragmentB = (1 - (f / 100));

                fragmentAWeb = fragmentB * fragmentAWeb;
            }
        }
        double beschikbaarheidServersDb = 1 - fragmentADb;
        double beschikbaarheidServersWeb = 1 - fragmentAWeb;
        return (beschikbaarheidServersDb * beschikbaarheidServersWeb * firewall) * 100;
    }

    public static void printCheapestResult() {
        System.out.println();
        System.out.println("Cheapest Result:");

        for (int i = 0; i < serversNaamFinal.size(); i++) {
            System.out.print(serversNaamFinal.get(i) + ", ");
            System.out.print(serversFunctieFinal.get(i) + ", ");
            System.out.print(serversKostenFinal.get(i) + ", ");
            System.out.println(serversUptimeFinal.get(i));
        }

        System.out.println("Cost: " + maxCost);
        System.out.println("Uptime: " + maxBeschikbaarheid);
    }
    //function
}//class