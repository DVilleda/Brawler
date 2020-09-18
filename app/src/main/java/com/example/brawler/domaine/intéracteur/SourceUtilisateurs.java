package com.example.brawler.domaine.intéracteur;

import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Utilisateur;

public interface SourceUtilisateurs {

    public Utilisateur getNouvelleUtilisateur(String location, Niveau niveau);
}
