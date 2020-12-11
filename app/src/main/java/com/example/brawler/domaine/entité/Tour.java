package com.example.brawler.domaine.entité;

public class Tour {
    int numéro;
    Mouvement mouvementAdversaire;
    Mouvement mouvementJoueur;

    /**
     * constructeur vide
     */
    public Tour() {

    }

    /**
     *
     * @param numéro
     * @param mouvementAdversaire
     * @param mouvementJoueur
     */
    public Tour(int numéro, Mouvement mouvementAdversaire, Mouvement mouvementJoueur) {
        this.numéro = numéro;
        this.mouvementAdversaire = mouvementAdversaire;
        this.mouvementJoueur = mouvementJoueur;
    }

    /**
     *
     * @return
     */
    public Mouvement getMouvementAdversaire() {
        return mouvementAdversaire;
    }

    /**
     *
     * @param mouvementAdversaire
     */
    public void setMouvementAdversaire(Mouvement mouvementAdversaire) {
        this.mouvementAdversaire = mouvementAdversaire;
    }

    /**
     *
     * @return
     */
    public Mouvement getMouvementJoueur() {
        return mouvementJoueur;
    }

    /**
     *
     * @param mouvementJoueur
     */
    public void setMouvementJoueur(Mouvement mouvementJoueur) {
        this.mouvementJoueur = mouvementJoueur;
    }
}
