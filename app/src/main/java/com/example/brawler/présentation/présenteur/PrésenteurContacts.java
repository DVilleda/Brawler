package com.example.brawler.présentation.présenteur;

import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.domaine.intéracteur.SourceUtilisateurs;
import com.example.brawler.domaine.intéracteur.UtilisateursException;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.vue.VueContacts;

import java.util.ArrayList;
import java.util.List;

public class PrésenteurContacts {

    VueContacts vueContacts;
    SourceUtilisateurs _source;
    Modèle _modèle;

    public PrésenteurContacts (VueContacts vue, Modèle modèle){
        this.vueContacts = vue;
        this._modèle = modèle;
    }

    public void setSource(SourceUtilisateurs source){this._source =source;}

    public void chargerListeContacts(){
        try{
            vueContacts.afficherContacts(_source.getUtilisateur());
        } catch (UtilisateursException e) {
            e.printStackTrace();
        }
    }
}
