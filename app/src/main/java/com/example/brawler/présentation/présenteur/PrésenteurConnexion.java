package com.example.brawler.présentation.présenteur;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.brawler.DAO.SourceUtilisateursApi;
import com.example.brawler.domaine.intéracteur.SourceUtilisateurs;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.vue.VueConnexion;
import com.example.brawler.MockDAO.SourceUtilisateurFictif.*;

import org.json.JSONException;

public class PrésenteurConnexion {

    private VueConnexion vue;
    private Modèle modèle;
    private SourceUtilisateursApi source;

    private Thread filEsclave = null;
    private Handler handler;

    private int MSG_NOUVELLE_CONNEXION = 0;
    private int MSG_ERREUR = 1;

    String resultat ="";

    public PrésenteurConnexion(final VueConnexion vue, Modèle modèle){
        this.vue = vue;
        this.modèle = modèle;

        this.handler = new Handler(){

            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);

                filEsclave = null;

                if (msg.what == MSG_NOUVELLE_CONNEXION) {
                    vue.notifierConnexionSuccès(resultat);
                }else if ( msg.what == MSG_ERREUR ) {
                    Log.e("Brawler", "Erreur d'accès à l'API", (Throwable) msg.obj);
                }
            }
        };
    }

    public void setSource(SourceUtilisateursApi source) {
        this.source = source;
    }

    public String VerifierInformations(String email, String mdp) {
        String response;
        response = source.Authentifier(email, mdp);
        return response;
    }

    public void ThreadDeAuthentifer(final String email, final String mdp){
        filEsclave = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message msg;
                        resultat = VerifierInformations(email, mdp);
                        msg = handler.obtainMessage(MSG_NOUVELLE_CONNEXION);
                        handler.sendMessage(msg);
                    }

                });
        filEsclave.start();
    }

}
