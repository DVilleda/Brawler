package com.example.brawler.domaine.intéracteur;

import com.example.brawler.domaine.entité.Partie;

import java.util.List;

public class InteracteurAquistionPartie {
    private SourceParties source;
    private static InteracteurAquistionPartie instance;

    public InteracteurAquistionPartie(SourceParties source) {
        this.source = source;
    }

    public static InteracteurAquistionPartie getInstance(SourceParties source) {
        instance =  new InteracteurAquistionPartie(source);
        return  instance;
    }

    public List<Partie> getDemandePartie() {
        return source.getDemandeParties();
    }

}
