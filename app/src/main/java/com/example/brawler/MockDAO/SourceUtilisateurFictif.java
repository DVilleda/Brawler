package com.example.brawler.MockDAO;

import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.domaine.intéracteur.SourceUtilisateurs;

import java.util.ArrayList;
import java.util.Random;

public class SourceUtilisateurFictif implements SourceUtilisateurs {

    private ArrayList<Utilisateur> utilisateurs;
    private String[] noms ={"Robert", "Jaques", "Bob", "Guillaume", "Angel", "Danny", "Jean"};
    private String[] locations ={"Saint-Constant", "Montréal", "Drummondville"};
    private Niveau[] niveaux = {Niveau.DÉBUTANT, Niveau.INTERMÉDIAIRE, Niveau.EXPERT, Niveau.LÉGENDAIRE};

    public  SourceUtilisateurFictif(){
        utilisateurs = new ArrayList<>();
        for(int i=0; i < new Random().nextInt(10); i++){

            utilisateurs.add(new Utilisateur (noms[new Random().nextInt(noms.length - 1)], niveaux[new Random().nextInt(niveaux.length - 1)], locations[new Random().nextInt(locations.length - 1)], new Random().nextInt(50), new Random().nextInt(50)));
        }
    }

    @Override
    public ArrayList<Utilisateur> getNouvelleUtilisateurParNiveau(String location, Niveau niveau){
        ArrayList<Utilisateur> utilisateursSelectionné = new ArrayList<>();
        for(int i = 0; utilisateurs.size() - 1 > i; i++){
            if(utilisateurs.get(i).getLocation() == location && utilisateurs.get(i).getNiveau().equals(niveau)) {
                utilisateursSelectionné.add(utilisateurs.get(i));
            }
        }
        return utilisateursSelectionné;
    }

    @Override
    public ArrayList<Utilisateur> getUtilisateur(String location) {
        ArrayList<Utilisateur> utilisateursSelectionné = new ArrayList<>();
        for(int i = 0; utilisateurs.size() - 1 > i; i++){
            if(utilisateurs.get(i).getLocation() == location) {
                utilisateursSelectionné.add(utilisateurs.get(i));
            }
        }
        return utilisateursSelectionné;
    }
}
