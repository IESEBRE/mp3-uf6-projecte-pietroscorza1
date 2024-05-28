package org.example.controller;

import org.example.model.entities.Client;
import org.example.model.entities.Generes;
import org.example.model.exceptions.DAOException;
import org.example.model.impls.ClientDAOJDBCOracleImpl;
import org.example.view.ModelComponentsVisuals;
import org.example.view.MatriculaView;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class Controller implements PropertyChangeListener { //1. Implementació de interfície PropertyChangeListener

    private ModelComponentsVisuals modelComponentsVisuals =new ModelComponentsVisuals();
    private ClientDAOJDBCOracleImpl dadesClients;
    private MatriculaView view;


    public Controller(ClientDAOJDBCOracleImpl dadesClients, MatriculaView view) {
        this.dadesClients = dadesClients;
        this.view = view;
        //5. Necessari per a que Controller reaccione davant de canvis a les propietats lligades
        canvis.addPropertyChangeListener(this);

        lligaVistaModel();

        afegirListeners();

        //Si no hem tingut cap poroblema amb la BD, mostrem la finestra
        view.setVisible(true);

    }

    /**
     * Mètode que lliga la vista amb el model
     *
     */
    private void lligaVistaModel() {
        comprovacioTaula();

        //Carreguem la taula d'Clients en les dades de la BD
            try {
                setModelTaulaClient(modelComponentsVisuals.getModelTaulaClient(),dadesClients.getAll());
            } catch (DAOException e) {
                this.setExcepcio(e);
            }

            //Fixem el model de la taula dels Clients
        JTable taula = view.getTaula();
        taula.setModel(this.modelComponentsVisuals.getModelTaulaClient());
        //Amago la columna que conté l'objecte Client

        //Fixem el model de la taula de matrícules
      //  JTable taulaMat = view.getTaulaMat();
       // taulaMat.setModel(this.modelComponentsVisuals.getModelTaulaMat());

        //Posem valor a el combo d'MPs

        //Desactivem la ingrestanya de la matrícula
        //view.getPestanyes(1, false);
        //view.getPestanyes().setTitleAt(1, "Matrícula de ...");

        //5. Necessari per a que Controller reaccione davant de canvis a les propietats lligades
    }

    /**
     * Mètode que omple el model de la taula de Clients
     * @param modelTaulaClient
     * @param all
     */
    private void setModelTaulaClient(DefaultTableModel modelTaulaClient, List<Client> all) {

        // Fill the table model with data from the collection
        for (Client client : all) {
            modelTaulaClient.addRow(new Object[]{client.getNom(), client.getIngres(),client.getEdad(), client.getMatriculat(), client.getGenere()});
        }
    }

    /**
     * Mètode que comprova si la taula de Clients existeix, i si no, la crea
     *
     */
    private void comprovacioTaula()  {
        List<Client> clients;
        try {
            clients = dadesClients.getAll();

        } catch (DAOException e) {
            try {
                dadesClients.crearTabla();
                JOptionPane.showMessageDialog(null, "Se'ha creat la taula client, ja que no havia cap creada'");
            } catch (DAOException ex) {
                setExcepcio(new DAOException(21));
            }
        }

    }

    /**
     * Mètode que afegeix els listeners als components de la vista
     */
    private void afegirListeners() {

        ModelComponentsVisuals modelo = this.modelComponentsVisuals;
        DefaultTableModel model = modelo.getModelTaulaClient();
       // DefaultTableModel modelMat = modelo.getModelTaulaMat();
        JTable taula = view.getTaula();
      //  JTable taulaMat = view.getTaulaMat();
        JButton insertarButton = view.getInsertarButton();
        JButton modificarButton = view.getModificarButton();
        JButton borrarButton = view.getBorrarButton();
        JTextField campNom = view.getCampNom();
        JTextField campIngres = view.getCampIngres();
        JTextField campEdad = view.getCampEdad();
        JCheckBox caixaClient = view.getCaixaClient();
        JTabbedPane ingrestanyes = view.getPestanyes();
        JButton commitButtom = view.getCommitButtom();
        JComboBox genereBox = view.getGenereBox();

        for (Generes genere : Generes.values()) {
            genereBox.addItem(genere);
        }
        Metodes metodes = new Metodes();

        //Botó insertar
        insertarButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {


                            if (metodes.campsBuits(campNom.getText(), campIngres.getText(), campEdad.getText()) == true) {
                                setExcepcio(new DAOException(10));
                            } else {
                                if (genereBox.getSelectedItem().toString().equals("SELECCIONA")) {
                                    setExcepcio(new DAOException(23));
                                } else {
                                    if (metodes.primeraMayuscula(campNom.getText()) == false) {
                                        setExcepcio(new DAOException(18));
                                    } else {
                                        //Si tots els camps estan plens, creem un nou Client i l'afegim a la BD (i a la taula
                                        try {
                                            int edad = 0;
                                            double ingres = metodes.passarIngres(campIngres.getText());
                                            try {
                                                edad = metodes.passarEdat(campEdad.getText());

                                                String genere = (String) genereBox.getSelectedItem().toString();

                                                Client al = new Client(campNom.getText(), ingres, edad, caixaClient.isSelected(), genere);

                                                try {
                                                    dadesClients.insert(al);
                                                    campNom.requestFocus();
                                                    model.addRow(new Object[]{al.getNom(), al.getIngres(), al.getEdad(), al.getMatriculat(), al.getGenere()});
                                                } catch (DAOException ex) {
                                                    setExcepcio(new DAOException(14));
                                                }

                                            } catch (DAOException ex) {
                                                setExcepcio(new DAOException(17));
                                            }
                                            ;
                                            //intentem que el foco vaigue al camp del nom
                                        } catch (DAOException ex) {
                                            setExcepcio(new DAOException(11));
                                            campIngres.setSelectionStart(0);
                                            campIngres.setSelectionEnd(campIngres.getText().length());
                                            campIngres.requestFocus();
                                        }
                                    }
                                }
                            }
                    }
                }
        );
        //boto de borrar
        borrarButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int filaSel = taula.getSelectedRow();
                        if (filaSel != -1) {
                            String nom = model.getValueAt(filaSel, 0).toString();
                            try {
                                Client cl = dadesClients.get(nom);
                                dadesClients.delete(cl);
                            } catch (DAOException ex) {
                                System.out.println(ex.getMessage());
                                setExcepcio(new DAOException(15));
                            }
                                model.removeRow(filaSel);
                        }
                        else setExcepcio(new DAOException(13));
                    }
                }
        );
        //boton de modificar
        modificarButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int filaSel = taula.getSelectedRow();
                        if (filaSel != -1) {
                            if (genereBox.getSelectedItem().toString().equals("SELECCIONA")) {
                                setExcepcio(new DAOException(23));
                            } else {
                                //Obtenim el nom de l'Client seleccionat (filaSel

                                String nom = model.getValueAt(filaSel, 0).toString();

                                int edad = 0;

                                try {
                                    double ingres = metodes.passarIngres(campIngres.getText());
                                    try {
                                        edad = metodes.passarEdat(campEdad.getText());

                                        String genere = (String) genereBox.getSelectedItem().toString();
                                        try {
                                            Client cl = dadesClients.get(nom);
                                            cl.setNom(campNom.getText());
                                            cl.setingres(ingres);
                                            cl.setEdad(edad);
                                            cl.setMatriculat(caixaClient.isSelected());
                                            cl.setGenere(genere);

                                            dadesClients.update(cl, nom);

                                            model.setValueAt(cl.getNom(), filaSel, 0);
                                            model.setValueAt(cl.getIngres(), filaSel, 1);
                                            model.setValueAt(cl.getEdad(), filaSel, 2);
                                            model.setValueAt(cl.getMatriculat(), filaSel, 3);
                                            model.setValueAt(cl.getGenere(), filaSel, 4);
                                        } catch (DAOException ex) {
                                            System.out.println(ex.getMessage());
                                            setExcepcio(new DAOException(16));
                                        }
                                    } catch (NumberFormatException ex) {
                                        setExcepcio(new DAOException(17));
                                    }

                                } catch (DAOException ex) {
                                    setExcepcio(new DAOException(19));
                                }
                            }
                        }
                        else setExcepcio(new DAOException(13));
                    }
                }
        );
        commitButtom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    dadesClients.save();
                } catch (DAOException e) {
                    setExcepcio(new DAOException(20));
                }
            }
        });
        

        taula.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                //Obtenim el número de la fila seleccionada
                int filaSel = taula.getSelectedRow();

                if (filaSel != -1) {        //Tenim una fila seleccionada
                    //Posem els valors de la fila seleccionada als camps respectius
                    campNom.setText(model.getValueAt(filaSel, 0).toString());
                    campIngres.setText(model.getValueAt(filaSel, 1).toString().replaceAll("\\.", ","));
                    campEdad.setText(model.getValueAt(filaSel, 2).toString());
                    caixaClient.setSelected((Boolean) model.getValueAt(filaSel, 3));
                    genereBox.setSelectedItem(model.getValueAt(filaSel, 4));

                    //Activem la ingrestanya de la matrícula de l'Client seleccionat
          //          view.getPestanyes().setEnabledAt(1, true);
            //        view.getPestanyes().setTitleAt(1, "Matrícula de " + campNom.getText());

                    //Posem valor a el combo d'MPs
                    //view.getComboMP().setModel(modelo.getComboBoxModel());

                } else {                  //Hem deseleccionat una fila
                    //Posem els camps de text en blanc
                    campNom.setText("");
                    campIngres.setText("");

                    //Desactivem ingrestanyes
                    view.getPestanyes().setEnabledAt(1, false);
                    view.getPestanyes().setTitleAt(1, "Matrícula de ...");
                }
            }
        });

    }




    //TRACTAMENT D'EXCEPCIONS

    //2. Propietat lligada per controlar quan genero una excepció
    public static final String PROP_EXCEPCIO="excepcio";
    private DAOException excepcio;

    public DAOException getExcepcio() {
        return excepcio;
    }

    /**
     * Mètode que permet canviar el valor de la propietat excepcio
     * @param excepcio
     */
    public void setExcepcio(DAOException excepcio) {
        DAOException valorVell=this.excepcio;
        this.excepcio = excepcio;
        canvis.firePropertyChange(PROP_EXCEPCIO, valorVell,excepcio);
    }

    PropertyChangeSupport canvis=new PropertyChangeSupport(this);

    @Override
    /**
     * Mètode que reacciona davant de canvis en les propietats lligades
     */
    public void propertyChange(PropertyChangeEvent evt) {
        DAOException rebuda=(DAOException)evt.getNewValue();

        try {
            throw rebuda;
        } catch (DAOException e) {
            //Aquí farem ele tractament de les excepcions de l'aplicació
            switch (evt.getPropertyName()) {
                case PROP_EXCEPCIO:
                    switch (rebuda.getTipo()) {
                        case 0:
                            JOptionPane.showMessageDialog(null, rebuda.getMessage());
                            System.exit(1);
                            break;
                        case 1:
                            JOptionPane.showMessageDialog(null, rebuda.getMessage());
                            break;
                        case 2:
                            JOptionPane.showMessageDialog(null, rebuda.getMessage());
                            //this.view.getCampNom().setText(rebuda.getMissatge());
                            this.view.getCampNom().setSelectionStart(0);
                            this.view.getCampNom().setSelectionEnd(this.view.getCampNom().getText().length());
                            this.view.getCampNom().requestFocus();

                            break;
                        default:
                            JOptionPane.showMessageDialog(null, rebuda.getMessage());
                    }


            }
        }
    }



}
