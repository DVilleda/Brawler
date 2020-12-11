package com.example.brawler.domaine.intéracteur;

import com.example.brawler.domaine.entité.Message;

import java.io.IOException;
import java.util.List;

public class InteracteurMessage {
    private SourceMessage source;
    private static InteracteurMessage instance;

    public static InteracteurMessage getInstance(SourceMessage source) {
        if (instance == null)
            instance =  new InteracteurMessage(source);

        return  instance;
    }

    private InteracteurMessage(SourceMessage source) {
        this.source = source;
    }

    /**
     * Retourne la liste de messages que l'utilisateur a envoyer ou recu a un autre utilisateur
     * @param idUtilisateur
     * @return
     * @throws MessageException
     * @throws UtilisateursException
     */
    public List<Message> getMessagesparUtilisateurs(int idUtilisateur) throws MessageException, UtilisateursException {
        return source.getMessagesparUtilisateurs(idUtilisateur);
    }

    /**
     * retourne seulement un partie des message que l'utilisateur a enovyer ouy recu d'un autre utilisateur
     * @param idUtilisateur
     * @param debutListe
     * @param finListe
     * @return
     * @throws MessageException
     * @throws UtilisateursException
     */
    public List<Message> getMessagesparUtilisateursEntreDeux(int idUtilisateur, int debutListe, int finListe) throws MessageException, UtilisateursException {
        return source.getMessagesparUtilisateursEntreDeux(idUtilisateur, debutListe, finListe);
    }

    /**
     * retourne tout les message que l'utilisateur a recu
     * @return
     * @throws MessageException
     * @throws UtilisateursException
     */
    public List<Message> getMessages() throws MessageException, UtilisateursException {
        return source.getMessages();
    }

    /**
     * retourne la liste des message qui sont marquer a notifier que l'utilisateur a recu
     * @return
     * @throws MessageException
     * @throws UtilisateursException
     */
    public List<Message> getMessagesÀNotifier() throws MessageException, UtilisateursException {
        return source.getMessageÀNotifier();
    }

    /**
     * marque un message recu comme notiifer
     * @param idMessage
     * @throws MessageException
     * @throws UtilisateursException
     */
    public void marquerNotifier(int idMessage) throws MessageException, UtilisateursException {
        source.marquerNotifier(idMessage);
    }

    /**
     * permet a l'utilisateur d'envoyer un message a un autre utilisateur
     * @param idUtilisateur
     * @param message
     * @return
     * @throws MessageException
     * @throws IOException
     * @throws UtilisateursException
     */
    public Message envoyerMessage(int idUtilisateur, String message) throws MessageException, IOException, UtilisateursException {
        return source.envoyerMessage(idUtilisateur, message);
    }

    /**
     * retourne le nombre de message qu'un utilisateur a recu d'un autre utilisateur ou qu'il lui a envoyer
     * @param id
     * @return
     * @throws MessageException
     * @throws UtilisateursException
     */
    public int obtenirNombreMessageParUtilisateur(int id) throws MessageException, UtilisateursException {
        return source.obtenireNombreMessage(id);
    }
}
