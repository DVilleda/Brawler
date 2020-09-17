package com.example.brawler.domaine.intéracteur;

import com.example.brawler.domaine.entité.Utilisateur;

public interface InterfaceUtiliasteur {

    public interface créerUtiliasteur {
        public Utilisateur créerUnUtilisateur();

    }

    public interface modifierUtilisateur {
        public Utilisateur modiifierNom(Utilisateur utilisateur, String nom) throws Exception;
        public Utilisateur modifierLocation(Utilisateur utilisateur, String location) throws Exception;

    }
}
