package com.example.brawler.domaine.intéracteur;

import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Utilisateur;

public interface InterfaceUtiliasteur {

    public interface créerUtiliasteur {
        public Utilisateur créerUnUtilisateur(String nom, Niveau niveau, String location, int partieGagnée, int partiePerdue) throws Exception;

    }

    public interface modifierUtilisateur {
        public Utilisateur modiifierNom(Utilisateur utilisateur, String nom) throws Exception;
        public Utilisateur modifierLocation(Utilisateur utilisateur, String location) throws Exception;

    }
}
