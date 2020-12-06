package com.example.brawler.domaine.entité;

public class Mouvement
{
    private int tour;
    private String mouvementAdv;
    private String mouvementJoueur;

    public Mouvement(int tour, String mouvementAdv, String mouvementJoueur) {
        this.tour = tour;
        this.mouvementAdv = mouvementAdv;
        this.mouvementJoueur = mouvementJoueur;
    }

    public int getTour() {
        return tour;
    }

    public void setTour(int tour) {
        this.tour = tour;
    }

    public String getMouvementAdv() {
        return mouvementAdv;
    }

    public void setMouvementAdv(String mouvementAdv) {
        this.mouvementAdv = mouvementAdv;
    }

    public String getMouvementJoueur() {
        return mouvementJoueur;
    }

    public void setMouvementJoueur(String mouvementJoueur) {
        this.mouvementJoueur = mouvementJoueur;
    }
}
