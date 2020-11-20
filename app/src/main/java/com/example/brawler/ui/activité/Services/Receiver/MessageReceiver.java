package com.example.brawler.ui.activité.Services.Receiver;

import android.app.RemoteInput;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.example.brawler.DAO.SourceMessageApi;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.présenteur.Notification.PrésenteurNoficationMessage;
import com.example.brawler.présentation.vue.notification.VueNotificationMessage;

public class MessageReceiver extends BroadcastReceiver {
    private static final String KEY_TEXT_REPLY = "com.brawler.keyTextReply";
    private static final String EXTRA_ID_UTILISATEUR_NOTIFICATION = "com.brawler.idUtilisateurNotification";

    private Context context;

    private String clé;
    private  VueNotificationMessage vue;
    private PrésenteurNoficationMessage présenteur;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        String texte = getTextRéponse(intent);
        int idUtilisateur = intent.getIntExtra(EXTRA_ID_UTILISATEUR_NOTIFICATION, -1);
        if(texte != null || idUtilisateur == -1){
            envoyerMessage(idUtilisateur, texte);
        }
    }

    private void envoyerMessage(int idUtilisateur, String message){

        vue = new VueNotificationMessage(context, context.getResources());
        Modèle modèle= new Modèle();
        modèle.setUtilisateurEnRevue(idUtilisateur);
        modèle.setTexteRéponse(message);
        présenteur = new PrésenteurNoficationMessage(modèle, vue);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        clé = sharedPref.getString("token", "");

        présenteur.setSource(new SourceMessageApi(clé));
        présenteur.startRépondre();
    }

    private String getTextRéponse(Intent intent){
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null)
            return remoteInput.getCharSequence(KEY_TEXT_REPLY).toString();
        return null;
    }
}
