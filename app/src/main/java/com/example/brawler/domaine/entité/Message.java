package com.example.brawler.domaine.entité;

import java.util.Date;

public class Message {

    int id;
    String texte;
    Utilisateur utilisateur;
    Date temps;
    Boolean lue;


    public Message(int id, String texte, Utilisateur utilisateur, Date temps, Boolean lue) {
        this.texte = texte;
        this.utilisateur = utilisateur;
        this.temps = temps;
        this.lue = lue;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Date getTemps() {
        return temps;
    }

    public void setTemps(Date temps) {
        this.temps = temps;
    }

    public Boolean getLue() {
        return lue;
    }

    public void setLue(Boolean lue) {
        this.lue = lue;
    }
}
