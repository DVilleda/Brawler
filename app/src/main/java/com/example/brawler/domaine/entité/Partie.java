package com.example.brawler.domaine.entité;

import java.util.List;

public class Partie {
    int idPartie;
    int idAdv;
    boolean enCours;
    List<Mouvement> mouvementsPartie;

    public Partie(int idPartie, boolean enCours,int idAdv, List<Mouvement> mouvementsPartie) {
        this.idPartie = idPartie;
        this.enCours = enCours;
        this.idAdv = idAdv;
        this.mouvementsPartie = mouvementsPartie;
    }

    public int getIdPartie() {
        return idPartie;
    }

    public void setIdPartie(int idPartie) {
        this.idPartie = idPartie;
    }

    public int getIdAdv() {
        return idAdv;
    }

    public void setIdAdv(int idAdv) {
        this.idAdv = idAdv;
    }

    public boolean isEnCours() {
        return enCours;
    }

    public void setEnCours(boolean enCours) {
        this.enCours = enCours;
    }

    public List<Mouvement> getMouvementsPartie() {
        return mouvementsPartie;
    }

    public void setMouvementsPartie(List<Mouvement> mouvementsPartie) {
        this.mouvementsPartie = mouvementsPartie;
    }
}
