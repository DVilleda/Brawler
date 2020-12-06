package com.example.brawler.domaine.intéracteur;

import com.example.brawler.domaine.entité.Partie;

import java.util.ArrayList;

public interface SourceDeroulementPartie {

    Partie getPartieParID(int idPartie) throws PartieException;

    void EnvoyerMouvement(int idPartie,int idAdversaire, String mouvement) throws PartieException;

    ArrayList RecevoirMouvements(int idPartie);
}
