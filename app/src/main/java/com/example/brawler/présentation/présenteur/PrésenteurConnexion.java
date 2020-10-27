package com.example.brawler.présentation.présenteur;

import com.example.brawler.DAO.SourceUtilisateursApi;
import com.example.brawler.domaine.intéracteur.SourceUtilisateurs;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.vue.VueConnexion;
import com.example.brawler.MockDAO.SourceUtilisateurFictif.*;

import org.json.JSONException;

public class PrésenteurConnexion {

    private VueConnexion vue;
    private Modèle modèle;
    private SourceUtilisateursApi source;

    private Thread filEsclave = null;

    String resultat ="";

    public PrésenteurConnexion(VueConnexion vue, Modèle modèle){
        this.vue = vue;
        this.modèle = modèle;

    }

    public void setSource(SourceUtilisateursApi source) {
        this.source = source;
    }

    public String VerifierInformations(String email, String mdp) {
        String response;
        response = source.Authentifier(email, mdp);
        return response;
    }

    public String ThreadDeAuthentifer(final String email, final String mdp){
        filEsclave = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        resultat = VerifierInformations(email, mdp);
                    }
                });
        filEsclave.start();
        return resultat;
    }

}
