package com.example.brawler.présentation.présenteur;

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
    }

    public void setSource(SourceUtilisateurs source) {
        this.source = source;
    }

    public void prochainUtilsateur() {

        if (modèle.getListUtilisateurs().size() == 0) {
            chargerNouvelleUtilisateur();
        }

        vue.afficherUtilisateur(modèle.getUtilisateurEnRevue());
    }

    public void changerRecherche() {
        modèle.viderList();
        parNiveau = !parNiveau;
    }

    private void chargerNouvelleUtilisateur() {
        if (parNiveau) {
            modèle.setUtilisateurEnRevue(InteracteurAquisitionUtilisateur.getInstance(source).getNouvelUtilsaiteurParNiveau("Montréal", Niveau.DÉBUTANT));
        } else {
            modèle.setUtilisateurEnRevue(InteracteurAquisitionUtilisateur.getInstance(source).getNouvelleUtilisateur("Montréal"));
        }
    }

}
