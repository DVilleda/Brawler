package com.example.brawler.domaine.intéracteur;

import android.util.Log;

import com.example.brawler.domaine.entité.Utilisateur;

public class InscriptionUtilisateur {

    public Utilisateur VerifierInscriptionUtilisateur (String email,
                                               String mdp,
                                               String prénom,
                                               String location,
                                               String description) throws Exception {
        boolean validation = true;
        String exception = "";
        String regexMdp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=\\S+$).{8,}$";
        Utilisateur leUser = new Utilisateur(email, mdp, prénom, location, description);

        if(email.trim().isEmpty() || email.equals(null)) {
            exception = "L'email ne peut pas etre vide";
            validation = false;
        } else if (!mdp.matches(regexMdp)) {
            exception = "Le mot de passe doit contenir au moins une majuscule, " +
                    "une minuscule, un chiffre et doit contenir 8 caracteres ou plus";
            validation = false;
        } else if (prénom.trim().isEmpty() || prénom.equals(null)) {
            exception = "Vous devez rentrer un prénom";
            validation = false;
        } else if (location.trim().isEmpty() || location.equals(null)) {
            exception = "Vous devez rentrer une location";
            validation = false;
        }
        else if (description.trim().isEmpty() || description.equals(null)) {
            exception = "Vous devez rentrer une description";
            validation = false;
        }
        if(!validation) {
            throw new Exception(exception);
        }
        return leUser;
    }
}
