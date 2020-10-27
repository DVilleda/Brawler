package com.example.brawler.domaine.intéracteur;

import com.example.brawler.DAO.SourceUtilisateursApi;
import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Utilisateur;

import org.json.JSONObject;

import java.util.List;

public interface SourceUtilisateurs {

    public List<Utilisateur> getNouvelleUtilisateurParNiveau(Niveau niveau) throws UtilisateursException;
    public List<Utilisateur> getUtilisateur() throws UtilisateursException;
    public JSONObject creerNouveauUtilisateur(String email, String mdp, String prénom, String location, String description );
}
