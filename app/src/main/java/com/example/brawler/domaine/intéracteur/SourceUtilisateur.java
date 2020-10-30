package com.example.brawler.domaine.intéracteur;

import com.example.brawler.domaine.entité.Message;
import com.example.brawler.domaine.entité.Utilisateur;

import java.util.List;

/**
 * Interface qui contiendra les differentes methodes pour traiter les objet de type Utilisateur
 */
public interface SourceUtilisateur {
    public Utilisateur getUtilisateur() throws  UtilisateursException;
    public void setUtilisateur(Utilisateur utilisateur) throws UtilisateursException;
}
