package com.example.brawler.MockDAO;

import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Statistique;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.domaine.intéracteur.SourceUtilisateur;
import com.example.brawler.domaine.intéracteur.UtilisateursException;

public class MockUtilisateur implements SourceUtilisateur {
    Utilisateur utilisateur = new Utilisateur("DVilleda", Niveau.INTERMÉDIAIRE,"Montréal", 0,0);
    /**
     * @return Retourne un mock Utilisateur sans photo de profil.
     */
    @Override
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    @Override
    public Utilisateur getUtilisateurParId(int id, boolean doitLireImage) throws UtilisateursException {
        return null;
    }

    @Override
    public void setUtilisateur(Utilisateur utilisateur) {

    }

    @Override
    public Utilisateur getUtilisateurActuel() throws UtilisateursException {
        return null;
    }
}
