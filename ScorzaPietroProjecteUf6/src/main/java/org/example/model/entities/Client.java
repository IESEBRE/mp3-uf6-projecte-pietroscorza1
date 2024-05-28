package org.example.model.entities;

import java.util.Collection;

/**
 * Classe que conté els atributs del client

 */
public class Client {

    private int id;
    private String nom;
    private double ingres;
    private int edad;
    private boolean matriculat;
    private String genere;


    /**
     * Constructor de la classe Client
     * @param nom nom del client
     * @param pes pes del client
     * @param edad edat del client
     * @param actiu si el client està matriculat o no
     * @param genere genere del client
     */
    public Client(String nom, double pes, int edad, boolean actiu, String genere) {
        this.nom = nom;
        this.ingres = pes;
        this.edad = edad;
        this.matriculat = actiu;
        this.genere = genere;
    }


    public Client() {

    }


    public String getGenere() {
        return genere;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getIngres() {
        return ingres;
    }

    public void setingres(double ingres) {
        this.ingres = ingres;
    }

    public boolean getMatriculat() {
        return matriculat;
    }

    public void setMatriculat(boolean matriculat) {
        this.matriculat = matriculat;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public long getID() {
        return id;
    }

    public void setGenere(String selectedItem) {
        this.genere = selectedItem;
    }
}