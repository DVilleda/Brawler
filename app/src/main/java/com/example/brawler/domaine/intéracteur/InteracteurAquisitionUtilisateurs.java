package com.example.brawler.domaine.intéracteur;

import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Utilisateur;

import java.util.List;


public class InteracteurAquisitionUtilisateurs {

    private SourceUtilisateurs source;
    private static InteracteurAquisitionUtilisateurs instance;

    public static InteracteurAquisitionUtilisateurs getInstance(SourceUtilisateurs source) {
        instance =  new InteracteurAquisitionUtilisateurs(source);
        return  instance;
    }

    private InteracteurAquisitionUtilisateurs(SourceUtilisateurs source) {
        this.source = source;
    }

    public List<Utilisateur> getNouvelleUtilisateur() throws UtilisateursException {
        return source.getUtilisateur();
    }

    public List<Utilisateur> getNouvelUtilsaiteurParNiveau(Niveau niveau) throws UtilisateursException {
        return source.getNouvelleUtilisateurParNiveau(niveau);
    }

}
