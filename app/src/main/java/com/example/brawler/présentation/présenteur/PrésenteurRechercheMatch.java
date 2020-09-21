package com.example.brawler.présentation.présenteur;

import android.util.Log;

import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.intéracteur.InteracteurAquisitionUtilisateur;
import com.example.brawler.domaine.intéracteur.SourceUtilisateurs;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.vue.VueRechercheMatch;

public class PrésenteurRechercheMatch {

    private VueRechercheMatch vue;
    private Modèle modèle;
    private SourceUtilisateurs source;
    private Boolean parNiveau;

    public PrésenteurRechercheMatch(VueRechercheMatch vue, Modèle modèle) {
        this.vue = vue;
        this.modèle = modèle;
        this.parNiveau = false;
    }

    public void setSource(SourceUtilisateurs source) {
        this.source = source;
    }

    public void prochainUtilsateur() {

        if (modèle.getListUtilisateurs().size() < 1 || modèle.getListUtilisateurs().size() > modèle.getUtilisateurEnRevue()) {
            chargerNouvelleUtilisateur();
        } else {
            modèle.prochainUtilisateur();
        }

        vue.afficherUtilisateur(modèle.getUtilisateurActuel());
    }

    public void changerRecherche(Boolean bool) {
        if(bool != parNiveau){
            parNiveau = bool;
            modèle.viderListe();
            prochainUtilsateur();
        }
    }

    private void chargerNouvelleUtilisateur() {
        modèle.viderListe();

        if (parNiveau) {
            modèle.setListeUtilisateurs(InteracteurAquisitionUtilisateur.getInstance(source).getNouvelUtilsaiteurParNiveau("Montréal", Niveau.DÉBUTANT));
        } else {
            modèle.setListeUtilisateurs(InteracteurAquisitionUtilisateur.getInstance(source).getNouvelleUtilisateur("Montréal"));
        }
    }

}
