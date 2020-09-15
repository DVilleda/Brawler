package com.example.brawler.présentation.modèle;

import com.example.brawler.domaine.entité.Utilisateur;

public class Modèle {
    private Utilisateur utilisateur;

    public Utilisateur getUtilisateur(){
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
