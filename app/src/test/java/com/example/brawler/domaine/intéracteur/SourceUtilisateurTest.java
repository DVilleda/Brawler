package com.example.brawler.domaine.intéracteur;

import com.example.brawler.DAO.SourceProfilApi;
import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Utilisateur;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class SourceUtilisateurTest {

    @Test
    /**
     * Test pour verifier si la methode getUtilisateur marche dans l'interacteur
     */
    public void testAccesseurUtilisateur(){
        SourceUtilisateur sourceUtilisateur = new SourceUtilisateur() {
            @Override
            public Utilisateur getUtilisateur() throws UtilisateursException {
                return new Utilisateur();
            }

            @Override
            public Utilisateur getUtilisateurParId(int id, boolean doitLireImage) throws UtilisateursException {
                return new Utilisateur();
            }

            @Override
            public void setUtilisateur(Utilisateur utilisateur) throws UtilisateursException {
                Utilisateur utilisateur1 = utilisateur;
            }

            @Override
            public Utilisateur getUtilisateurActuel() throws UtilisateursException {
                return new Utilisateur();
            }
        };
        InteracteurChargementProfil interacteur = new InteracteurChargementProfil(sourceUtilisateur);
        try{
            Utilisateur utilisateur = interacteur.getUtilisateur();
            assertNotNull(utilisateur);
        } catch (UtilisateursException e){
            fail(e.getMessage());
        }
    }

    @Test
    /**
     * Test pour verifier si la methode setUtilisateur marche dans l'interacteur
     */
    public void testModifierUtilisateur(){
        SourceUtilisateur sourceUtilisateur = new SourceUtilisateur() {
            Utilisateur utilisateurActuel = new Utilisateur(1,"Dan", Niveau.EXPERT,
                    "Toronto","dan@crosemont.qc.ca","rien",null);
            @Override
            public Utilisateur getUtilisateur() throws UtilisateursException {
                return null;
            }

            @Override
            public Utilisateur getUtilisateurParId(int id, boolean doitLireImage) throws UtilisateursException {
                return null;
            }

            @Override
            public void setUtilisateur(Utilisateur utilisateur) throws UtilisateursException {
                utilisateurActuel = utilisateur;
            }

            @Override
            public Utilisateur getUtilisateurActuel() throws UtilisateursException {
                return null;
            }
        };
        InteracteurChargementProfil interacteur = new InteracteurChargementProfil(sourceUtilisateur);
        try {
            Utilisateur utilisateurActuel = new Utilisateur(1,"Dan", Niveau.EXPERT,
                    "Toronto","dan@crosemont.qc.ca","description creative :)",null);
            interacteur.setUtilisateur(utilisateurActuel);
            assert true;
        } catch (UtilisateursException e) {
            fail(e.getMessage());
        }
    }

    @Test
    /**
     * Test pour verifier que l'interacteur peut utiliser la methode pour obtenir l'Utilisateur
     * stocker dans l'instance de l'interacteur
     */
    public void testGetUtilisateurActuel() {
        SourceUtilisateur sourceUtilisateur = new SourceUtilisateur() {
            Utilisateur utilisateurActuel = new Utilisateur(1, "Dan", Niveau.EXPERT,
                    "Toronto", "dan@crosemont.qc.ca", "rien", null);

            @Override
            public Utilisateur getUtilisateur() throws UtilisateursException {
                return null;
            }

            @Override
            public Utilisateur getUtilisateurParId(int id, boolean doitLireImage) throws UtilisateursException {
                return null;
            }

            @Override
            public void setUtilisateur(Utilisateur utilisateur) throws UtilisateursException {
                utilisateurActuel = utilisateur;
            }

            @Override
            public Utilisateur getUtilisateurActuel() throws UtilisateursException {
                return utilisateurActuel;
            }
        };
        InteracteurChargementProfil interacteur = new InteracteurChargementProfil(sourceUtilisateur);
        try {
            Utilisateur testUtilisateur = interacteur.chargerUtilisateurActuel();
            assertEquals(1, testUtilisateur.getId());
            assertEquals("Dan", testUtilisateur.getNom());
        } catch (UtilisateursException e) {
            fail(e.getMessage());
        }
    }
}