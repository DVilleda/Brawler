package com.example.brawler.domaine.intéracteur;

import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Utilisateur;

import java.util.ArrayList;



public class InteracteurAquisitionUtilisateur {

    private SourceUtilisateurs source;
    private ArrayList<Utilisateur> utilisateurs;
    private static InteracteurAquisitionUtilisateur instance;

    public static InteracteurAquisitionUtilisateur getInstance(SourceUtilisateurs source) {
        if (instance == null)
            instance =  new InteracteurAquisitionUtilisateur(source);

        return  instance;
    }

    private InteracteurAquisitionUtilisateur(SourceUtilisateurs source) {

        utilisateurs = new ArrayList<>();
        this.source = source;
    }

    public Utilisateur getUtilsaiteur(String location, Niveau niveau){
        if(utilisateurs.size() == 0) {
            getNouvelUtilsaiteur(location, niveau);
        }
        return utilisateurs.get(utilisateurs.size() - 1);
    }

    public Utilisateur getNouvelUtilsaiteur(String location, Niveau niveau){
        utilisateurs.add(source.getNouvelleUtilisateur(location, niveau));
        return utilisateurs.get(utilisateurs.size() - 1);
    }
}
