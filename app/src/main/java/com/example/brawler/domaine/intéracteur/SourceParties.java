package com.example.brawler.domaine.intéracteur;

import com.example.brawler.DAO.SourcePartiesApi;
import com.example.brawler.domaine.entité.Partie;

import java.util.List;

public interface SourceParties {
    public List<Partie> getDemandeParties() throws SourcePartiesApi.SourcePartieApiException;

    void envoyerDemandePartie(int id) throws SourcePartiesApi.SourcePartieApiException;

    void refuserDemandePartie(int id) throws SourcePartiesApi.SourcePartieApiException;
}
