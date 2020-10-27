package com.example.brawler.domaine.entité;

import java.io.File;

public class Utilisateur {
    private int id;
    private String nom;
    private String email;
    private String description;
    private String mdp;
    private Niveau niveau;
    private String location;
    private Statistique statistique;
    private byte[] photo;

    public Utilisateur(){

    }

    public Utilisateur(String email, String mdp, String prénom, String location, String description) {
        this.email = email;
        this.mdp = mdp;
        this.nom = prénom;
        this.location = location;
        this.description = description;
        //statistique par défault
        statistique = new Statistique(0,0);
    }

    public Utilisateur(int id, String nom, Niveau niveau, String location) {
        this.id = id;
        this.nom = nom;
        this.niveau = niveau;
        this.location = location;
        //statistique par défault
        statistique = new Statistique(0,0);
    }

    public Utilisateur(String nom, Niveau niveau, String location, int nombreVictoire, int nombrePerdu) {
        this.nom = nom;
        this.niveau = niveau;
        this.location = location;
        this.statistique = new Statistique(nombreVictoire, nombrePerdu);
    }

    public Utilisateur(String nom, Niveau niveau, String location, int nombreVictoire, int nombrePerdu, String leMdp) {
        this.nom = nom;
        this.niveau = niveau;
        this.location = location;
        this.statistique = new Statistique(nombreVictoire, nombrePerdu);
        this.mdp = leMdp;
    }

    public Utilisateur(int id, String nom, Niveau niveau, String location, String email,String description) {
        this.id = id;
        this.nom = nom;
        this.niveau = niveau;
        this.location = location;
        this.email = email;
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMdp(){return mdp;}

    public void setMdp(String leMdp){ this.mdp = leMdp;}

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Statistique getStatistique() {
        return statistique;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatistique(Statistique statistique) {
        this.statistique = statistique;
    }

}
