package com.example.brawler.domaine.intéracteur;

import com.example.brawler.domaine.entité.Message;

import java.util.List;

public interface SourceMessage {
    public List<Message> getMessagesparUtilisateurs() throws MessageException, UtilisateursException;
    public void envoyerMessage(int idUtilisateur, String message) throws MessageException;
}
