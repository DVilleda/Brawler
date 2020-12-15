package com.example.brawler.présentation.modèle;

import android.graphics.Bitmap;

import com.example.brawler.domaine.entité.Message;
import com.example.brawler.domaine.entité.Mouvement;
import com.example.brawler.domaine.entité.Partie;
import com.example.brawler.domaine.entité.Utilisateur;

import java.util.ArrayList;
import java.util.List;

public class Modèle {

    private List<Utilisateur> listeUtilisateurs;
    private List<Integer> listUtilisateursId;
    private int utilisateurEnRevue;
    private Utilisateur utilisateur;
    private Utilisateur utilisateurDeApplication;
    private List<Message> messages;
    private String texteRéponse;
    private int nombreMessageTotale;
    private Bitmap bitmapPhoto;
    private List<Partie> parties;
    private Partie partieChoisi;

    public Modèle() {
        listeUtilisateurs = new ArrayList<>();
        listUtilisateursId = new ArrayList<>();
        utilisateurEnRevue = 0;
        parties = new ArrayList<>();
        messages = new ArrayList<>();

    }

    public List<Utilisateur> getListUtilisateurs() {
        return listeUtilisateurs;
    }

    public void setListeUtilisateurs(List<Utilisateur> listUtilisateurs) {
        this.listeUtilisateurs = listUtilisateurs;
    }

    public List<Integer> getListUtilisateursId() {
        return listUtilisateursId;
    }

    public void setListUtilisateursId(List<Integer> listUtilisateursId) {
        this.listUtilisateursId = listUtilisateursId;
    }

    public void setListeMessage(List<Message> messages) {
        this.messages = messages;
    }

    public List<Message> getMessages(){
        return messages;
    }

    public int getUtilisateurIdActuel() {
        return utilisateurEnRevue;
    }

    public int getUtilisateurEnRevue(){
        return utilisateurEnRevue;
    }

    public void prochainUtilisateur(){utilisateurEnRevue +=1;}

    public void viderListeUtilisateurs() {
        if (listUtilisateursId.size() != 0) {
            listUtilisateursId.clear();
            utilisateurEnRevue = 0;
        }
    }

    public Utilisateur getUtilisateur(){
        return this.utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }



    public void setUtilisateurEnRevue(int i) {
        utilisateurEnRevue = i;
    }

    public String getTexteRéponse() {
        return texteRéponse;
    }

    public void setTexteRéponse(String texteRéponse) {
        this.texteRéponse = texteRéponse;
    }

    public int getNombreMessageTotale() {
        return this.nombreMessageTotale;
    }

    public void setNombreMessageTotale(int nombreMessageTotale) {
        this.nombreMessageTotale = nombreMessageTotale;
    }

    public void ajouterListeMessage(List<Message> messagesparUtilisateursEntreDeux) {
        this.messages.addAll(messagesparUtilisateursEntreDeux);
    }

    public Bitmap getBitmapPhoto() {
        return bitmapPhoto;
    }

    public void setBitmapPhoto(Bitmap bitmapPhoto) {
        this.bitmapPhoto = bitmapPhoto;
    }

    public Utilisateur getUtilisateurDeApplication() {
        return utilisateurDeApplication;
    }

    public void setUtilisateurDeApplication(Utilisateur utilisateurDeApplication) {
        this.utilisateurDeApplication = utilisateurDeApplication;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    //Partie
    public void setParties(List<Partie> parties){
        this.parties = parties;
    }

    public List<Partie> getParties() {
        return this.parties;
      }
    public Partie getPartieChoisi() {
        return partieChoisi;
    }

    public void setPartieChoisi(Partie partieChoisi) {
        this.partieChoisi = partieChoisi;
    }

    public void ajouterDebutListe(List<Message> messagesparUtilisateursEntreDeux) {
        for (Message message:
             messagesparUtilisateursEntreDeux) {
            messages.add(0, message );
        }

    }
}
