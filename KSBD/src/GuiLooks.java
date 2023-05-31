import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.io.IOException;
import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Arrays;


public class GuiLooks extends JFrame implements ActionListener {
    //GUI
    private Color headerKleur = new Color(184, 207, 229);
    private JFrame scherm;

    //Monitoring
    private JButton knopVergelijkenMonitoring;
    private JButton knopOpslaanMonitoring;
    private JButton knopVerversenMonitoring;
    private JButton knopGrafiekMonitoren;
    private JTextField opmerking;
    private JTable tabelMonitor;

    //Berekenen
    private JButton knopBerekenBerekenen;
    private JLabel totaalKosten;
    private JLabel foutInvoer = new JLabel("Ongeldig percentage");
    private double gewensteBeschikbaarheid = 0;
    private int tKosten = 0;
    private JTextArea gewenstPercentage;
    private Leverancier leverancierWilco = new Leverancier();
    private DefaultTableModel berekenenBodyModel;

    //ontwerpen
    private ArrayList<Object> selectedUptime = new ArrayList<>();
    private ArrayList<Object> saveComboNames = new ArrayList<>();
    private ArrayList<Object> saveComboUptimes = new ArrayList<>();
    ArrayList<Object> selectedUptimeDb = new ArrayList<>();
    ArrayList<Object> selectedUptimeWeb = new ArrayList<>();
    ArrayList<Object> selectedUptimeFirewall = new ArrayList<>();
    private JLabel labelBeschikbaarheidOntwerpen = new JLabel();
    private JButton knopLadenOntwerpen;
    private JButton knopOpslaanOntwerpen;
    private JButton knopToevoegenOntwerpen;
    private JButton knopResetOntwerpen;
    private JButton knopVerwijderOntwerpen;
    private JButton setTitle;
    private JButton getTitle;
    private JTable tabelOntwerpen1;
    private JTable tabelOntwerpen2;
    private JTable tabelOntwerpen3;
    private JTable tabelOntwerpen4;
    private JTextField naamLaden;
    private JTextField naamOpslaan;
    private DefaultTableModel ontwerpenBodyModel4;

    GuiLooks() throws MalformedURLException {
        //geregistreerde devices uit netwerk ophalen
        Netwerk netwerk = new Netwerk();
        ArrayList<NetwerkDevice> monitorDevices = new ArrayList<>(netwerk.getDevices());

        //devices vanuit de leverancier ophalen
        Leverancier leverancier = new Leverancier();
        ArrayList<LeverancierDevice> leverancierDevices = new ArrayList<>(leverancier.getLeverancierDevices());

        //GUI
        scherm = new JFrame();
        scherm.setResizable(false);
        scherm.setTitle("Nerdy 'o Monitor");
        URL url = new URL("https://upload.wikimedia.org/wikipedia/en/c/cd/Gardenscapes_icon.png");
        try {
            Image image1 = ImageIO.read(url);
            scherm.setIconImage(new ImageIcon(image1).getImage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
        JEditorPane janet = new JEditorPane();

        //Data voor header
        Object[] columsTabelMonitoring = {"Nep", "Nep", "Nep"};
        Object[][] dataMonitoring = {
                {"pfSense", "192.168.1.1", "Online"}
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

        knopGrafiekMonitoren = new JButton("Bekijk online");
        knopGrafiekMonitoren.setBounds(15, 30, 200, 30);
        knopGrafiekMonitoren.addActionListener(this);

        knopVerversenMonitoring = new JButton("Verversen");
        knopVerversenMonitoring.setBounds(15,60,200,30);
        knopVerversenMonitoring.addActionListener(this);

        //Opmerkingen
        opmerking = new JTextField("Eventuele opmerkingen");
        opmerking.setBounds(475, 270, 275, 150);

        //Tabelheader
        JTable tabelMonitorHeader = new JTable(columsMonitoringHeader, columsTabelMonitoring);
        tabelMonitorHeader.setBounds(15, 270, 425, 16);
        tabelMonitorHeader.setBackground(headerKleur);

        //tabelinhoud
        tabelMonitor = new JTable(dataMonitoring, columsTabelMonitoring);

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
        Object[] columnsTabelBereken = {"Nep", "Nep", "Nep", "Nep"};
        Object[][] dataBerekenen = {
                {"pfSense", "firewall", 99.99, "4000"}
        };
        Object[][] columnsBerekenHeader = {
                {"Device", "Functie", "Uptime", "Kosten"}
        };

        //button 'knopBerekenBerekenen'
        knopBerekenBerekenen = new JButton("Go!");
        knopBerekenBerekenen.setBounds(95, 35, 60, 20);
        knopBerekenBerekenen.addActionListener(this);


        //textarea 'gewenstpercentage'
        gewenstPercentage = new JTextArea("99.7");
        gewenstPercentage.setBounds(15, 35, 70, 20);


        //text 'voer de gewenste beschikbaarheid in'
        JLabel voerIn = new JLabel("Voer de gewenste beschikbaarheid in:");
        voerIn.setBounds(15, 10, 251, 10);


        //table
        JTable tabelBereken = new JTable(dataBerekenen, columnsTabelBereken);
        tabelBereken.setBounds(15, 116, 350, 300);

        JTable tabelBerekenHeader = new JTable(columnsBerekenHeader, columnsTabelBereken);
        tabelBerekenHeader.setBounds(15, 100, 350, 16);
        tabelBerekenHeader.setBackground(headerKleur);


        //Iseditable uitzetten
        DefaultTableModel berekenenHeaderModel = new DefaultTableModel(columnsBerekenHeader, columnsTabelBereken) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelBerekenHeader.setModel(berekenenHeaderModel);

        berekenenBodyModel = new DefaultTableModel(dataBerekenen, columnsTabelBereken) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelBereken.setModel(berekenenBodyModel);

        //text 'totale kosten'
        String totaalK = "Totale kosten: $" + tKosten;
        totaalKosten = new JLabel(totaalK);
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
        textFirewall.setBounds(15, 15, 220, 16);

        JTable tabelOntwerpenHeader1 = new JTable(columsOntwerpenHeader1, columsTabelOntwerpen1);
        tabelOntwerpenHeader1.setBounds(15, 35, 220, 16);
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
        textWebserver.setBounds(250, 15, 220, 16);

        JTable tabelOntwerpenHeader2 = new JTable(columsOntwerpenHeader2, columsTabelOntwerpen2);
        tabelOntwerpenHeader2.setBounds(250, 35, 220, 16);
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
        textDatabase.setBounds(485, 15, 220, 16);

        JTable tabelOntwerpenHeader3 = new JTable(columsOntwerpenHeader3, columsTabelOntwerpen3);
        tabelOntwerpenHeader3.setBounds(485, 35, 220, 16);
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

        //verwijder specifiek deel ontwerp
        knopVerwijderOntwerpen = new JButton("Verwijder");
        knopVerwijderOntwerpen.setBounds(650, 290, 100, 25);
        knopVerwijderOntwerpen.addActionListener(this);

        //reset volledig ontwerp
        knopResetOntwerpen = new JButton("Reset");
        knopResetOntwerpen.setBounds(650, 320, 100, 25);
        knopResetOntwerpen.addActionListener(this);

        //beschikbaarheid berekenen (refresh is automatisch)
        labelBeschikbaarheidOntwerpen.setBounds(550, 400, 180, 25);

//Frame bouwen-------------------------------------------------------------

        //add in all the stuff monitoren
        monitoring.add(tabelMonitorHeader);
        monitoring.add(tabelMonitor);
        monitoring.add(knopOpslaanMonitoring);
        monitoring.add(knopVergelijkenMonitoring);
        monitoring.add(knopGrafiekMonitoren);
        monitoring.add(opmerking);
        monitoring.add(knopVerversenMonitoring);
        tablad.addTab("Monitoring", monitoring);

        //add in all the stuff berekenen
        berekenen.add(tabelBerekenHeader);
        berekenen.add(tabelBereken);
        berekenen.add(voerIn);
        berekenen.add(knopBerekenBerekenen);
        berekenen.add(gewenstPercentage);
        berekenen.add(totaalKosten);
        berekenen.add(foutInvoer);
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
        ontwerpen.add(knopVerwijderOntwerpen);
        ontwerpen.add(knopResetOntwerpen);
        ontwerpen.add(knopLadenOntwerpen);
        ontwerpen.add(knopOpslaanOntwerpen);
        tablad.addTab("Ontwerp", ontwerpen);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object actionSource = e.getSource();

        if (actionSource == knopVergelijkenMonitoring) {
            new GuiLooksVergelijken();
            System.out.println("window vergelijken openen...");
        } else if (actionSource == knopVerversenMonitoring) {
            repaint();
        } else if (actionSource == knopGrafiekMonitoren) {

            //browsermoment
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                try {
                    Desktop.getDesktop().browse(new URI("http://192.168.1.194:3000/d/rYdddlPWka/node-exporter-fullv2?orgId=1"));

                    Thread.sleep(2500);

                    Robot robot = new Robot();
                    typeText(robot, "guest");
                    robot.keyPress(KeyEvent.VK_TAB);
                    robot.keyRelease(KeyEvent.VK_TAB);
                    typeText(robot, "guest"); //
                    robot.keyPress(KeyEvent.VK_ENTER);
                    robot.keyRelease(KeyEvent.VK_ENTER);
                } catch (IOException | URISyntaxException | AWTException | InterruptedException mama) {
                    mama.printStackTrace();
                }
            }
        } else if (actionSource == knopLadenOntwerpen) {
            System.out.println("oude projecten laden...");

            //vraag file naam op
            JDialog d = new JDialog(scherm, "Naam: ");
            d.setSize(260, 80);
            d.setResizable(false);
            d.setVisible(true);

            naamLaden = new JTextField();
            naamLaden.setBounds(5, 10, 90, 30);
            d.add(naamLaden);

            getTitle = new JButton("laad");
            getTitle.setBounds(100, 10, 90, 30);
            getTitle.addActionListener(this);
            d.add(getTitle);
        } else if (actionSource == getTitle) {
            ontwerpenBodyModel4.setRowCount(0);
            String fileName = naamLaden.getText();

            if (fileName != null && !fileName.equals(" ")) {
                try {
                    ArrayList<String> readLines = read(fileName);
                    for (String line : readLines) {
                        String[] text = line.split(" ");
                        ontwerpenBodyModel4.insertRow(0, text);
                    }

                } catch (Exception noFile) {
                    System.out.println("Filename no workie");
                }
            } else {
                System.out.println("naam is niet goei");
            }

            //herbereken uptime
            ArrayList<Object> uptimesDB = new ArrayList<>();
            ArrayList<Object> uptimesW = new ArrayList<>();
            ArrayList<Object> uptimesF = new ArrayList<>();

            for (int i = 0; i <= ontwerpenBodyModel4.getRowCount() - 1; i++) {
                String content = (ontwerpenBodyModel4.getValueAt(i, 0)).toString();

                if (content.contains("DB")) {
                    uptimesDB.add(ontwerpenBodyModel4.getValueAt(i, 1));
                } else if (content.contains("W")) {
                    uptimesW.add(ontwerpenBodyModel4.getValueAt(i, 1));
                } else if (content.contains("pfSense")) {
                    uptimesF.add(ontwerpenBodyModel4.getValueAt(i, 1));
                }
            }

            String textB = "De beschikbaarheid is " + Functions.beschikbaarheidOntwerp(uptimesF, uptimesW, uptimesDB) + "%";
            labelBeschikbaarheidOntwerpen.setText(textB);
            repaint();
        } else if (actionSource == knopOpslaanOntwerpen) {
            //vraag titel ontwerp op
            JDialog d = new JDialog(scherm, "Naam: ");
            d.setSize(260, 80);
            d.setResizable(false);
            d.setVisible(true);

            naamOpslaan = new JTextField();
            naamOpslaan.setBounds(5, 10, 90, 30);
            d.add(naamOpslaan);

            setTitle = new JButton("Ok");
            setTitle.setBounds(100, 10, 90, 30);
            setTitle.addActionListener(this);
            d.add(setTitle);

            for (int i = 0; i <= ontwerpenBodyModel4.getRowCount() - 1; i++) {
                saveComboNames.add(ontwerpenBodyModel4.getValueAt(i, 0));
            }
            for (int i = 0; i <= ontwerpenBodyModel4.getRowCount() - 1; i++) {
                saveComboUptimes.add(ontwerpenBodyModel4.getValueAt(i, 1));
            }
        } else if (actionSource == setTitle) {
            ontwerpenBodyModel4.setRowCount(0);
            String fileName = naamOpslaan.getText();

            if (fileName != null && !fileName.equals(" ")) {
                try {
                    write(fileName, saveComboNames, saveComboUptimes);
                } catch (Exception noFile) {
                    System.out.println("Filename no workie");
                }
            } else {
                System.out.println("naam is niet goei");
            }
        } else if (actionSource == knopOpslaanMonitoring) {

            String bijzonderheden = opmerking.getText();

            new saveMonitoring(100.00, bijzonderheden);
        } else if (actionSource == knopBerekenBerekenen) {

            berekenenBodyModel.setRowCount(0);

            Object[] firewall =
                    {leverancierWilco.getLeverancierDevices().get(0).getNaam(), leverancierWilco.getLeverancierDevices().get(0).getFunctie(), leverancierWilco.getLeverancierDevices().get(0).getUptime(), leverancierWilco.getLeverancierDevices().get(0).getKostenEnkel()};
            berekenenBodyModel.insertRow(0, firewall);

            gewensteBeschikbaarheid = Double.parseDouble(gewenstPercentage.getText());
            if (gewensteBeschikbaarheid < leverancierWilco.getLeverancierDevices().get(0).getUptime()){
                Functions.backtrackEerst(gewensteBeschikbaarheid);

                int kostenPrint = Functions.maxCost + leverancierWilco.getLeverancierDevices().get(0).getKostenEnkel();
                totaalKosten.setText("Totale kosten: $" + kostenPrint);


                for (int j = 0; j < Functions.serversNaamFinal.size(); j++) { //combo kan niet groter dan 4 worden (no duplicates)
                    Object[] insertDevice = {
                            Functions.serversNaamFinal.get(j), Functions.serversFunctieFinal.get(j), Functions.serversUptimeFinal.get(j), Functions.serversKostenFinal.get(j)
                    };

                    berekenenBodyModel.insertRow(0, insertDevice);
                }
                foutInvoer.setVisible(false);
                repaint();
            } else {
                foutInvoer.setBounds(15, 70, 200, 20);
                foutInvoer.setForeground(Color.red);
                foutInvoer.setVisible(true);

                totaalKosten.setText("Totale kosten: 0$");
                repaint();
            }
        } else if (actionSource == knopToevoegenOntwerpen) {

            //extract de data van geselecteerde row en voeg die toe aan tabel 4
            if (tabelOntwerpen1.getSelectedRow() != -1) {
                String naamSelected = tabelOntwerpen1.getValueAt(tabelOntwerpen1.getSelectedRow(), 0).toString();
                Object uptimeSelectedFirewall = tabelOntwerpen1.getValueAt(tabelOntwerpen1.getSelectedRow(), 1);

                Object[] rowData = {naamSelected, uptimeSelectedFirewall};
                selectedUptimeFirewall.add(tabelOntwerpen1.getValueAt(tabelOntwerpen1.getSelectedRow(), 1));
                ontwerpenBodyModel4.insertRow(0, rowData);

                tabelOntwerpen1.clearSelection();
                String textB = "De beschikbaarheid is " + Functions.beschikbaarheidOntwerp(selectedUptimeFirewall, selectedUptimeDb, selectedUptimeWeb) + "%";
                labelBeschikbaarheidOntwerpen.setText(textB);
                repaint();
            }

            if (tabelOntwerpen2.getSelectedRow() != -1) {
                String naamSelected = tabelOntwerpen2.getValueAt(tabelOntwerpen2.getSelectedRow(), 0).toString();
                Object uptimeSelectedDb = tabelOntwerpen2.getValueAt(tabelOntwerpen2.getSelectedRow(), 1);

                Object[] rowData = {naamSelected, uptimeSelectedDb};
                selectedUptimeDb.add(tabelOntwerpen2.getValueAt(tabelOntwerpen2.getSelectedRow(), 1));
                ontwerpenBodyModel4.insertRow(0, rowData);


                tabelOntwerpen2.clearSelection();
                String textB = "De beschikbaarheid is " + Functions.beschikbaarheidOntwerp(selectedUptimeFirewall, selectedUptimeDb, selectedUptimeWeb) + "%";
                labelBeschikbaarheidOntwerpen.setText(textB);
                repaint();
            }
            if (tabelOntwerpen3.getSelectedRow() != -1) {
                String naamSelected = tabelOntwerpen3.getValueAt(tabelOntwerpen3.getSelectedRow(), 0).toString();
                Object uptimeSelectedWeb = tabelOntwerpen3.getValueAt(tabelOntwerpen3.getSelectedRow(), 1);

                Object[] rowData = {naamSelected, uptimeSelectedWeb};
                selectedUptimeWeb.add(tabelOntwerpen3.getValueAt(tabelOntwerpen3.getSelectedRow(), 1));
                ontwerpenBodyModel4.insertRow(0, rowData);

                tabelOntwerpen3.clearSelection();
                String textB = "De beschikbaarheid is " + Functions.beschikbaarheidOntwerp(selectedUptimeFirewall, selectedUptimeWeb, selectedUptimeDb) + "%";
                labelBeschikbaarheidOntwerpen.setText(textB);
                repaint();
            }
        } else if (actionSource == knopVerwijderOntwerpen) {
                double selectedUptime = (double) tabelOntwerpen4.getValueAt(tabelOntwerpen4.getSelectedRow(), 1);
                ontwerpenBodyModel4.removeRow(tabelOntwerpen4.getSelectedRow());

                selectedUptimeDb.remove(selectedUptime);
                selectedUptimeWeb.remove(selectedUptime);

                String textB = "De beschikbaarheid is " + Functions.beschikbaarheidOntwerp(selectedUptimeFirewall, selectedUptimeWeb, selectedUptimeDb) + "%";
                labelBeschikbaarheidOntwerpen.setText(textB);

                repaint();
        } else if (actionSource == knopResetOntwerpen) {
            ontwerpenBodyModel4.setRowCount(0);

            selectedUptimeDb.clear();
            selectedUptimeWeb.clear();

            String textB = "De beschikbaarheid is " + Functions.beschikbaarheidOntwerp(selectedUptimeFirewall, selectedUptimeWeb, selectedUptimeDb) + "%";
            labelBeschikbaarheidOntwerpen.setText(textB);

            repaint();
        }
    }

    private static void typeText(Robot robot, String text) {
        for (char c : text.toCharArray()) {
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);
        }
    }

    private static void write(String filename, ArrayList<Object> ontwerpNames, ArrayList<Object> ontwerpUptimes) throws IOException {
        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(new FileWriter(filename));

        for (int i = 0; i < ontwerpNames.size(); i++) {
            outputWriter.write(ontwerpNames.get(i) + " ");
            outputWriter.write(ontwerpUptimes.get(i) + "/");
            outputWriter.newLine();
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static ArrayList<String> read(String filename) throws IOException {
        ArrayList<String> readCombo = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("/");


                // gooi alle gelezen dingksels in array
                readCombo.addAll(Arrays.asList(parts));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readCombo;
    }
}

