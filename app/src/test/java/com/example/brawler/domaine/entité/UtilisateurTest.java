package com.example.brawler.domaine.entité;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class UtilisateurTest {

    @Test
    public void testAccesseurNom() {
        Utilisateur unUtilisateur;
        unUtilisateur = new Utilisateur(1,"Jaques", Niveau.DÉBUTANT, "Montréal");

        assertEquals("Jaques", unUtilisateur.getNom());
    }

    @Test
    public void testModificateurNom() {
        Utilisateur unUtilisateur;
        unUtilisateur = new Utilisateur(1,"Jaques", Niveau.DÉBUTANT, "Montréal");
        unUtilisateur.setNom("Robert");

        assertEquals("Robert", unUtilisateur.getNom());
    }

    @Test
    public void testAccesseurNiveau() {
        Utilisateur unUtilisateur;
        unUtilisateur = new Utilisateur(1,"Jaques", Niveau.DÉBUTANT, "Montréal");

        assertEquals(Niveau.DÉBUTANT, unUtilisateur.getNiveau());
    }

    @Test
    public void setNiveau() {
        Utilisateur unUtilisateur;
        unUtilisateur = new Utilisateur(1,"Jaques", Niveau.DÉBUTANT, "Montréal");
        unUtilisateur.setNiveau(Niveau.EXPERT);

        assertEquals(Niveau.EXPERT, unUtilisateur.getNiveau());
    }

    @Test
    public void getLocation() {
        Utilisateur unUtilisateur;
        unUtilisateur = new Utilisateur(1,"Jaques", Niveau.DÉBUTANT, "Montréal");

        assertEquals("Montréal", unUtilisateur.getLocation());
    }

    @Test
    public void setLocation() {
        Utilisateur unUtilisateur;
        unUtilisateur = new Utilisateur(1,"Jaques", Niveau.DÉBUTANT, "Montréal");
        unUtilisateur.setLocation("Québec");

        assertEquals("Québec", unUtilisateur.getLocation());
    }

}