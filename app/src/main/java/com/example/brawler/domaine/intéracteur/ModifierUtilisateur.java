package com.example.brawler.domaine.intéracteur;

import com.example.brawler.domaine.entité.Utilisateur;

public class ModifierUtilisateur implements InterfaceUtiliasteur.modifierUtilisateur {
    @Override
    public Utilisateur modiifierNom(Utilisateur utilisateur, String nouveauNom) throws Exception {
        boolean validation = true;
        String messageErreur = "";

        if(nouveauNom.trim().isEmpty() || nouveauNom.equals(null)){
            validation = false;
            messageErreur = "le nouveau nom est vide";
        }

        if(!validation) {
            throw new Exception(messageErreur);
        } else {
            utilisateur.setNom(nouveauNom);
            return utilisateur;
        }
    }

    @Override
    public Utilisateur modifierLocation(Utilisateur utilisateur, String nouvelleLocation) throws Exception {
        boolean validation = true;
        String messageErreur = "";

        if(nouvelleLocation.trim().isEmpty() || nouvelleLocation.equals(null)){
            validation = false;
            messageErreur = "le nouvelle location est vide";
        }

        if(!validation) {
            throw new Exception(messageErreur);
        } else {
            utilisateur.setLocation(nouvelleLocation);
            return utilisateur;
        }
    }
}
