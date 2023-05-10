import javax.swing.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.JFrame;

public class GuiLooks extends JFrame implements ActionListener {
    Color headerKleur = new Color(184, 207, 229);


    //Monitoring
    JButton knopVergelijkenMonitoring;
    JButton knopOpslaanMonitoring;
    JButton knopVerversenMonitoring;
    URL url;
    JLabel plaatje;

    //Berkenen
    JButton knopBerekenBerekenen;

    //ontwerpen
    ArrayList<Object> selectedUptime = new ArrayList<>();
    JLabel labelBeschikbaarheidOntwerpen = new JLabel();
    JButton knopLadenOntwerpen;
    JButton knopOpslaanOntwerpen;
    JButton knopToevoegenOntwerpen;
    JButton knopResetOntwerpen;
    JTable tabelOntwerpen1;
    JTable tabelOntwerpen2;
    JTable tabelOntwerpen3;
    JTable tabelOntwerpen4;
    DefaultTableModel ontwerpenBodyModel4;
    boolean booleanWeb = false;
    boolean booleanDb = false;


    GuiLooks() throws MalformedURLException {
        //geregistreerde devices uit netwerk ophalen
        Netwerk netwerk = new Netwerk();
        ArrayList<NetwerkDevice> monitorDevices = new ArrayList<>(netwerk.getDevices());

        //devices vanuit de leverancier ophalen
        Leverancier leverancier = new Leverancier();
        ArrayList<LeverancierDevice> leverancierDevices = new ArrayList<>(leverancier.getLeverancierDevices());

        //GUI
        JFrame scherm = new JFrame();
        scherm.setResizable(false);
        scherm.setSize(800, 600);
        scherm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        scherm.setVisible(true);
        JTabbedPane tablad = new JTabbedPane();
        scherm.add(tablad);
        tablad.setFocusable(false);

//tabladen

//monitoring----------------------------------------------------------------
        JLabel monitoring = new JLabel();//tablad monitoring
        monitoring.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()));

        //grafiek
        try {
            url = new URL("http://3.bp.blogspot.com/-LxZM5TD1Eb8/US53AyIr8PI/AAAAAAAACd0/UN5jwhK-Wqc/s1600/CHART.GIF");
            Icon icon = new ImageIcon(url);
            plaatje = new JLabel(icon);
            plaatje.setBounds(15, 10, 250,250);
        } catch (Exception e){
            System.out.println("plaatje werkte neit");
        }

        //Data voor header
        Object[] columsTabelMonitoring = {"Nep", "Nep", "Nep"};
        Object[][] dataMonitoring = {
                {"pfSense", "193.167.12.3", "Online"}
        };
        Object[][] columsMonitoringHeader = {
                {"Server", "IPv4", "Status"}
        };

        //Knoppen
        knopOpslaanMonitoring = new JButton("Opslaan gegevens");
        knopOpslaanMonitoring.setBounds(475, 450, 275, 30);
        knopOpslaanMonitoring.addActionListener(this);

        knopVergelijkenMonitoring = new JButton("Vergelijk met andere dagen");
        knopVergelijkenMonitoring.setBounds(475, 420, 275, 30);
        knopVergelijkenMonitoring.addActionListener(this);

        knopVerversenMonitoring = new JButton("Verversen");
        knopVerversenMonitoring.setBounds(650,80,120,35);
        knopVerversenMonitoring.addActionListener(this);

        //Opmerkingen
        double percentage = 99.7;
        int minuten = 3;

        JLabel textMonitor = new JLabel("De huidige beschikbaarheid is: " + percentage + "%");
        textMonitor.setBounds(450,30,500,30);
        textMonitor.setFont(new Font("Calibri", Font.PLAIN, 20));

        JLabel textGeleden = new JLabel( "(" + minuten + " minuten geleden)");
        textGeleden.setBounds(650,50,200,15);

        JTextField opmerking = new JTextField("Eventuele opmerkingen");
        opmerking.setBounds(475, 270, 275, 150);

        //Tabelheader
        JTable tabelMonitorHeader = new JTable(columsMonitoringHeader, columsTabelMonitoring);
        tabelMonitorHeader.setBounds(15, 270, 425, 16);
        tabelMonitorHeader.setBackground(headerKleur);

        //tabelinhoud
        JTable tabelMonitor = new JTable(dataMonitoring, columsTabelMonitoring);

        //Iseditable uitzetten
        DefaultTableModel monitoringHeaderModel = new DefaultTableModel(columsMonitoringHeader, columsTabelMonitoring) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelMonitorHeader.setModel(monitoringHeaderModel);

        DefaultTableModel monitoringBodyModel = new DefaultTableModel(dataMonitoring, columsTabelMonitoring) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (NetwerkDevice i : monitorDevices) {
            Object[] row = {i.getNaam(), i.getIpv4(), i.isStatus()};
            monitoringBodyModel.insertRow(1, row);
        }

        tabelMonitor.setBounds(15, 286, 425, 200);
        tabelMonitor.setFillsViewportHeight(true);
        tabelMonitor.setModel(monitoringBodyModel);

//berekenen----------------------------------------------------------------
        JLabel berekenen = new JLabel();//bereken tablad
        berekenen.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder()));

        //tabledata
        Object[] columnsTabelBereken = {"Nep", "Nep", "Nep", "Nep", "Nep"};
        Object[][] dataBerekenen = {
                {"pfSense", "firewall", 99.99, 1, "1200"}
        };
        Object[][] columnsBerekenHeader = {
                {"Device", "Functie", "Uptime", "Aantal", "Kosten"}
        };

        //button 'knopBerekenBerekenen'
        knopBerekenBerekenen = new JButton("Go!");
        knopBerekenBerekenen.setBounds(95, 35, 60, 20);
        knopBerekenBerekenen.addActionListener(this);


        //textarea 'gewenstpercentage'
        JTextArea gewenstPercentage = new JTextArea("%");
        gewenstPercentage.setBounds(15, 35, 70, 20);

        //text 'voer de gewenste beschikbaarheid in'
        JLabel voerIn = new JLabel("Voer de gewenste beschikbaarheid in:");
        voerIn.setBounds(15, 10, 251, 10);


        //table
        JTable tabelBereken = new JTable(dataBerekenen, columnsTabelBereken);
        tabelBereken.setBounds(15, 116, 300, 300);

        JTable tabelBerekenHeader = new JTable(columnsBerekenHeader, columnsTabelBereken);
        tabelBerekenHeader.setBounds(15, 100, 300, 16);
        tabelBerekenHeader.setBackground(headerKleur);


        //Iseditable uitzetten
        DefaultTableModel berekenenHeaderModel = new DefaultTableModel(columnsBerekenHeader, columnsTabelBereken) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelBerekenHeader.setModel(berekenenHeaderModel);

        DefaultTableModel berekenenBodyModel = new DefaultTableModel(dataBerekenen, columnsTabelBereken) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        //inhoud genereren
        int tKosten = 0;
        for (LeverancierDevice i : leverancierDevices) {
            tKosten += i.getKostenEnkel();

            Object[] row = {i.getNaam(), i.getFunctie(), i.getUptime(), 5 ,i.getKostenEnkel()};
            berekenenBodyModel.insertRow(1, row);
        }

        tabelBereken.setModel(berekenenBodyModel);

        //text 'totale kosten'
        String totaalK = "Totale kosten: $"+ tKosten;
        JLabel totaalKosten = new JLabel(totaalK);
        totaalKosten.setBounds(215, 50, 251, 10);

//ontwerpen----------------------------------------------------------------
        JLabel ontwerpen = new JLabel();

        //tabel 1-----------------------------
        Object[] columsTabelOntwerpen1 = {"Nep", "Nep"};
        Object[][] dataOntwerpen1 = {
                {leverancierDevices.get(0).getNaam(), leverancierDevices.get(0).getUptime()}
        };
        Object[][] columsOntwerpenHeader1 = {
                {"Firewall", "%Uptime", "Toevoegen"}
        };

        JLabel textFirewall = new JLabel("Selecteer een firewall: ");
        textFirewall.setBounds(15,15,220,16);

        JTable tabelOntwerpenHeader1 = new JTable(columsOntwerpenHeader1, columsTabelOntwerpen1);
        tabelOntwerpenHeader1.setBounds(15,35,220,16);
        tabelOntwerpenHeader1.setBackground(headerKleur);

        tabelOntwerpen1 = new JTable(dataOntwerpen1, columsTabelOntwerpen1);
        tabelOntwerpen1.setBounds(15, 51, 220, 200);
        tabelOntwerpen1.setFillsViewportHeight(true);

        //tabel 2----------------------------------------
        Object[] columsTabelOntwerpen2 = {"Nep", "Nep"};
        Object[][] dataOntwerpen2 = {
                {leverancierDevices.get(4).getNaam(), leverancierDevices.get(4).getUptime()},
                {leverancierDevices.get(5).getNaam(), leverancierDevices.get(5).getUptime()},
                {leverancierDevices.get(6).getNaam(), leverancierDevices.get(6).getUptime()}
        };
        Object[][] columsOntwerpenHeader2 = {
                {"Database", "%Uptime", "Toevoegen"}
        };

        JLabel textWebserver = new JLabel("Selecteer een webserver: ");
        textWebserver.setBounds(250,15,220,16);

        JTable tabelOntwerpenHeader2 = new JTable(columsOntwerpenHeader2, columsTabelOntwerpen2);
        tabelOntwerpenHeader2.setBounds(250,35,220,16);
        tabelOntwerpenHeader2.setBackground(headerKleur);

        tabelOntwerpen2 = new JTable(dataOntwerpen2, columsTabelOntwerpen2);
        tabelOntwerpen2.setBounds(250, 51, 220, 200);
        tabelOntwerpen2.setFillsViewportHeight(true);

        //tabel 3----------------------------------------------
        Object[] columsTabelOntwerpen3 = {"Nep", "Nep"};
        Object[][] dataOntwerpen3 = {
                {leverancierDevices.get(1).getNaam(), leverancierDevices.get(1).getUptime()},
                {leverancierDevices.get(2).getNaam(), leverancierDevices.get(2).getUptime()},
                {leverancierDevices.get(3).getNaam(), leverancierDevices.get(3).getUptime()}
        };
        Object[][] columsOntwerpenHeader3 = {
                {"Webserver", "%Uptime", "Toevoegen"}
        };

        JLabel textDatabase = new JLabel("Selecteer een database: ");
        textDatabase.setBounds(485,15,220,16);

        JTable tabelOntwerpenHeader3 = new JTable(columsOntwerpenHeader3, columsTabelOntwerpen3);
        tabelOntwerpenHeader3.setBounds(485,35,220,16);
        tabelOntwerpenHeader3.setBackground(headerKleur);

        tabelOntwerpen3 = new JTable(dataOntwerpen3, columsTabelOntwerpen3);
        tabelOntwerpen3.setBounds(485, 51, 220, 200);
        tabelOntwerpen3.setFillsViewportHeight(true);

        //tabel 4: ontwerp--------------------------------------------
        Object[] columnsTableOntwerpen4 = {"Nep", "Nep"};
        Object[][] dataOntwerpen4 = {
                {"pfSense", 99.998}
        };
        Object[][] columnsOntwerpenHeader4 = {
                {"Device", "%Uptime"}
        };

        ontwerpenBodyModel4 = new DefaultTableModel(dataOntwerpen4, columnsTableOntwerpen4) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabelOntwerpenHeader4 = new JTable(columnsOntwerpenHeader4, columnsTableOntwerpen4);
        tabelOntwerpenHeader4.setBounds(15, 285, 450, 16);
        tabelOntwerpenHeader4.setBackground(headerKleur);

        tabelOntwerpen4 = new JTable(dataOntwerpen4, columnsTableOntwerpen4);
        tabelOntwerpen4.setBounds(15, 300, 450, 200);
        tabelOntwerpen4.setModel(ontwerpenBodyModel4);
        tabelOntwerpen4.setFillsViewportHeight(true);

        //berekenLaden
        knopLadenOntwerpen = new JButton("Laden");
        knopLadenOntwerpen.setBounds(650, 470, 100, 25);
        knopLadenOntwerpen.addActionListener(this);

        //berekenOpslaan
        knopOpslaanOntwerpen = new JButton("Opslaan");
        knopOpslaanOntwerpen.setBounds(650, 500, 100, 25);
        knopOpslaanOntwerpen.addActionListener(this);

        //toevoegen aan ontwerp
        knopToevoegenOntwerpen = new JButton("Toevoegen");
        knopToevoegenOntwerpen.setBounds(650, 260, 100, 25);
        knopToevoegenOntwerpen.addActionListener(this);

        //reset ontwerp
        knopResetOntwerpen = new JButton("Verwijder");
        knopResetOntwerpen.setBounds(650, 290, 100, 25);
        knopResetOntwerpen.addActionListener(this);

        //beschikbaarheid berekenen (refresh is automatisch)
        labelBeschikbaarheidOntwerpen.setBounds(550, 400, 180, 25);

//Frame bouwen-------------------------------------------------------------

        //add in all the stuff monitoren
        monitoring.add(tabelMonitorHeader);
        monitoring.add(tabelMonitor);
        monitoring.add(knopOpslaanMonitoring);
        monitoring.add(knopVergelijkenMonitoring);
        monitoring.add(opmerking);
        monitoring.add(plaatje);
        monitoring.add(textMonitor);
        monitoring.add(textGeleden);
        monitoring.add(knopVerversenMonitoring);
        tablad.addTab("Monitoring", monitoring);

        //add in all the stuff berekenen
        berekenen.add(tabelBerekenHeader);
        berekenen.add(tabelBereken);
        berekenen.add(voerIn);
        berekenen.add(knopBerekenBerekenen);
        berekenen.add(gewenstPercentage);
        berekenen.add(totaalKosten);
        tablad.addTab("Berekenen", berekenen);

        //add in all the stuff ontwerpen
        ontwerpen.add(tabelOntwerpenHeader1);
        ontwerpen.add(tabelOntwerpen1);
        ontwerpen.add(tabelOntwerpenHeader2);
        ontwerpen.add(tabelOntwerpen2);
        ontwerpen.add(tabelOntwerpenHeader3);
        ontwerpen.add(tabelOntwerpen3);
        ontwerpen.add(tabelOntwerpenHeader4);
        ontwerpen.add(tabelOntwerpen4);
        ontwerpen.add(textDatabase);
        ontwerpen.add(textWebserver);
        ontwerpen.add(labelBeschikbaarheidOntwerpen);
        ontwerpen.add(textFirewall);
        ontwerpen.add(knopToevoegenOntwerpen);
        ontwerpen.add(knopResetOntwerpen);
        ontwerpen.add(knopLadenOntwerpen);
        ontwerpen.add(knopOpslaanOntwerpen);
        tablad.addTab("Ontwerp", ontwerpen);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object actionSource = e.getSource();

        if(actionSource == knopVergelijkenMonitoring) {
            new GuiLooksVergelijken();
            System.out.println("window vergelijken openen...");
        }
        else if(actionSource == knopLadenOntwerpen){
            System.out.println("oude projecten laden...");
        }
        else if(actionSource == knopOpslaanOntwerpen){
            System.out.println("Project opslaan...");
        }
        else if(actionSource == knopOpslaanMonitoring){
            System.out.println("monitorgegevens opslaan...");
        }
        else if(actionSource == knopBerekenBerekenen){
            System.out.println("berekenen benodigde devices...");
        }
        else if(actionSource == knopToevoegenOntwerpen){
            System.out.println("toevoegen aan ontwerp...");

            //extract de data van geselecteerde row en voeg die toe aan tabel 4
            if(tabelOntwerpen1.getSelectedRow() != -1) {
                String naamSelected = tabelOntwerpen1.getValueAt(tabelOntwerpen1.getSelectedRow(), 0).toString();
                Object uptimeSelected = tabelOntwerpen1.getValueAt(tabelOntwerpen1.getSelectedRow(), 1);

                Object[] rowData = {naamSelected, uptimeSelected};
                selectedUptime.add(tabelOntwerpen1.getValueAt(tabelOntwerpen1.getSelectedRow(), 1));
                ontwerpenBodyModel4.insertRow(0, rowData);
                System.out.println("toevoegen "+naamSelected);

                tabelOntwerpen1.clearSelection();
                String textB = "De beschikbaarheid is "+ Functions.beschikbaarheidOntwerp(selectedUptime, booleanWeb, booleanDb) + "%";
                labelBeschikbaarheidOntwerpen.setText(textB);
                repaint();
            }

            if (tabelOntwerpen2.getSelectedRow() != -1){
                booleanWeb = true;

                String naamSelected = tabelOntwerpen2.getValueAt(tabelOntwerpen2.getSelectedRow(), 0).toString();
                Object uptimeSelected = tabelOntwerpen2.getValueAt(tabelOntwerpen2.getSelectedRow(), 1);

                Object[] rowData = {naamSelected, uptimeSelected};
                selectedUptime.add(tabelOntwerpen2.getValueAt(tabelOntwerpen2.getSelectedRow(), 1));
                ontwerpenBodyModel4.insertRow(0, rowData);
                System.out.println("toevoegen "+naamSelected);


                tabelOntwerpen2.clearSelection();
                String textB = "De beschikbaarheid is "+ Functions.beschikbaarheidOntwerp(selectedUptime, booleanWeb, booleanDb) + "%";
                labelBeschikbaarheidOntwerpen.setText(textB);
                repaint();
            }
            if (tabelOntwerpen3.getSelectedRow() != -1){
                booleanDb = true;

                String naamSelected = tabelOntwerpen3.getValueAt(tabelOntwerpen3.getSelectedRow(), 0).toString();
                Object uptimeSelected = tabelOntwerpen3.getValueAt(tabelOntwerpen3.getSelectedRow(), 1);

                Object[] rowData = {naamSelected, uptimeSelected};
                selectedUptime.add(tabelOntwerpen3.getValueAt(tabelOntwerpen3.getSelectedRow(), 1));
                ontwerpenBodyModel4.insertRow(0, rowData);
                System.out.println("toevoegen "+naamSelected);

                tabelOntwerpen3.clearSelection();
                String textB = "De beschikbaarheid is "+ Functions.beschikbaarheidOntwerp(selectedUptime, booleanWeb, booleanDb) + "%";
                labelBeschikbaarheidOntwerpen.setText(textB);
                repaint();
            }
        }
        else if(actionSource == knopVerversenMonitoring){
            System.out.println("Verversen beschikbaarheid");
        }
        else if (actionSource == knopResetOntwerpen){
            try {
                ontwerpenBodyModel4.removeRow(0);
                selectedUptime.remove(0);

                String textB = "De beschikbaarheid is "+ Functions.beschikbaarheidOntwerp(selectedUptime, booleanWeb, booleanDb) + "%";
                labelBeschikbaarheidOntwerpen.setText(textB);

                repaint();
            } catch (ArrayIndexOutOfBoundsException u){
                System.out.println("deleting null");
            }
        }
    }
}