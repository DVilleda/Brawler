package com.example.brawler.domaine.intéracteur;

import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Utilisateur;

import java.util.ArrayList;



public class InteracteurAquisitionUtilisateur {

    private SourceUtilisateurs source;
    private static InteracteurAquisitionUtilisateur instance;

    public static InteracteurAquisitionUtilisateur getInstance(SourceUtilisateurs source) {
        if (instance == null)
            instance =  new InteracteurAquisitionUtilisateur(source);

        return  instance;
    }

    private InteracteurAquisitionUtilisateur(SourceUtilisateurs source) {

        this.source = source;
    }

    public ArrayList<Utilisateur> getNouvelleUtilisateur(String location){
        return source.getUtilisateur(location);
    }

    public ArrayList<Utilisateur> getNouvelUtilsaiteurParNiveau(String location, Niveau niveau){
        return source.getNouvelleUtilisateurParNiveau(location, niveau);
    }

}
