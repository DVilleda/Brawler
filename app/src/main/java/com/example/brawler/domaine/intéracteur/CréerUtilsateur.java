package com.example.brawler.domaine.intéracteur;

import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Utilisateur;

public class CréerUtilsateur implements InterfaceUtiliasteur.créerUtiliasteur {

    public Utilisateur créerUnUtilisateur (String nom, Niveau niveau, String location, int partieGagnée, int partiePerdue) throws Exception {
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
        } else if(partieGagnée < 0) {
            exception = "le nombre de victoire ne peut pas être négatif";
            validation = false;
        } else if (partiePerdue < 0) {
            exception = "le nombre de partie perdu ne peut aps être plus pas être négatif";
            validation = false;
        }
        if(!validation) {
            throw new Exception(exception);
        }
        return new Utilisateur(nom, niveau, location, partieGagnée, partiePerdue);
    }

}