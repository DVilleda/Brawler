package com.example.brawler.domaine.intéracteur;

import com.example.brawler.domaine.entité.Partie;

import java.util.ArrayList;

/**
 * Cette classe à pour but d'obtenir les mouvement d'une partie
 */
public class InteracteurJouerPartie {
    private SourceDeroulementPartie sourceDeroulementPartie;
    private Partie _partie;
    private int leTour = 0;
    private boolean tourChange = false;
    static InteracteurJouerPartie instance;

    /**
     * Retourne une instance de l'interacteur jouer partie
     * @param source
     * @return
     */
    public static InteracteurJouerPartie getInstance(SourceDeroulementPartie source) {
        if (instance ==null)
            instance = new InteracteurJouerPartie(source);

        return  instance;
    }

    /**
     * Met la source à utiliser
     * @param _source
     */
    protected InteracteurJouerPartie(SourceDeroulementPartie _source){
        this.sourceDeroulementPartie = _source;
    }

    /**
     * Obtenir une partie selon le ID de la partie
     * @param id de la partie
     * @return la partie choisie
     * @throws PartieException Si erreur d'API
     */
    public Partie getPartieParID(int id) throws PartieException {
        _partie = sourceDeroulementPartie.getPartieParID(id);
        if(_partie.getMouvementsPartie() != null) {
            int index = _partie.getMouvementsPartie().size();
            if (index > leTour) {
                tourChange = true;
                this.leTour = index;
            }
        }
        return _partie;
    }

    /**
     * Retourne une instance du tour actuel
     * @return
     */
    public int getLeTour(){
        return this.leTour;
    }

    /**
     * Retourne un boolean qui verifie si le tour a changé
     * @return
     */
    public boolean getBoolTourChange(){
        return this.tourChange;
    }

    /**
     * Permet d'envoyer un mouvement à la partie selon le mouvement choisi
     * @param idPartie id de la partie
     * @param idAdv id de l'adversaire
     * @param mouvement string du mouvment
     * @throws PartieException
     */
    public void envoyerMouvement(int idPartie,int idAdv,String mouvement) throws PartieException {
        tourChange = false;
        sourceDeroulementPartie.EnvoyerMouvement(idPartie,idAdv,mouvement);
    }

    /**
     * Permet d'obtenir une partie qui est dans l'instace d'interacteur
     * @return
     */
    public Partie get_partie() {
        return _partie;
    }
}
