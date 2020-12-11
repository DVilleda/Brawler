package com.example.brawler.domaine.intéracteur;

import com.example.brawler.DAO.SourceUtilisateursApi;

public class LocalisationUtilisateur implements ILocalisationUtilisateur{

    private static LocalisationUtilisateur instance;

    public static LocalisationUtilisateur getInstance() {
        if (instance == null)
            instance =  new LocalisationUtilisateur();
        return  instance;
    }


    public LocalisationUtilisateur(){}

    private SourceUtilisateursApi source;

    public String getLocalisationActuelle() {

        return null;
    }

    @Override
    public boolean setLocalisation(String clé, String localisation) {
        source = new SourceUtilisateursApi(clé);
        return source.setLocalisation(localisation);
    }
}