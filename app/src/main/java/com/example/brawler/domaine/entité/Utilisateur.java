package com.example.brawler.domaine.entité;

import android.location.Location;

public class Utilisateur {
    private String nom;
    private Niveau niveau;
    private String location;

    public Utilisateur(String nom, Niveau niveau, String location) {
        this.nom = nom;
        this.niveau = niveau;
        this.location = location;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }
}
