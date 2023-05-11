import javax.swing.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
public class GuiLooksVergelijken {

    GuiLooksVergelijken(){
        JFrame tabVergelijken = new JFrame();
        tabVergelijken.setSize(800, 600);
        tabVergelijken.setResizable(false);
        tabVergelijken.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tabVergelijken.setVisible(true);

        //data van de tabel

        Object[] columsTabelMonitoring = {"Nep","Nep", "Nep"};

        Object[][] dataMonitoring = {
                {"19-04-2023", "99,6%", "Aardbeving"},
                {"17-04-2023", "99,6%", "online"},
                {"12-04-2023", "99,6%", "online"},
                {"05-04-2023", "99,6%", "Losse veter"},
        };
        Object[][] columsMonitoringHeader = {
                {"Datum","Beschikbaarheid", "Bijzonderheden"}
        };

        //Headervan de tabel
        JTable tabelVergelijkenHeader = new JTable(columsMonitoringHeader, columsTabelMonitoring);
        tabelVergelijkenHeader.setBounds(15,15,450,16);

        //tabelVergelijken
        JTable tabelVergelijken = new JTable(dataMonitoring, columsTabelMonitoring);
        tabelVergelijken.setBounds(15, 31, 450, 200);

        //TextBeschikbaarheid
        JLabel text = new JLabel("Beschikbaarheid");
        text.setBounds(15,400,200,200);

        //isEditableUitzetten
        DefaultTableModel tableModel2 = new DefaultTableModel(columsMonitoringHeader, columsTabelMonitoring){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelVergelijkenHeader.setModel(tableModel2);

        DefaultTableModel tableModel3 = new DefaultTableModel(dataMonitoring, columsTabelMonitoring){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelVergelijken.setModel(tableModel3);


        tabVergelijken.add(tabelVergelijkenHeader);
        tabVergelijken.add(tabelVergelijken);
        tabVergelijken.add(text);

    }
}
