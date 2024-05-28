package org.example.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MatriculaView extends JFrame{
    private JTabbedPane pestanyes;
    private JTable taula;
    private JScrollPane scrollPane1;
    private JButton insertarButton;
    private JButton modificarButton;
    private JButton borrarButton;
    private JTextField campNom;
    private JTextField campIngres;
    private JCheckBox caixaClient;
    private JPanel panel;
    private JTextField campEdad;
    private JButton commitButtom;
    private JComboBox genereBox;
    private JLabel Edad;
    //private JTabbedPane PanelPestanya;

    //Getters



    public JTabbedPane getPestanyes() {
        return pestanyes;
    }

    public JTable getTaula() {
        return taula;
    }

    public JButton getBorrarButton() {
        return borrarButton;
    }

    public JButton getModificarButton() {
        return modificarButton;
    }

    public JButton getInsertarButton() {
        return insertarButton;
    }

    public JTextField getCampNom() {
        return campNom;
    }

    public JTextField getCampIngres() {
        return campIngres;
    }

    public JCheckBox getCaixaClient() {
        return caixaClient;
    }

    public JTextField getCampEdad() {
        return campEdad;
    }
    public JButton getCommitButtom() {
        return commitButtom;
    }
    public JComboBox getGenereBox() {
        return genereBox;
    }
    //Constructor de la classe
    public MatriculaView() {


        //Per poder vore la finestra
        this.setContentPane(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(false);

    }

        private void createUIComponents() {
        // TODO: place custom component creation code here
        scrollPane1 = new JScrollPane();
        taula = new JTable();
        pestanyes = new JTabbedPane();
        taula.setModel(new DefaultTableModel());
        taula.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scrollPane1.setViewportView(taula);

    }

}
