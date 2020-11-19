package com.example.brawler.présentation.présenteur.Notification;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

import com.example.brawler.domaine.entité.Notification;
import com.example.brawler.domaine.intéracteur.InteracteurMessage;
import com.example.brawler.domaine.intéracteur.MessageException;
import com.example.brawler.domaine.intéracteur.SourceMessage;
import com.example.brawler.domaine.intéracteur.UtilisateursException;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.vue.notification.VueNotificationMessage;

import java.util.ArrayList;

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

    public PrésenteurNoficationMessage(Modèle modèle, VueNotificationMessage vue) {

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
                }
            }
        };
    }

    public void getMessagesÀNotifier(){
        filEsclaveEnvoyerMessage = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message msg = null;
                        try {
                            Thread.sleep(0);
                            modèle.setListeMessage(InteracteurMessage.getInstance(source).getMessagesÀNotifier());
                            msg = handlerRéponse.obtainMessage( MSG_CHARGER_MESSAGES );
                        } catch (InterruptedException e) {
                            msg = handlerRéponse.obtainMessage( MSG_ANNULER );
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

    public void setSource(SourceMessage source) {
        this.source = source;
    }

    public void notifierMessage(){
        if(modèle.getMessages().size() > 0) {
            créerNotificationParMessage();
            for (Notification notification : modèle.getNotification()) {
                vue.afficherNotification(notification);
            }
        }

    }

    private  void créerNotificationParMessage() {
        boolean utilisateurDéjàEnNotification;
        modèle.setNotification(new ArrayList<Notification>());
        for (com.example.brawler.domaine.entité.Message message : modèle.getMessages()) {
            utilisateurDéjàEnNotification = false;

            if(modèle.getNotification().size() > 0) {
                for (Notification notification : modèle.getNotification()) {
                    if (message.getUtilisateur() == notification.getUtilisateur()) {
                        notification.addMessage(message);
                        utilisateurDéjàEnNotification = true;
                    }
                }
            }

            if(!utilisateurDéjàEnNotification){
                modèle.getNotification().add(new Notification(message.getUtilisateur(), message));
            }
        }
    }
}
