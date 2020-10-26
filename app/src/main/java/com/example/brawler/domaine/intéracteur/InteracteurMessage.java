package com.example.brawler.domaine.intéracteur;

import com.example.brawler.domaine.entité.Message;
import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Utilisateur;

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

    public List<Message> getMessages() throws MessageException, UtilisateursException {
        return source.getMessagesparUtilisateurs();
    }

    public void envoyerMessage(int idUtilisateur, String message) throws MessageException {
        source.envoyerMessage(idUtilisateur, message);
    }
}
