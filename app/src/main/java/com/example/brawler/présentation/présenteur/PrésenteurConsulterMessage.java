package com.example.brawler.présentation.présenteur;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.intéracteur.InteracteurAquisitionUtilisateur;
import com.example.brawler.domaine.intéracteur.InteracteurMessage;
import com.example.brawler.domaine.intéracteur.MessageException;
import com.example.brawler.domaine.intéracteur.SourceMessage;
import com.example.brawler.domaine.intéracteur.SourceUtilisateurs;
import com.example.brawler.domaine.intéracteur.UtilisateursException;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.vue.VueRechercheMatch;

public class PrésenteurConsulterMessage {

    private VueRechercheMatch vue;
    private Modèle modèle;
    private SourceMessage source;

    private final Handler handlerRéponse;

    private Thread filEsclaveEnvoyerMessage = null;

    private final int MSG_NOUVEAU_MESSAGE = 0;
    private final int MSG_CHARGER_MESSAGES = 1;
    private final int MSG_ERREUR = 2;
    private final int MSG_ANNULER = 3;

    public PrésenteurConsulterMessage() {
        this.handlerRéponse = new Handler(){

            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);

                filEsclaveEnvoyerMessage = null;

                if (msg.what == MSG_CHARGER_MESSAGES) {
                } else if (msg.what == MSG_NOUVEAU_MESSAGE){

                } else if ( msg.what == MSG_ERREUR ) {
                    Log.e("Brawler", "Erreur d'accès à l'API", (Throwable) msg.obj);
                }
            }
        };
    }

    public void setSource(SourceMessage source) {
        this.source = source;
    }


    public void envoyerMessage(final String texte){
        filEsclaveEnvoyerMessage = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message msg = null;
                        try {
                            Thread.sleep(0);
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

    public void getMessages(){
        filEsclaveEnvoyerMessage = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message msg = null;
                        try {
                            Thread.sleep(0);
                            modèle.setListeMessage(InteracteurMessage.getInstance(source).getMessages());

                            msg = handlerRéponse.obtainMessage( MSG_NOUVEAU_MESSAGE );
                        } catch (InterruptedException e) {
                            msg = handlerRéponse.obtainMessage( MSG_ANNULER );
                        } catch (MessageException e) {
                            msg = handlerRéponse.obtainMessage( MSG_ANNULER );
                        } catch (UtilisateursException e) {
                            msg = handlerRéponse.obtainMessage( MSG_ANNULER );
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
}
