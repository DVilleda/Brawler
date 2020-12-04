package com.example.brawler.domaine.entité;

import java.util.List;

public class Partie {
    int id;
    Utilisateur adversaire;
    Utilisateur gagnant;
    List<Tour> tour;

    /**
     * constructeur
     */
    public Partie() {
    }

    /**
     *
     * @return
     */
    public Utilisateur getAdversaire() {
        return adversaire;
    }

    /**
     *
     * @param adversaire
     */
    public void setAdversaire(Utilisateur adversaire) {
        this.adversaire = adversaire;
    }

    /**
     *
     * @return
     */
    public Utilisateur getGagnant() {
        return gagnant;
    }

    /**
     *
     * @param gagnant
     */
    public void setGagnant(Utilisateur gagnant) {
        this.gagnant = gagnant;
    }

    /**
     *
     * @return
     */
    public List<Tour> getTour() {
        return tour;
    }

    /**
     *
     * @param tour
     */
    public void setTour(List<Tour> tour) {
        this.tour = tour;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }
}
