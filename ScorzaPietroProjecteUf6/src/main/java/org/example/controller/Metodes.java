package org.example.controller;

import org.example.model.exceptions.DAOException;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe que conté els mètodes que s'utilitzen a la vista
 */
public class Metodes extends Exception {
    /**
     * Comprova si els camps estan buits
     * @param nom nom del client
     * @param pes pes del client
     * @param edat edat del client
     * @return
     */
    public boolean campsBuits(String nom, String pes, String edat) {
        return nom.isEmpty() || pes.isEmpty() || edat.isEmpty();
    }

    /**
     * Comprova si el nom del client és correcte
     * @param input nom del client
     * @return
     */
    public boolean primeraMayuscula(String input) {
        Pattern pattern = Pattern.compile("^[A-Z][a-z]*$");
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Comprova si el pes del client és correcte
     * @param ingres
     * @return
     * @throws DAOException
     */
    public Double passarIngres(String ingres) throws DAOException {
        NumberFormat num = NumberFormat.getNumberInstance(Locale.getDefault());   //Creem un número que entèn la cultura que utilitza l'aplicació
        try {
            double ingresDouble = num.parse(ingres).doubleValue();
            return ingresDouble;
        }catch (Exception e){
            throw new DAOException(11);
        }
    }

    /**
     * Comprova si l'edat del client és correcte
     * @param edat
     * @return
     * @throws DAOException
     */
    public int passarEdat(String edat) throws DAOException {
        try {
            int edatInt = Integer.parseInt(edat.trim());
            return edatInt;
        }catch (Exception e){
            throw new DAOException(17);
        }
    }

}
