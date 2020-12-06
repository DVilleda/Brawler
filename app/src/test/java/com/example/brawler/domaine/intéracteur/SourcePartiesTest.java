package com.example.brawler.domaine.intéracteur;

import com.example.brawler.DAO.SourcePartiesApi;
import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Partie;
import com.example.brawler.domaine.entité.Utilisateur;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SourcePartiesTest {

    @Test
    public void testAccesseurPArtie() {
        SourceParties sourceParties = new SourceParties() {

            @Override
            public List<Partie> getDemandeParties() throws SourcePartiesApi.SourcePartieApiException {
                 return new ArrayList<Partie>();
            }

            @Override
            public void envoyerDemandePartie(int id) throws SourcePartiesApi.SourcePartieApiException {
                int idutilisateur = id;
            }

            @Override
            public void refuserDemandePartie(int id) throws SourcePartiesApi.SourcePartieApiException {
                int idutilisateur = id;
            }
        };
        InteracteurAquistionPartie interacteur = new InteracteurAquistionPartie(sourceParties);
        try {
            List<Partie> parties1 = interacteur.getDemandePartie();
            List<Partie> parties2 = interacteur.getDemandePartie();
            assertTrue( parties1.size() == parties2.size());
        } catch (SourcePartiesApi.SourcePartieApiException e) {
            fail( e.getMessage() );
        }
    }

    @Test
    public void intereacteurAccepterDemandePartie() {
        SourceParties sourceParties = new SourceParties() {

            @Override
            public List<Partie> getDemandeParties() throws SourcePartiesApi.SourcePartieApiException {
                final List<Partie> parties = new ArrayList<Partie>();;
                for(int i = 0; i < 10; i++) {
                    Partie partie = new Partie();
                    partie.setAdversaire(new Utilisateur(-1, "Jaques", Niveau.DÉBUTANT, "Montréal", "gmail@gmail.com", "cool guy"));
                    partie.setId(i);
                    parties.add(partie);
                }
                return parties;
            }

            @Override
            public void envoyerDemandePartie(int id) throws SourcePartiesApi.SourcePartieApiException {
                int idutilisateur = id;
            }

            @Override
            public void refuserDemandePartie(int id) throws SourcePartiesApi.SourcePartieApiException {
                int idutilisateur = id;
            }
        };
        InteracteurAquistionPartie interacteur = new InteracteurAquistionPartie(sourceParties);
        try {
            interacteur.enovyerDemandePartie(0);
            assert true;
        } catch (SourcePartiesApi.SourcePartieApiException e) {
            fail( e.getMessage() );
        } catch (UtilisateursException e) {
            fail( e.getMessage() );
        }
    }

    @Test
    public void intereacteurAccepterDemandePartieAvecChiffreNegarif() {
        SourceParties sourceParties = new SourceParties() {

            @Override
            public List<Partie> getDemandeParties() throws SourcePartiesApi.SourcePartieApiException {
                final List<Partie> parties = new ArrayList<Partie>();;
                for(int i = 0; i < 10; i++) {
                    Partie partie = new Partie();
                    partie.setAdversaire(new Utilisateur(-1, "Jaques", Niveau.DÉBUTANT, "Montréal", "gmail@gmail.com", "cool guy"));
                    partie.setId(i);
                    parties.add(partie);
                }
                return parties;
            }

            @Override
            public void envoyerDemandePartie(int id) throws SourcePartiesApi.SourcePartieApiException {
                int idutilisateur = id;
            }

            @Override
            public void refuserDemandePartie(int id) throws SourcePartiesApi.SourcePartieApiException {
                int idutilisateur = id;
            }
        };
        InteracteurAquistionPartie interacteur = new InteracteurAquistionPartie(sourceParties);
        try {
            interacteur.enovyerDemandePartie(-1);
            assert false;
        } catch (SourcePartiesApi.SourcePartieApiException e) {
            fail( e.getMessage() );
        } catch (UtilisateursException e) {
            assert true;
        }
    }

}