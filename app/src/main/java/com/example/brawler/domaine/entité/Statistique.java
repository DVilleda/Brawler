package com.example.brawler.domaine.entité;

public class Statistique {

    private int nombreVictoire;
    private int nombrePerdu;

    public Statistique(int nombreVictoire, int nombrePerdu) {
        this.nombreVictoire = nombreVictoire;
        this.nombrePerdu = nombrePerdu;
    }

    public int getNombreVictoire() {
        return nombreVictoire;
    }

    public int getNombrePerdu() {
        return nombrePerdu;
    }

    public int getPartiesAuTotal() { return nombrePerdu+nombrePerdu; }

    public void setNombreVictoire(int nombreVictoire) {
        this.nombreVictoire = nombreVictoire;
    }

    public void setNombrePerdu(int nombrePerdu) {
        this.nombrePerdu = nombrePerdu;
    }

    public void ajouterVictoire() {
        nombreVictoire += 1;
    }

    public void ajouterPerdu() {
        nombrePerdu += 1;
    }

}
