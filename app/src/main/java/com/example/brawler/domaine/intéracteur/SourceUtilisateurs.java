package com.example.brawler.domaine.intéracteur;

import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Utilisateur;

import java.util.ArrayList;

public interface SourceUtilisateurs {

    public ArrayList<Utilisateur> getNouvelleUtilisateurParNiveau(String location, Niveau niveau);
    public ArrayList<Utilisateur> getUtilisateur(String location);

}
