package com.example.brawler.présentation.présenteur;

import com.example.brawler.domaine.intéracteur.SourceUtilisateurs;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.vue.VueConnexion;
import com.example.brawler.MockDAO.SourceUtilisateurFictif.*;

public class PrésenteurConnexion {

    private VueConnexion vue;
    private Modèle modèle;
    private SourceUtilisateurs source;

    public PrésenteurConnexion(VueConnexion vue, Modèle modèle){
        this.vue = vue;
        this.modèle = modèle;

    }

    public boolean VerifierInformations(String uName, String mdp){
        boolean estValide = false;
        String nomDUtilisateur = modèle.getUtilisateur().getNom();
        String motDePasseEnregistré = modèle.getUtilisateur().getMdp();

        if (nomDUtilisateur.contentEquals(uName) && motDePasseEnregistré.contentEquals(motDePasseEnregistré)){
            estValide = true;
        }

        return estValide;
    }

}
