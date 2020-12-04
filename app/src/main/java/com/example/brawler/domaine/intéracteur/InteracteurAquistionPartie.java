package com.example.brawler.domaine.intéracteur;

import com.example.brawler.DAO.SourcePartiesApi;
import com.example.brawler.domaine.entité.Partie;

import java.util.List;

public class InteracteurAquistionPartie {
    private SourceParties source;
    private static InteracteurAquistionPartie instance;

    /**
     * Créer l'interacteur
     * @param source
     */
    public InteracteurAquistionPartie(SourceParties source) {
        this.source = source;
    }


    /**
     * Retourne l'isntance de l'interacteur
     * @param source
     * @return
     */
    public static InteracteurAquistionPartie getInstance(SourceParties source) {
        instance =  new InteracteurAquistionPartie(source);
        return  instance;
    }

    /**
     * Permet d'obtenir la list des demande de partie qu'un joueur recoit
     * @return
     * @throws SourcePartiesApi.SourcePartieApiException
     */
    public List<Partie> getDemandePartie() throws SourcePartiesApi.SourcePartieApiException {
        return source.getDemandeParties();
    }

    /**
     * Envoyie ou accepte un demande de partie selon l'i de l'adversaire
     * @param idAversaire
     * @throws SourcePartiesApi.SourcePartieApiException
     */
    public void enovyerDemandePartie(int idAversaire) throws SourcePartiesApi.SourcePartieApiException {
        source.envoyerDemandePartie(idAversaire);
    }

    /**
     *
     * @param idAversaire
     * @throws SourcePartiesApi.SourcePartieApiException
     */
    public void refuserDemandePartie(int idAversaire) throws SourcePartiesApi.SourcePartieApiException {
        source.refuserDemandePartie(idAversaire);
    }

}
