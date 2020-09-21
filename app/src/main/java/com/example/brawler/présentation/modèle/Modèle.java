package com.example.brawler.présentation.modèle;

import com.example.brawler.domaine.entité.Utilisateur;

import java.util.ArrayList;

public class Modèle {

    private ArrayList<Utilisateur> listUtilisateurs;
    private Utilisateur utilisateurEnRevue;

    public ArrayList<Utilisateur> getListUtilisateurs() {
        return listUtilisateurs;
    }

    public void setListUtilisateurs(ArrayList<Utilisateur> listUtilisateurs) {
        this.listUtilisateurs = listUtilisateurs;
    }

    public Utilisateur getUtilisateurEnRevue() {
        return utilisateurEnRevue;
    }

    public void setUtilisateurEnRevue(Utilisateur utilisateurEnRevue) {
        this.utilisateurEnRevue = utilisateurEnRevue;
    }

    public void viderList() {
        listUtilisateurs.clear();
    }

}
