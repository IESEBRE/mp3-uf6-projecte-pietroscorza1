package org.example.view;

import org.example.model.entities.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ModelComponentsVisuals {

    private DefaultTableModel modelTaulaClient;
    private DefaultTableModel modelTaulaMat;


    public DefaultTableModel getModelTaulaClient() {
        return modelTaulaClient;
    }

    public DefaultTableModel getModelTaulaMat() {
        return modelTaulaMat;
    }

    public ModelComponentsVisuals() {


        //Anem a definir l'estructura de la taula dels Clients
        modelTaulaClient =new DefaultTableModel(new Object[]{"nom","Ingres","Edad","Actiu","Genere"},0){
            /**
             * Returns true regardless of parameter values.
             * @param row    the row whose value is to be queried
             * @param column the column whose value is to be queried
             */
            @Override
            public boolean isCellEditable(int row, int column) {

                //Fem que TOTES les cel·les de la columna 1 de la taula es puguen editar
                //if(column==1) return true;
                return false;
            }



            //Permet definir el tipo de cada columna
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                        case 1:
                        return Double.class;
                    case 2:
                        return Integer.class;
                    case 3:
                        return Boolean.class;
                    case 4:
                        return String.class;
                        default:
                        return Object.class;
                }
            }
        };
        //Anem a definir l'estructura de la taula de les matrícules
        modelTaulaMat =new DefaultTableModel(new Object[]{"MP","Nota"},0){
            /**
             * Returns true regardless of parameter values.
             *
             * @param row    the row whose value is to be queried
             * @param column the column whose value is to be queried
             * @return true
             * @see #setValueAt
             */
            @Override
            public boolean isCellEditable(int row, int column) {

                //Fem que TOTES les cel·les de la columna 1 de la taula es puguen editar
                //if(column==1) return true;
                return false;
            }

            //Permet definir el tipo de cada columna
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Integer.class;
                    default:
                        return Object.class;
                }
            }
        };

    }
}
