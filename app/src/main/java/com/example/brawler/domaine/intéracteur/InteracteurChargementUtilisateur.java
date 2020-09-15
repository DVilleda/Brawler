package com.example.brawler.domaine.intéracteur;

import com.example.brawler.domaine.entité.Utilisateur;

public class InteracteurChargementUtilisateur {
    SourceUtilisateur _sourceUtilisateur;
    Utilisateur _utilisateur;
    static InteracteurChargementUtilisateur instance;

    /**
     * L'accesseur du singleton qui crée une nouvelle instance dans le cas où elle n'existe pas.
     */
    public static InteracteurChargementUtilisateur getInstance(SourceUtilisateur sourceUtilisateur){
        if(instance == null){
            instance = new InteracteurChargementUtilisateur(sourceUtilisateur);
        }
        return instance;
    }

    private InteracteurChargementUtilisateur(SourceUtilisateur source) {
        assert source != null;

        _utilisateur = null;
        _sourceUtilisateur = source;
    }

    public Utilisateur chargerNouveauUtilisateur(){
        Utilisateur nouveauUtiliateur = _sourceUtilisateur.getUtilisateur();

        _utilisateur = nouveauUtiliateur;
        return _utilisateur;
    }

    public Utilisateur getUtilisateur(){
        if(_utilisateur == null){
            return chargerNouveauUtilisateur();
        }else {
            return _utilisateur;
        }
    }
}
