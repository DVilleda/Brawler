package com.example.brawler.domaine.entité;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Notification {
    Utilisateur utilisateur;
    List<Message> messages;

    public Notification(Utilisateur utilisateur, Message message) {
        this.utilisateur = utilisateur;
        this.messages = new ArrayList<>();
        this.messages.add(message);
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<Message> getMessage() {
        return this.messages;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

}
