package com.example.brawler.domaine.intéracteur;

import com.example.brawler.DAO.SourceLikeApi;

import java.io.IOException;

public class InteracteurLikeUtilisateur {
    private SourceLike source;
    private static InteracteurLikeUtilisateur instance;

    public static InteracteurLikeUtilisateur getInstance(SourceLike source) {
        if (instance == null)
            instance =  new InteracteurLikeUtilisateur(source);

        return  instance;
    }

    private InteracteurLikeUtilisateur(SourceLike source) {

        this.source = source;
    }

    public boolean likerUtilisateur(int id) throws UtilisateursException {
        return source.confirmerLike(id);
    }
}
