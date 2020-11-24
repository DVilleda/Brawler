package com.example.brawler.domaine.intéracteur;

import com.example.brawler.domaine.entité.Utilisateur;

/**
 * Cette classe à pour but de faire la gestion des interactions entre la source de données et le UI
 * de l'application.
 */
public class InteracteurChargementProfil {
    SourceUtilisateur _sourceUtilisateur;
    Utilisateur _utilisateur;
    static InteracteurChargementProfil instance;

    /**
     * L'accesseur du singleton qui crée une nouvelle instance dans le cas où elle n'existe pas.
     */
    public static InteracteurChargementProfil getInstance(SourceUtilisateur sourceUtilisateur){
        if(instance == null)
            instance = new InteracteurChargementProfil(sourceUtilisateur);

        return instance;
    }

    /**
     * Constructeur de la classe
     * @param source
     */
    private InteracteurChargementProfil(SourceUtilisateur source) {
        this._sourceUtilisateur = source;
    }

    /**
     * Cette methode va charger un nouvel utilisateur et le placer comme utilisateur actuel
     * @return l'utilisateur chargé par l'application
     */
    public Utilisateur chargerUtilisateurActuel() throws UtilisateursException {
        return _utilisateur;
    }

    public Utilisateur getUtilisateur() throws UtilisateursException {
        _utilisateur = _sourceUtilisateur.getUtilisateur();
        return _utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) throws UtilisateursException {
        _utilisateur = utilisateur;
        _sourceUtilisateur.setUtilisateur(utilisateur);
    }
}
