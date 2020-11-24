package com.example.brawler.domaine.intéracteur;

import com.example.brawler.DAO.SourceUtilisateursApi;

public class LocalisationUtilisateur implements ILocalisationUtilisateur{

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