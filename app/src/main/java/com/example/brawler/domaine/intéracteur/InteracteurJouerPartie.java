package com.example.brawler.domaine.intéracteur;

import com.example.brawler.domaine.entité.Partie;

import java.util.ArrayList;

public class InteracteurJouerPartie {
    SourceDeroulementPartie sourceDeroulementPartie;
    Partie _partie;
    static InteracteurJouerPartie instance;

    public static InteracteurJouerPartie getInstance(SourceDeroulementPartie source) {
        if (instance ==null)
            instance = new InteracteurJouerPartie(source);

        return  instance;
    }

    private InteracteurJouerPartie(SourceDeroulementPartie _source){
        this.sourceDeroulementPartie = _source;
    }

    public Partie getPartieParID(int id) throws PartieException {
        _partie = sourceDeroulementPartie.getPartieParID(id);
        return _partie;
    }

    public ArrayList getMouvementsPartie(int id){
        return sourceDeroulementPartie.RecevoirMouvements(id);
    }

    public void envoyerMouvement(int idPartie,int idAdv,String mouvement) throws PartieException {
        sourceDeroulementPartie.EnvoyerMouvement(idPartie,idAdv,mouvement);
    }

    public Partie get_partie() {
        return _partie;
    }
}
