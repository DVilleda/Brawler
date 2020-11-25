package com.example.brawler.présentation.présenteur;

import com.example.brawler.DAO.SourceUtilisateursApi;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.domaine.intéracteur.InscriptionUtilisateur;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.vue.VueCréationCompte;

import org.json.JSONException;
import org.json.JSONObject;

public class PrésenteurCréationCompte {

    private VueCréationCompte vue;
    private Modèle modèle;
    private SourceUtilisateursApi source;
    private InscriptionUtilisateur interacteur;

    String resultat ="";


    private Thread filEsclave = null;

    public PrésenteurCréationCompte(VueCréationCompte nouvelleVue, Modèle nouveauModèle) {
        this.vue = nouvelleVue;
        this.modèle = nouveauModèle;
    }

    public void setSource(SourceUtilisateursApi source) {
        this.source = source;
    }
    public void setInteracteur(InscriptionUtilisateur interacteur){this.interacteur = interacteur;}


    public String creationDeCompte(String email, String mdp, String prénom, String location, String description ){
        JSONObject resultatEnJSON = new JSONObject();

        try {
            Utilisateur createdUser = interacteur.VerifierInscriptionUtilisateur(email, mdp, prénom, location, description);

            resultatEnJSON = source.creerNouveauUtilisateur(email,
                    createdUser.getMdp(),
                    createdUser.getNom(),
                    createdUser.getLocation(),
                    createdUser.getDescription());

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            resultat = resultatEnJSON.getString("statut");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return resultat;
    }

    public String ThreadDeCreationDeCompte(final String email, final String mdp, final String prénom, final String  location, final String  description){
        filEsclave = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        resultat = creationDeCompte(email, mdp, prénom, location, description );
                    }
                });
        filEsclave.start();
        return resultat;
    }

}
