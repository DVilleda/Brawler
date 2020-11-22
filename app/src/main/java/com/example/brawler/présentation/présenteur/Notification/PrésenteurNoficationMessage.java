package com.example.brawler.présentation.présenteur.Notification;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

import com.example.brawler.domaine.intéracteur.InteracteurMessage;
import com.example.brawler.domaine.intéracteur.MessageException;
import com.example.brawler.domaine.intéracteur.SourceMessage;
import com.example.brawler.domaine.intéracteur.UtilisateursException;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.vue.notification.VueNotificationMessage;

import java.util.ArrayList;
import java.util.Date;

public class PrésenteurNoficationMessage {

    private Thread filEsclaveEnvoyerMessage = null;
    private final Handler handlerRéponse;
    private SourceMessage source;
    private SharedPreferences sharedPref;
    private Modèle modèle;
    private VueNotificationMessage vue;

    private final int MSG_CHARGER_MESSAGES = 1;
    private final int MSG_ERREUR = 2;
    private final int MSG_ANNULER = 3;
    private final int MSG_MARQUER_NOTIFIER = 4;
    private final int MSG_NOUVEAU_MESSAGE = 5;

    public PrésenteurNoficationMessage(final Modèle modèle, VueNotificationMessage vue) {

        this.modèle = modèle;
        this.vue = vue;

        this.handlerRéponse = new Handler(){

            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);

                filEsclaveEnvoyerMessage = null;

                if (msg.what == MSG_CHARGER_MESSAGES) {
                    notifierMessage();
                } else if ( msg.what == MSG_ERREUR ) {

                } else if (msg.what == MSG_NOUVEAU_MESSAGE) {
                    afficherRépondreMessage();
                }
            }
        };
    }

    public void setSource(SourceMessage source) {
        this.source = source;
    }

    public void startNotifier() {
        getMessagesÀNotifier();
    }

    public void startRépondre(){
        envoyerMessage(modèle.getTexteRéponse(), modèle.getUtilisateurEnRevue());
    }

    private void getMessagesÀNotifier(){

        filEsclaveEnvoyerMessage = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message msg = null;
                        try {
                            modèle.setListeMessage(InteracteurMessage.getInstance(source).getMessagesÀNotifier());
                            msg = handlerRéponse.obtainMessage( MSG_CHARGER_MESSAGES );
                        } catch (MessageException e) {
                            msg = handlerRéponse.obtainMessage( MSG_ERREUR );
                        } catch (UtilisateursException e) {
                            msg = handlerRéponse.obtainMessage( MSG_ERREUR );
                        }

                        handlerRéponse.sendMessage( msg );
                    }
                });
        filEsclaveEnvoyerMessage.start();
    }



    private void notifierMessage(){
        if(modèle.getMessages().size() > 0) {
            for (com.example.brawler.domaine.entité.Message unMessage: modèle.getMessages()) {
                vue.afficherNotification(unMessage.getUtilisateur().getId(), unMessage.getTexte(), unMessage.getUtilisateur().getNom(),unMessage.getTemps());
                marquerMessagesNotifier(unMessage);
            }
        }

    }

    private void envoyerMessage(final String texte,final int idUtiliasteur){
        filEsclaveEnvoyerMessage = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message msg = null;
                        try {
                            InteracteurMessage.getInstance(source).envoyerMessage(idUtiliasteur, texte);
                            msg = handlerRéponse.obtainMessage( MSG_NOUVEAU_MESSAGE );
                        } catch (MessageException e) {
                            msg = handlerRéponse.obtainMessage( MSG_ANNULER );
                        }

                        handlerRéponse.sendMessage( msg );
                    }
                });
        filEsclaveEnvoyerMessage.start();
    }

    private void marquerMessagesNotifier(com.example.brawler.domaine.entité.Message message) {
        commencerFileEscalveMarquerNotifier(message);
    }

    private void commencerFileEscalveMarquerNotifier(final com.example.brawler.domaine.entité.Message message) {

        filEsclaveEnvoyerMessage = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message msg = null;
                        try {
                            InteracteurMessage.getInstance(source).marquerNotifier(message.getId());
                            msg = handlerRéponse.obtainMessage( MSG_MARQUER_NOTIFIER );
                        } catch (MessageException e) {
                            msg = handlerRéponse.obtainMessage( MSG_ERREUR );
                        } catch (UtilisateursException e) {
                            msg = handlerRéponse.obtainMessage( MSG_ERREUR );
                        }

                        handlerRéponse.sendMessage( msg );
                    }
                });
        filEsclaveEnvoyerMessage.start();
    }


    private void afficherRépondreMessage() {
        vue.afficherNotification(modèle.getUtilisateurEnRevue(), modèle.getTexteRéponse(), null, new Date());
    }
}
