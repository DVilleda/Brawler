package com.example.brawler.domaine.entité;

import java.io.File;

public class Utilisateur {
    private String nom;
    private Niveau niveau;
    private String location;
    private byte[] photo;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
