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

    public PrésenteurRechercheMatch(VueRechercheMatch vue, Modèle modèle) {
        this.vue = vue;
        this.modèle = modèle;
    }

    public void setSource(SourceUtilisateurs source) {
        this.source = source;
    }

    public void prochainUtilsateur() {
        modèle.setUtilisateurEnRevue(InteracteurAquisitionUtilisateur.getInstance(source).getNouvelUtilsaiteur("Montréal", Niveau.DÉBUTANT));
        vue.afficherUtilisateur(modèle.getUtilisateurEnRevue());
    }
}
