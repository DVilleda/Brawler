package com.example.brawler.présentation.modèle;

import com.example.brawler.domaine.entité.Utilisateur;

public class Modèle {

    private Utilisateur utilisateurActuel;
    private Utilisateur utilisateurEnRevue;

    public Utilisateur getUtilisateurActuel() {
        return utilisateurActuel;
    }

    public void setUtilisateurActuel(Utilisateur utilisateurActuel) {
        this.utilisateurActuel = utilisateurActuel;
    }

    public Utilisateur getUtilisateurEnRevue() {
        return utilisateurEnRevue;
    }

    public void setUtilisateurEnRevue(Utilisateur utilisateurEnRevue) {
        this.utilisateurEnRevue = utilisateurEnRevue;
    }
}
