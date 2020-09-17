package com.example.brawler.domaine.intéracteur;

import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Utilisateur;

public class CréerUtilsateur implements InterfaceUtiliasteur.créerUtiliasteur {

    public Utilisateur créerUnUtilisateur (String nom, Niveau niveau, String location) throws Exception {
        Boolean validation = true;
        String exception = "";

        if(nom.trim().isEmpty() || nom.equals(null)) {
            exception = "Le nom est vide";
            validation = false;
        } else if (niveau.equals(null)) {
            exception = "l'utilsiateur n'a pas de niveau";
            validation = false;
        } else if (location.trim().isEmpty() || location.equals(null)) {
            exception = "l'utilisateur n'à pas de location";
            validation = false;
        }
        if(!validation) {
            throw new Exception(exception);
        }
        return new Utilisateur(nom, niveau, location);
    }

}