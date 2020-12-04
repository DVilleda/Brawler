package com.example.brawler.présentation.présenteur;

import android.os.Handler;
import android.os.Message;

import com.example.brawler.domaine.intéracteur.InteracteurAquisitionUtilisateur;
import com.example.brawler.domaine.intéracteur.InteracteurAquistionPartie;
import com.example.brawler.domaine.intéracteur.SourceParties;
import com.example.brawler.domaine.intéracteur.UtilisateursException;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.vue.VueDemandeDePartie;
import com.example.brawler.présentation.vue.VueRechercheMatch;

public class PrésenteurDemandeDePartie {
    private final Handler handlerRéponse;
    private Thread filEsclaveEnvoyerMessage = null;

    VueDemandeDePartie vue;
    Modèle modèle;
    SourceParties sourceParties;

    private final int MSG_CHARGER_PARTIE = 1;
    private final int MSG_ANNULER = 2;

    public PrésenteurDemandeDePartie(final VueDemandeDePartie vue, Modèle modèle) {
        this.vue = vue;
        this.modèle = modèle;

        this.handlerRéponse = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                filEsclaveEnvoyerMessage = null;

                if (msg.what == MSG_CHARGER_PARTIE) {
                    vue.rafraichirVue();
                } else if (msg.what == MSG_ANNULER) {

                }
            }
        };
    }

    public void setSourceParties(SourceParties sourceParties) {
        this.sourceParties = sourceParties;
    }

    public void démarer(){
        chercherDemandeDePartie();
    }

    public void accepeterDemande(int position) {
        lancerFileEsclaveAccepterDemande(modèle.getParties().get(position).getId());
    }

    public void refuserDemande(int position) {
    }

    private void chercherDemandeDePartie(){
        filEsclaveEnvoyerMessage = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message msg = null;
                        try {
                            Thread.sleep(0);
                            modèle.setParties(InteracteurAquistionPartie.getInstance(sourceParties).getDemandePartie());
                            msg = handlerRéponse.obtainMessage( MSG_CHARGER_PARTIE );
                        } catch (InterruptedException e) {
                            msg = handlerRéponse.obtainMessage( MSG_ANNULER );
                        }

                        handlerRéponse.sendMessage( msg );
                    }
                });
        filEsclaveEnvoyerMessage.start();
    }

    private void lancerFileEsclaveAccepterDemande(int id) {
    }

}
