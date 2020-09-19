package com.example.brawler.domaine.entité;

import java.io.File;

public class Utilisateur {
    /**
     * Paramètres de la classe Utilisateur
     */
    private String nom;
    private Niveau niveau;
    private String location;
    private byte[] photo;

    /**
     * Constructeur de la classe Utilisateur
     * @param nom de l'utilisateur
     * @param niveau actuel de l'utilisateur
     * @param location emplacement ou l'utilisateur se trouve
     * @param photo image qui sera sa photo de profil
     */
    public Utilisateur(String nom, Niveau niveau, String location, byte[] photo) {
        this.nom = nom;
        this.niveau = niveau;
        this.location = location;
        this.photo = photo;
    }

    /**
     * Accesseurs de la classe Utilisateur
     */
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
