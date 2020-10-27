package com.example.brawler.présentation.présenteur;

import android.app.Activity;
import android.content.Intent;

import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.domaine.intéracteur.SourceUtilisateurs;
import com.example.brawler.domaine.intéracteur.UtilisateursException;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.vue.VueContacts;
import com.example.brawler.ui.activité.ConsulterMessageActivité;

import java.util.ArrayList;
import java.util.List;

public class PrésenteurContacts {
    private static final String EXTRA_ID_UTILSAITEUR = "com.brawler.idUtilisateur";

    VueContacts vueContacts;
    SourceUtilisateurs _source;
    Modèle _modèle;
    Activity activité;

    public PrésenteurContacts (VueContacts vue, Modèle modèle, Activity activité){
        this.vueContacts = vue;
        this._modèle = modèle;
        this.activité = activité;
    }

    public void setSource(SourceUtilisateurs source){this._source =source;}

    public void chargerListeContacts(){
        try{
            vueContacts.afficherContacts(_source.getUtilisateur());
        } catch (UtilisateursException e) {
            e.printStackTrace();
        }
    }

    public void chargerConversationUtilisateur(int idUtilisateur) {
        Intent intentConsulterMessage = new Intent(activité, ConsulterMessageActivité.class);
        intentConsulterMessage.putExtra(EXTRA_ID_UTILSAITEUR, idUtilisateur);
        activité.startActivity(intentConsulterMessage);
    }
}
