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

    public PrésenteurConsulterMessage(VueConsulterMessage nouvelleVue, final Modèle modèle) {
        this.vue = nouvelleVue;
        this.modèle = modèle;
        nbMessageActuel = 0;
        doitRafrahcir = true;


        this.handlerRéponse = new Handler(){

            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);

                filEsclaveEnvoyerMessage = null;

                if (msg.what == MSG_CHARGER_MESSAGES) {
                    if(nbMessageActuel != getNbMessages()) {
                        nbMessageActuel = getNbMessages();
                        vue.rafraîchir();
                        if(doitRafrahcir)
                            rafraichir();
                    } else {
                        if(doitRafrahcir)
                            rafraichir();
                    }
                } else if (msg.what == MSG_NOUVEAU_MESSAGE){
                    vue.viderTxtMessage();
                    getMessages(modèle.getUtilisateurEnRevue());
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
        vue.changerBtnEnvoyer(false);
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

    public void rafraichir() {

        this.handlerRafraîchir = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.d("se", "rafrachit");
                getMessages(modèle.getUtilisateurEnRevue());
            }
        };

        handlerRafraîchir.postDelayed(runnable, 1000);
    }

    public void getMessages(final int idUtilisateur){
        filEsclaveEnvoyerMessage = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message msg = null;
                        try {
                            Thread.sleep(0);
                            modèle.setListeMessage(InteracteurMessage.getInstance(source).getMessagesparUtilisateurs(idUtilisateur));
                            msg = handlerRéponse.obtainMessage( MSG_CHARGER_MESSAGES );
                        } catch (InterruptedException e) {
                            msg = handlerRéponse.obtainMessage( MSG_ANNULER );
                        } catch (MessageException e) {
                            Log.d("bug", String.valueOf(e));
                            msg = handlerRéponse.obtainMessage( MSG_ERREUR );
                        } catch (UtilisateursException e) {
                            Log.d("bug", String.valueOf(e));
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
