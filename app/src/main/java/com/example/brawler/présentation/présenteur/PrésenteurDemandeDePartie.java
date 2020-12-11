package com.example.brawler.présentation.présenteur;

import android.os.Handler;
import android.os.Message;

import com.example.brawler.DAO.SourcePartiesApi;
import com.example.brawler.R;
import com.example.brawler.domaine.entité.Partie;
import com.example.brawler.domaine.intéracteur.InteracteurAquistionPartie;
import com.example.brawler.domaine.intéracteur.SourceParties;
import com.example.brawler.domaine.intéracteur.UtilisateursException;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.vue.VueDemandeDePartie;

public class PrésenteurDemandeDePartie {
    private final Handler handlerRéponse;
    private Thread filEsclaveEnvoyerMessage = null;

    VueDemandeDePartie vue;
    Modèle modèle;
    SourceParties sourceParties;

    private boolean afficherDemandeDePartie;

    private final int MSG_CHARGER_PARTIE = 1;
    private final int MSG_ERREUR = 2;
    private final int MSG_PARTIE_ACCEPTER = 3;
    private final int MSG_REFUSER_PARTIE = 4;

    /**
     * Créer le présenteur
     * @param vue
     * @param modèle
     */
    public PrésenteurDemandeDePartie(final VueDemandeDePartie vue, Modèle modèle) {
        this.vue = vue;
        this.modèle = modèle;

        this.afficherDemandeDePartie = true;
        this.handlerRéponse = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                filEsclaveEnvoyerMessage = null;

                if (msg.what == MSG_CHARGER_PARTIE || msg.what == MSG_PARTIE_ACCEPTER  || msg.what == MSG_REFUSER_PARTIE) {
                    vue.chargerAdapterListePartie(afficherDemandeDePartie);
                    vue.rafraichirVue();
                } else if (msg.what == MSG_ERREUR) {

                }
            }
        };
    }

    /**
     * Permet de mettre la source vers les demandes de parties
     * @param sourceParties
     */
    public void setSourceParties(SourceParties sourceParties) {
        this.sourceParties = sourceParties;
    }

    /**
     * permet de démarrer pour la première fois le présenteur
     */
    public void démarer(){
        if(afficherDemandeDePartie) {
            chercherDemandeDePartie();
        } else {
            checherPartieEnCour();
        }
    }



    /**
     * lance l'acceptation d'une demande de partie
     * @param position
     */
    public void accepeterDemande(int position) {
        lancerFileEsclaveAccepterDemande(modèle.getParties().get(position));
    }

    /**
     * lance si l'utilisateur décide de refuser une demande de partie
     * @param position
     */
    public void refuserDemande(int position) {
        lancerFileEsclaveRefuserDemande(modèle.getParties().get(position));
    }

    /**
     * lance une file escable qui va chercher les parties dans l'api
     */
    private void chercherDemandeDePartie(){
        filEsclaveEnvoyerMessage = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message msg = null;
                        try {
                            modèle.setParties(InteracteurAquistionPartie.getInstance(sourceParties).getDemandePartie());
                            msg = handlerRéponse.obtainMessage( MSG_CHARGER_PARTIE );
                        } catch (SourcePartiesApi.SourcePartieApiException e) {
                            msg = handlerRéponse.obtainMessage( MSG_ERREUR );
                        }

                        handlerRéponse.sendMessage( msg );
                    }
                });
        filEsclaveEnvoyerMessage.start();
    }

    private void checherPartieEnCour() {
        filEsclaveEnvoyerMessage = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message msg = null;
                        try {
                            modèle.setParties(InteracteurAquistionPartie.getInstance(sourceParties).getPartieEnCour());
                            msg = handlerRéponse.obtainMessage( MSG_CHARGER_PARTIE );
                        } catch (SourcePartiesApi.SourcePartieApiException e) {
                            msg = handlerRéponse.obtainMessage( MSG_ERREUR );
                        }

                        handlerRéponse.sendMessage( msg );
                    }
                });
        filEsclaveEnvoyerMessage.start();
    }

    /**
     * lance l'intérateur pour accepter la demande de partie
     * @param partie
     */
    private void lancerFileEsclaveAccepterDemande(final Partie partie) {
        filEsclaveEnvoyerMessage = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message msg = null;
                        try {
                            InteracteurAquistionPartie.getInstance(sourceParties).enovyerDemandePartie(partie.getAdversaire().getId());
                            modèle.getParties().remove(partie);
                            msg = handlerRéponse.obtainMessage( MSG_PARTIE_ACCEPTER );
                        } catch (SourcePartiesApi.SourcePartieApiException e) {
                            msg = handlerRéponse.obtainMessage( MSG_ERREUR );
                        } catch (UtilisateursException e) {
                            msg = handlerRéponse.obtainMessage( MSG_ERREUR );
                        }

                        handlerRéponse.sendMessage( msg );
                    }
                });
        filEsclaveEnvoyerMessage.start();
    }

    /**
     * Lance l'interacteur pour refuser la demande de partie
     * @param partie
     */
    private void lancerFileEsclaveRefuserDemande(final Partie partie) {
        filEsclaveEnvoyerMessage = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message msg = null;
                        try {
                            InteracteurAquistionPartie.getInstance(sourceParties).refuserDemandePartie(partie.getAdversaire().getId());
                            modèle.getParties().remove(partie);
                            msg = handlerRéponse.obtainMessage( MSG_REFUSER_PARTIE );
                        } catch (SourcePartiesApi.SourcePartieApiException | UtilisateursException e) {
                            msg = handlerRéponse.obtainMessage( MSG_ERREUR );
                        }

                        handlerRéponse.sendMessage( msg );
                    }
                });
        filEsclaveEnvoyerMessage.start();
    }

    /**
     * obtient le nombre de demande
     * @return
     */
    public int getNbDemande() {
        if(modèle.getParties().size() > 0)
            return modèle.getParties().size();
        return 0;
    }

    /**
     * Retourne une partie par id
     * @param position
     * @return
     */
    public Partie getDemandeParId(int position) {
        return  modèle.getParties().get(position);
    }


    /**
     *
     * @param modèle
     */
    public void setModèle(Modèle modèle) {
        this.modèle = modèle;
    }

    public void changerAffichage(boolean bool) {
        modèle.getParties().removeAll(modèle.getParties());
        afficherDemandeDePartie = bool;
        if(afficherDemandeDePartie){
            vue.setTextTvTypePartite(R.string.vosDemandeDePArtie);
        } else {
            vue.setTextTvTypePartite(R.string.vosPartieEnCour);
        }
    }

    public void accederPartie(int position) {
        modèle.getParties().get(position).getId();
    }
}
