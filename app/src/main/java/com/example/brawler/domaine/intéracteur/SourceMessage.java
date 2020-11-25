package com.example.brawler.domaine.intéracteur;

import com.example.brawler.domaine.entité.Message;

import java.util.List;

public interface SourceMessage {

    public List<Message> getMessagesparUtilisateurs(int idUtiliasteur) throws MessageException, UtilisateursException;

    List<Message> getMessagesparUtilisateursEntreDeux(int idUtilisateur, int debutListe, int finListe) throws MessageException, UtilisateursException;

    List<Message> getMessages() throws MessageException, UtilisateursException;

    List<Message> getMessageÀNotifier() throws MessageException, UtilisateursException;

    void marquerNotifier(int idMessage) throws MessageException, UtilisateursException;

    public void envoyerMessage(int idUtilisateur, String message) throws MessageException;

    int obtenireNombreMessage(int id) throws MessageException, UtilisateursException;
}
