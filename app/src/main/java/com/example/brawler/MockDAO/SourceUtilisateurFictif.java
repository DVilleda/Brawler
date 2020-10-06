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
    private String[] motsDePasses= {"5d41402abc4b2a76b9719d911017c592","202cb962ac59075b964b07152d234b70", "e99a18c428cb38d5f260853678922e03"};
    private Niveau[] niveaux = {Niveau.DÉBUTANT, Niveau.INTERMÉDIAIRE, Niveau.EXPERT, Niveau.LÉGENDAIRE};

    private void nouveauUtilisateur() {
        utilisateurs = new ArrayList<>();
        int nombreUtilisateur = 0;
        while(nombreUtilisateur == 0){
            nombreUtilisateur = new Random().nextInt(10);
        }
        for(int i=0; i < nombreUtilisateur; i++){
            utilisateurs.add(new Utilisateur (
                    noms[new Random().nextInt(noms.length - 1)],
                    niveaux[new Random().nextInt(niveaux.length - 1)],
                    locations[new Random().nextInt(locations.length - 1)], new Random().nextInt(50), new Random().nextInt(50),
                    motsDePasses[new Random().nextInt(locations.length - 1)]
            ));
        }
    }

    @Override
    public ArrayList<Utilisateur> getNouvelleUtilisateurParNiveau(String location, Niveau niveau){
        nouveauUtilisateur();

        ArrayList<Utilisateur> utilisateursSelectionné = new ArrayList<>();
        while(utilisateursSelectionné.size() == 0) {
            nouveauUtilisateur();
            for (int i = 0; utilisateurs.size() - 1 > i; i++) {
                if (utilisateurs.get(i).getLocation() == location && utilisateurs.get(i).getNiveau().equals(niveau)) {
                    utilisateursSelectionné.add(utilisateurs.get(i));
                }
            }
        }
        return utilisateursSelectionné;
    }

    @Override
    public ArrayList<Utilisateur> getUtilisateur(String location) {
        nouveauUtilisateur();

        ArrayList<Utilisateur> utilisateursSelectionné = new ArrayList<>();
        while(utilisateursSelectionné.size() == 0) {
            nouveauUtilisateur();
            for (int i = 0; utilisateurs.size() - 1 > i; i++) {
                if (utilisateurs.get(i).getLocation() == location) {
                    utilisateursSelectionné.add(utilisateurs.get(i));
                }
            }
        }
        return utilisateursSelectionné;
    }
}
