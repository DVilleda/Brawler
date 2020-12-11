package com.example.brawler.présentation.présenteur;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.domaine.intéracteur.InteracteurAquisitionUtilisateurs;
import com.example.brawler.domaine.intéracteur.SourceUtilisateurs;
import com.example.brawler.domaine.intéracteur.UtilisateursException;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.vue.VueContacts;
import com.example.brawler.ui.activité.ConsulterMessageActivité;

import java.util.ArrayList;
import java.util.List;

public class PrésenteurContacts {
    private static final String EXTRA_ID_UTILSAITEUR = "com.brawler.idUtilisateur";

    VueContacts vueContacts;
    SourceUtilisateurs _source;
    Modèle _modèle;
    Activity activité;

    private final Handler handlerRéponseApi;

    private Thread filEsclave = null;

    private final int MSG_NOUVEAU_UTILISATEUR = 0;
    private final int MSG_ERREUR = 1;
    private final int MSG_ANNULER = 2;

    public PrésenteurContacts (VueContacts vue, final Modèle modèle, Activity activité){
        this.vueContacts = vue;
        this._modèle = modèle;
        this.activité = activité;

        this.handlerRéponseApi = new Handler(){

            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);

                filEsclave = null;

                if ( msg.what == MSG_NOUVEAU_UTILISATEUR ) {
                    vueContacts.afficherContacts();
                }
                else if ( msg.what == MSG_ERREUR ) {
                    Log.e("Brawler", "Erreur d'accès à l'API", (Throwable) msg.obj);
                }

            }
        };

    }

    public void setSource(SourceUtilisateurs source){this._source =source;}

    public void chargerListeContacts(){
        vueContacts.rafraichirVue();
        filEsclave = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message msg = null;
                        try {
                            Thread.sleep(0);
                            _modèle.viderListeUtilisateurs();

                            _modèle.setListeUtilisateurs(InteracteurAquisitionUtilisateurs.getInstance(_source).getContact());

                            msg = handlerRéponseApi.obtainMessage( MSG_NOUVEAU_UTILISATEUR );
                        } catch (UtilisateursException e) {
                            msg = handlerRéponseApi.obtainMessage( MSG_ERREUR, e );
                        } catch (InterruptedException e) {
                            msg = handlerRéponseApi.obtainMessage( MSG_ANNULER );
                        }

                        handlerRéponseApi.sendMessage( msg );
                    }
                });
        filEsclave.start();
    }

    public void chargerConversationUtilisateur(int idUtilisateur) {
        Intent intentConsulterMessage = new Intent(activité, ConsulterMessageActivité.class);
        intentConsulterMessage.putExtra(EXTRA_ID_UTILSAITEUR, idUtilisateur);
        activité.startActivity(intentConsulterMessage);
    }

    public List<Utilisateur> getListContact() {
        return _modèle.getListUtilisateurs();
    }
}
