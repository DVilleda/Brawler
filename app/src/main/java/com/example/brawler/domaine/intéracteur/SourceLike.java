package com.example.brawler.domaine.intéracteur;

import com.example.brawler.DAO.SourceLikeApi;

import java.io.IOException;

public interface SourceLike {

    public boolean confirmerLike(int id) throws UtilisateursException;

}
