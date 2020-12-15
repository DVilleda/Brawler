package com.example.brawler.domaine.intéracteur;

import com.example.brawler.domaine.entité.Mouvement;
import com.example.brawler.domaine.entité.Partie;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class SourceDeroulementPartieTest {

    @Test
    public void testGetPartieParID(){
        SourceDeroulementPartie sourceDeroulementPartie = new SourceDeroulementPartie() {
            @Override
            public Partie getPartieParID(int idPartie) throws PartieException {
                return new Partie(1,true,3,false,null);
            }

            @Override
            public void EnvoyerMouvement(int idPartie, int idAdversaire, String mouvement) throws PartieException {
                String mouvementSoi = mouvement;
            }

        };
        InteracteurJouerPartie interacteur = new InteracteurJouerPartie(sourceDeroulementPartie);
        try{
            Partie partie = interacteur.getPartieParID(1);
            assertNotNull(partie);
        } catch (PartieException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testEnvoyerMouvement(){
        SourceDeroulementPartie sourceDeroulementPartie = new SourceDeroulementPartie() {
            List<Mouvement> liste = Arrays.asList(
                    new Mouvement(1,"Roche","Papier"),new Mouvement(2,"Papier",""));
            Partie partie = new Partie(1,true,2,false,liste);
            @Override
            public Partie getPartieParID(int idPartie) throws PartieException {
                return new Partie(1,true,3,false,null);
            }

            @Override
            public void EnvoyerMouvement(int idPartie, int idAdversaire, String mouvement) throws PartieException {
                List<Mouvement> liste = Arrays.asList(
                        new Mouvement(1,"Roche","Papier"),new Mouvement(2,"Papier",mouvement));
                partie.setMouvementsPartie(liste);
            }

        };
        InteracteurJouerPartie interacteur = new InteracteurJouerPartie(sourceDeroulementPartie);
        try {
            interacteur.envoyerMouvement(1,2,"Papier");
            assert true;
        } catch (PartieException e) {
            fail(e.getMessage());
        }
    }
}