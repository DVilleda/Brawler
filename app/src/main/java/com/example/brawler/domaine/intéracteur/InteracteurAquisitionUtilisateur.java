package com.example.brawler.domaine.intéracteur;

import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Utilisateur;

import java.util.List;

public class InteracteurAquisitionUtilisateur {

        private SourceUtilisateur source;
        private static InteracteurAquisitionUtilisateur instance;

        public static InteracteurAquisitionUtilisateur getInstance(SourceUtilisateur source) {
            instance =  new InteracteurAquisitionUtilisateur(source);
            return  instance;
        }

        private InteracteurAquisitionUtilisateur(SourceUtilisateur source) {
            this.source = source;
        }

        public Utilisateur getUtilisateurParId(int id) throws UtilisateursException {
            return source.getUtilisateurParId(id, true);
        }

}
