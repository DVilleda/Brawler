package com.example.brawler.domaine.intéracteur;

import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Utilisateur;

public class CréerUtilsateur {

    public Utilisateur créerUnUtilisateur (String nom, Niveau niveau, String location) throws Exception {
        Boolean validation = true;
        String exception = "";

        if(!validation) {
            throw new Exception(exception);
        }
        return new Utilisateur(nom, niveau, location);
    }

}
