package com.example.brawler.présentation.présenteur;

import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.brawler.domaine.intéracteur.InteracteurMessage;
import com.example.brawler.domaine.intéracteur.MessageException;
import com.example.brawler.domaine.intéracteur.SourceMessage;
import com.example.brawler.domaine.intéracteur.UtilisateursException;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.vue.VueConsulterMessage;

public class PrésenteurConsulterMessage {

    private VueConsulterMessage vue;
    private Modèle modèle;
    private SourceMessage source;
    private int nbMessageActuel;
    private boolean doitRafrahcir;

    private final Handler handlerRéponse;
    private Handler handlerRafraîchir;

    private Thread filEsclaveEnvoyerMessage = null;

    private final int MSG_NOUVEAU_MESSAGE = 0;
    private final int MSG_CHARGER_MESSAGES = 1;
    private final int MSG_ERREUR = 2;
    private final int MSG_ANNULER = 3;
    private final int MSG_VÉRIFER_NOUVEAU_MESSAGE = 4;

    public PrésenteurConsulterMessage(VueConsulterMessage nouvelleVue, final Modèle modèle) {
        this.vue = nouvelleVue;
        this.modèle = modèle;
        doitRafrahcir = true;

        this.handlerRéponse = new Handler(){

            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);

                filEsclaveEnvoyerMessage = null;

                if (msg.what == MSG_CHARGER_MESSAGES) {
                    nbMessageActuel = getNbMessages();
                    vue.rafraîchir();
                    rafraichir();
                } else if (msg.what == MSG_VÉRIFER_NOUVEAU_MESSAGE){
                    if(modèle.getNombreMessageTotale() > nbMessageActuel) {
                        getMessages(modèle.getUtilisateurEnRevue());
                    } else {
                        rafraichir();
                    }
                } else if (msg.what == MSG_NOUVEAU_MESSAGE){
                    vue.viderTxtMessage();
                    getMessages(modèle.getUtilisateurEnRevue());
                    doitRafrahcir = true
                } else if ( msg.what == MSG_ERREUR ) {
                    Log.e("Brawler", "Erreur d'accès à l'API", (Throwable) msg.obj);
                }
            }
        };

    }

    public void setSource(SourceMessage source) {
        this.source = source;
    }

    public void commencerVoirMessage(){
        getNombreMessagesApi(modèle.getUtilisateurEnRevue());
    }


    public void envoyerMessage(final String texte){
        vue.changerBtnEnvoyer(false);
        filEsclaveEnvoyerMessage = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message msg = null;
                        try {
                            Thread.sleep(0);
                            doitRafrahcir = false;
                            InteracteurMessage.getInstance(source).envoyerMessage(modèle.getUtilisateurEnRevue(), texte);
                            msg = handlerRéponse.obtainMessage( MSG_NOUVEAU_MESSAGE );
                        } catch (InterruptedException e) {
                            msg = handlerRéponse.obtainMessage( MSG_ANNULER );
                        } catch (MessageException e) {
                            msg = handlerRéponse.obtainMessage( MSG_ANNULER );
                        }

                        handlerRéponse.sendMessage( msg );
                    }
                });
        filEsclaveEnvoyerMessage.start();
    }

    public void rafraichir() {
        if(doitRafrahcir) {
            this.handlerRafraîchir = new Handler();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    getMessages(modèle.getUtilisateurEnRevue());
                }
            };

            handlerRafraîchir.postDelayed(runnable, 2000);
        }
    }

    public void getMessages(final int idUtilisateur){
        filEsclaveEnvoyerMessage = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message msg = null;
                        try {
                            modèle.setListeMessage(InteracteurMessage.getInstance(source).getMessagesparUtilisateursEntreDeux(idUtilisateur, nbMessageActuel, nbMessageActuel));
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

    public void getNombreMessagesApi(final int idUtilisateur){
        filEsclaveEnvoyerMessage = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message msg = null;
                        try {
                             modèle.setNombreMessageTotale(InteracteurMessage.getInstance(source).obtenirNombreMessageParUtilisateur(idUtilisateur));
                             msg = handlerRéponse.obtainMessage(MSG_VÉRIFER_NOUVEAU_MESSAGE);
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

    public int getNbMessages() {
        if(modèle.getMessages() != null)
            return modèle.getMessages().size();
        return 0;
    }

    public com.example.brawler.domaine.entité.Message getMessageParPos(int id){
        return modèle.getMessages().get(id);
    }

    public int getIdUtilisateur() {
        return modèle.getUtilisateurEnRevue();
    }

    public void arrêterRafraichir(){
        doitRafrahcir = false;
    }

    public void commencerRafraichir(){
        doitRafrahcir = true;
    }
}
