package com.example.brawler.domaine.entité;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Classe de test pour les getter et setter de la classe Partie
 */
public class PartieTest {

    @Test
    public void getIdPartie() {
        Partie mock = new Partie(1,false,2,false,null);
        assertEquals(1,mock.getIdPartie());
    }

    @Test
    public void setIdPartie() {
        Partie mock = new Partie(1,false,2,false,null);
        mock.setIdPartie(2);
        assertEquals(2,mock.getIdPartie());
    }

    @Test
    public void getIdAdv() {
        Partie mock = new Partie(1,false,2,false,null);
        assertEquals(2,mock.getIdAdv());
    }

    @Test
    public void setIdAdv() {
        Partie mock = new Partie(1,false,2,false,null);
        mock.setIdAdv(10);
        assertEquals(10,mock.getIdAdv());
    }

    @Test
    public void isEnCours() {
        Partie mock = new Partie(1,false,2,false,null);
        assertEquals(false,mock.isEnCours());
    }

    @Test
    public void setEnCours() {
        Partie mock = new Partie(1,false,2,false,null);
        mock.setEnCours(true);
        assertEquals(true,mock.isEnCours());
    }

    @Test
    public void getMouvementsPartie() {
        List<Mouvement> mouvementList = new ArrayList<>();
        mouvementList.add(new Mouvement(1,"Roche","Papier"));
        mouvementList.add(new Mouvement(2,"Papier","Papier"));
        Partie mock = new Partie(1,false,2,false,mouvementList);
        assertEquals(2,mock.getMouvementsPartie().size());
    }

    @Test
    public void setMouvementsPartie() {
        List<Mouvement> mouvementList = new ArrayList<>();
        mouvementList.add(new Mouvement(1,"Roche","Papier"));
        mouvementList.add(new Mouvement(2,"Papier","Papier"));
        Partie mock = new Partie(1,false,2,false,mouvementList);
        mouvementList.add(new Mouvement(3,"Roche","Roche"));
        mock.setMouvementsPartie(mouvementList);
        assertEquals(3,mock.getMouvementsPartie().size());
    }

    @Test
    public void isGagne() {
        Partie mock = new Partie(1,false,2,false,null);
        assertEquals(false,mock.isGagne());
    }

    @Test
    public void setGagne() {
        Partie mock = new Partie(1,false,2,false,null);
        mock.setGagne(true);
        assertEquals(true,mock.isGagne());
    }
}