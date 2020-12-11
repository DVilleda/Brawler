package com.example.brawler.domaine.entité;

import java.util.List;

public class Partie {
    int idPartie;
    int idAdv;
    Utilisateur gagnant;
    Utilisateur adversaire;
    boolean enCours;
    boolean gagne;
    List<Mouvement> mouvementsPartie;

    public Partie(int idPartie, boolean enCours,int idAdv,boolean victorieux, List<Mouvement> mouvementsPartie) {
        this.idPartie = idPartie;
        this.enCours = enCours;
        this.idAdv = idAdv;
        this.mouvementsPartie = mouvementsPartie;
        this.gagne = victorieux;
    }

    public Partie() {

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

    public boolean isGagne() {
        return gagne;
    }

    public void setGagne(boolean gagne) {
        this.gagne = gagne;
    }

    public Utilisateur getGagnant() {
        return  gagnant;
    }
    public void setGagnant(Utilisateur gagnant){
        this.gagnant = gagnant;
    }

    public Utilisateur getAdversaire() {
        return adversaire;
    }

    public void setAdversaire(Utilisateur adversaire) {
        this.adversaire = adversaire;
    }
}
