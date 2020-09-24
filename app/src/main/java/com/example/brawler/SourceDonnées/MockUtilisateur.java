package com.example.brawler.SourceDonnées;

import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Statistique;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.domaine.intéracteur.SourceUtilisateur;

public class MockUtilisateur implements SourceUtilisateur {
    /**
     * @return Retourne un mock Utilisateur sans photo de profil.
     */
    @Override
    public Utilisateur getUtilisateur() {
        return new Utilisateur("DVilleda", Niveau.INTERMÉDIAIRE,"Montréal", 0,0);
    }
}
