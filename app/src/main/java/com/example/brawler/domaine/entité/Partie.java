package com.example.brawler.domaine.entité;

import java.util.List;

public class Partie {
    int id;
    Utilisateur adversaire;
    Utilisateur gagnat;
    List<Tour> tour;

    public Partie() {
    }

    public Utilisateur getAdversaire() {
        return adversaire;
    }

    public void setAdversaire(Utilisateur adversaire) {
        this.adversaire = adversaire;
    }

    public Utilisateur getGagnat() {
        return gagnat;
    }

    public void setGagnat(Utilisateur gagnat) {
        this.gagnat = gagnat;
    }

    public List<Tour> getTour() {
        return tour;
    }

    public void setTour(List<Tour> tour) {
        this.tour = tour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
