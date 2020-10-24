package com.example.brawler.domaine.intéracteur;

import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Utilisateur;

/**
 * Cette classe à pour but de faire la gestion des interactions entre la source de données et le UI
 * de l'application.
 */
public class InteracteurChargementUtilisateur{
    SourceUtilisateur _sourceUtilisateur;
    Utilisateur _utilisateur;
    static InteracteurChargementUtilisateur instance;

    /**
     * L'accesseur du singleton qui crée une nouvelle instance dans le cas où elle n'existe pas.
     */
    public static InteracteurChargementUtilisateur getInstance(SourceUtilisateur sourceUtilisateur){
        if(instance == null)
            instance = new InteracteurChargementUtilisateur(sourceUtilisateur);

        return instance;
    }

    /**
     * Constructeur de la classe
     * @param source
     */
    private InteracteurChargementUtilisateur(SourceUtilisateur source) {
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
        //TODO Modifier pour changer les donnees dans la source aussi
    }
}
