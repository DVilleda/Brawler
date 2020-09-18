package com.example.brawler.MockDAO;

import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.domaine.intéracteur.SourceUtilisateurs;

import java.util.Random;

public class SourceUtilisateurFictif implements SourceUtilisateurs {

    public String[] noms ={"Robert", "Jaques", "Bob", "Guillaume", "Angel", "Danny", "Jean"};

    @Override
    public Utilisateur getNouvelleUtilisateur(String location, Niveau niveau) {
        return new Utilisateur (noms[new Random().nextInt(noms.length + 1)], niveau, location, new Random().nextInt(50), new Random().nextInt(50));
    }
}
