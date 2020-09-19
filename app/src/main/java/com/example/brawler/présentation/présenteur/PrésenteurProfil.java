package com.example.brawler.présentation.présenteur;

import android.app.Activity;

import com.example.brawler.domaine.intéracteur.InteracteurChargementUtilisateur;
import com.example.brawler.domaine.intéracteur.SourceUtilisateur;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.vue.VueProfil;

public class PrésenteurProfil {

    /**
     * Paramètres du présenteur
     */
    VueProfil vueProfil;
    SourceUtilisateur _source;
    Modèle _modèle;

    /**
     * Constructeur du présenteur de la vue Profil
     */
    public PrésenteurProfil(VueProfil vue, Modèle modèle){
        _modèle = modèle;
        vueProfil = vue;
    }

    /**
     * Méthode qui permet de définir la source de données pour le profil
     * @param source
     */
    public void setSourceUtilisateur(SourceUtilisateur source){
        _source = source;
    }

    /**
     * Place l'utilisateur dans le modèle puis affiche ce même utilisateur dans le fragment profil
     */
    public void setUtilisateur(){
        _modèle.setUtilisateur(InteracteurChargementUtilisateur.getInstance(_source).chargerNouveauUtilisateur());
        vueProfil.afficherUtilisateur(_modèle.getUtilisateur());
    }

    /**
     * Permet de changer la visibilité des données personnelles dans le profil publique
     */
    public void setVisibleInfos(){
        vueProfil.expandInfosProfil();
    }
}
