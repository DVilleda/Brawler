package com.example.brawler.domaine.entité;

import java.util.Date;

public class Message {
    String texte;
    Utilisateur utilisateur;
    Date temps;

    public Message(String texte, Utilisateur utilisateur, Date temps) {
        this.texte = texte;
        this.utilisateur = utilisateur;
        this.temps = temps;
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
}
