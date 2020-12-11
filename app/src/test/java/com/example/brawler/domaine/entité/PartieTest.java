package com.example.brawler.domaine.entité;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PartieTest {

    @Test
    public void adversaire() {
        Partie partie = new Partie();
        final Utilisateur utilisateur = new Utilisateur(0, "Jaques", Niveau.DÉBUTANT, "Montréal", "gmail@gmail.com", "cool guy");
        partie.setAdversaire(utilisateur);

        assertEquals(utilisateur, partie.getAdversaire());
    }

    @Test
    public void gagnant() {
        Partie partie = new Partie();
        final Utilisateur utilisateur = new Utilisateur(0, "Jaques", Niveau.DÉBUTANT, "Montréal", "gmail@gmail.com", "cool guy");
        partie.setGagnant(utilisateur);

        assertEquals(utilisateur, partie.getGagnant());
    }

    @Test
    public void tour() {
        Partie partie = new Partie();
        final List<Tour> tour= new ArrayList<Tour>();
        partie.setTour(tour);

        assertEquals(tour, partie.getTour());
    }

    @Test
    public void Id() {
        Partie partie = new Partie();
        final int i = 1;
        partie.setId(i);

        assertEquals(partie.getId(), i);
    }

}