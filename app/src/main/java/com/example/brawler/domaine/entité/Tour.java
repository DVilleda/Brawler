package com.example.brawler.domaine.entité;

public class Tour {
    int numéro;
    Mouvement mouvementAdversaire;
    Mouvement mouvementJoueur;

    public Tour() {

    }

    public Tour(int numéro, Mouvement mouvementAdversaire, Mouvement mouvementJoueur) {
        this.numéro = numéro;
        this.mouvementAdversaire = mouvementAdversaire;
        this.mouvementJoueur = mouvementJoueur;
    }

    public Mouvement getMouvementAdversaire() {
        return mouvementAdversaire;
    }

    public void setMouvementAdversaire(Mouvement mouvementAdversaire) {
        this.mouvementAdversaire = mouvementAdversaire;
    }

    public Mouvement getMouvementJoueur() {
        return mouvementJoueur;
    }

    public void setMouvementJoueur(Mouvement mouvementJoueur) {
        this.mouvementJoueur = mouvementJoueur;
    }
}
