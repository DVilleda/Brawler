package com.example.brawler.domaine.intéracteur;

import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Utilisateur;

import java.util.List;


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

    public List<Utilisateur> getNouvelleUtilisateur() throws UtilisateursException {
        return source.getUtilisateur();
    }

    public List<Utilisateur> getNouvelUtilsaiteurParNiveau(Niveau niveau) throws UtilisateursException {
        return source.getNouvelleUtilisateurParNiveau(niveau);
    }

}
