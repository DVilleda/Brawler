package com.example.brawler.présentation.présenteur;

import android.app.Activity;

import com.example.brawler.domaine.intéracteur.InteracteurChargementUtilisateur;
import com.example.brawler.domaine.intéracteur.SourceUtilisateur;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.vue.VueProfil;

public class PrésenteurProfil {

    VueProfil vueProfil;
    SourceUtilisateur _source;
    Activity _activity;
    Modèle _modèle;

    public PrésenteurProfil(Activity activity, VueProfil vue, Modèle modèle){
        _activity = activity;
        _modèle = modèle;
        vueProfil = vue;
    }

    public void setSourceUtilisateur(SourceUtilisateur source){
        _source = source;
    }

    public void setUtilisateur(){
        _modèle.setUtilisateur(InteracteurChargementUtilisateur.getInstance(_source).chargerNouveauUtilisateur());
        vueProfil.afficherUtilisateur(_modèle.getUtilisateur());
    }
}
