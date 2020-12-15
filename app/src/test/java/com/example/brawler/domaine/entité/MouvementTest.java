package com.example.brawler.domaine.entité;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Classe de test pour les getter et setter de la classe Mouvement
 */
public class MouvementTest {

    @Test
    public void getTour() {
        Mouvement mock = new Mouvement(1,"Roche","Papier");
        assertEquals(1,mock.getTour());
    }

    @Test
    public void setTour() {
        Mouvement mock = new Mouvement(1,"Roche","Papier");
        mock.setTour(3);
        assertEquals(3,mock.getTour());
    }

    @Test
    public void getMouvementAdv() {
        Mouvement mock = new Mouvement(1,"Roche","Papier");
        assertEquals("Roche",mock.getMouvementAdv());
    }

    @Test
    public void setMouvementAdv() {
        Mouvement mock = new Mouvement(1,"Roche","Papier");
        mock.setMouvementAdv("Ciseaux");
        assertEquals("Ciseaux",mock.getMouvementAdv());
    }

    @Test
    public void getMouvementJoueur() {
        Mouvement mock = new Mouvement(1,"Roche","Papier");
        assertEquals("Papier",mock.getMouvementJoueur());
    }

    @Test
    public void setMouvementJoueur() {
        Mouvement mock = new Mouvement(1,"Roche","Papier");
        mock.setMouvementJoueur("Roche");
        assertEquals("Roche",mock.getMouvementJoueur());

    }
}