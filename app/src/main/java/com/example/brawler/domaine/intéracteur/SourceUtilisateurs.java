package com.example.brawler.domaine.intéracteur;

import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Utilisateur;

import java.util.List;

public interface SourceUtilisateurs {

    public List<Utilisateur> getNouvelleUtilisateurParNiveau(String location, Niveau niveau);
    public List<Utilisateur> getUtilisateur(String location);

}
