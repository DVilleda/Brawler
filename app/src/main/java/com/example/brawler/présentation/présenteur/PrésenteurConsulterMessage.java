package com.example.brawler.présentation.présenteur;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.brawler.DAO.SourcePartiesApi;
import com.example.brawler.domaine.intéracteur.InteracteurAquisitionUtilisateur;
import com.example.brawler.domaine.intéracteur.InteracteurAquistionPartie;
import com.example.brawler.domaine.intéracteur.InteracteurMessage;
import com.example.brawler.domaine.intéracteur.MessageException;
import com.example.brawler.domaine.intéracteur.SourceMessage;
import com.example.brawler.domaine.intéracteur.SourceParties;
import com.example.brawler.domaine.intéracteur.SourceUtilisateur;
import com.example.brawler.domaine.intéracteur.UtilisateursException;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.vue.VueConsulterMessage;

import java.io.IOException;

public class PrésenteurConsulterMessage {

    private VueConsulterMessage vue;
    private Modèle modèle;
    private SourceMessage sourceMessage;
    private SourceUtilisateur sourceUtilisateur;
    private SourceParties sourceParite;

    private int nbMessageActuel;
    private boolean premierCharqement;
    private boolean doitRafrahcir;
    private boolean doitCharger;

    private final Handler handlerRéponse;
    private Handler handlerRafraîchir;

    private Thread filEsclaveEnvoyerMessage = null;

    private final int MSG_NOUVEAU_MESSAGE = 0;
    private final int MSG_CHARGER_MESSAGES = 1;
    private final int MSG_ERREUR = 2;
    private final int MSG_ANNULER = 3;
    private final int MSG_VÉRIFER_NOUVEAU_MESSAGE = 4;
    private final int MSG_UTILIASTEUR_CHARGER = 5;
    private final int MSG_DEMANDE_ENVOYER = 6;

    private boolean envoyerVueDébutMessage;
    private boolean nouveauMessage = false;

    public PrésenteurConsulterMessage(VueConsulterMessage nouvelleVue, final Modèle modèle) {
        this.vue = nouvelleVue;
        this.modèle = modèle;
        doitRafrahcir = true;
        nbMessageActuel = 0;
        premierCharqement = true;
        doitCharger = true;
        envoyerVueDébutMessage = true;

        this.handlerRéponse = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                filEsclaveEnvoyerMessage = null;

                if (msg.what == MSG_CHARGER_MESSAGES) {
                    if (modèle.getMessages().size() != 0) {
                        vue.rafraîchir();
                        if (envoyerVueDébutMessage) {
                            vue.allerPremierMessage();
                            envoyerVueDébutMessage = false;
                        }
                    }
                    doitRafrahcir = true;
                    rafraichir();
                    doitCharger = true;
                } else if (msg.what == MSG_DEMANDE_ENVOYER){
                    //posibitlité pour plus tard
                }else if (msg.what == MSG_VÉRIFER_NOUVEAU_MESSAGE) {
                    vue.rafraîchir();
                    if (modèle.getNombreMessageTotale() == 0) {
                        rafraichir();
                    } else if (modèle.getNombreMessageTotale() > nbMessageActuel) {
                        premierCharqement = true;
                        chargerNoveuMessage(modèle.getUtilisateurEnRevue(), 0, modèle.getNombreMessageTotale() - nbMessageActuel);
                        nbMessageActuel = modèle.getNombreMessageTotale();
                    } else {
                        rafraichir();
                    }
                } else if (msg.what == MSG_NOUVEAU_MESSAGE) {
                    vue.viderTxtMessage();
                    vue.rafraîchir();
                } else if (msg.what == MSG_UTILIASTEUR_CHARGER) {
                    if(modèle.getUtilisateur().getPhoto() != null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(modèle.getUtilisateur().getPhoto(), 0, modèle.getUtilisateur().getPhoto().length);
                        modèle.setBitmapPhoto(bitmap);
                    }
                    vue.setInfoUtilisateur(modèle.getUtilisateur().getNom(), modèle.getUtilisateur().getPhoto());
                    getNombreMessagesApi(modèle.getUtilisateurEnRevue());
                } else if (msg.what == MSG_ERREUR) {
                    Log.e("Brawler", "Erreur d'accès à l'API", (Throwable) msg.obj);
                }
            }


        };

    }



    /**
     *
     * @param sourceMessage
     */
    public void setSourceMessage(SourceMessage sourceMessage) {
        this.sourceMessage = sourceMessage;
    }

    /**
     *
     * @param sourceUtilisateur
     */
    public void setSourceUtilisateur(SourceUtilisateur sourceUtilisateur) {
        this.sourceUtilisateur = sourceUtilisateur;
    }

    /**
     *
     * @param sourceParite
     */
    public void setSourceParite(SourceParties sourceParite) {
        this.sourceParite = sourceParite;
    }

    /**
     *
     */
    public void commencerVoirMessage(){
        chargerUtilisateur();
    }

    /**
     *
     */
    public void envoyerDemandePartie() {
        lancerFileEsclaveEnvoyerDemandeDePartie();
    }

    /**
     * charge l'utilisateur de message
     */
    private void chargerUtilisateur() {
        filEsclaveEnvoyerMessage = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message msg = null;
                        try {
                            Thread.sleep(0);
                            modèle.setUtilisateur(InteracteurAquisitionUtilisateur.getInstance(sourceUtilisateur).getUtilisateurParId(modèle.getUtilisateurEnRevue()));
                            msg = handlerRéponse.obtainMessage( MSG_UTILIASTEUR_CHARGER );
                        } catch (InterruptedException e) {
                            msg = handlerRéponse.obtainMessage( MSG_ANNULER );
                        } catch (UtilisateursException e) {
                            e.printStackTrace();
                        }

                        handlerRéponse.sendMessage( msg );
                    }
                });
        filEsclaveEnvoyerMessage.start();
    }


    /**
     * envoi un message a l'api
     * @param texte
     */
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
                            envoyerVueDébutMessage = true;
                            modèle.getMessages().add(0, InteracteurMessage.getInstance(sourceMessage).envoyerMessage(modèle.getUtilisateurEnRevue(), texte));
                            msg = handlerRéponse.obtainMessage( MSG_NOUVEAU_MESSAGE );
                        } catch (InterruptedException e) {
                            msg = handlerRéponse.obtainMessage( MSG_ANNULER );
                        } catch (MessageException e) {
                            msg = handlerRéponse.obtainMessage( MSG_ERREUR );
                        } catch (UtilisateursException e) {
                            msg = handlerRéponse.obtainMessage( MSG_ERREUR );
                        } catch (IOException e) {
                            msg = handlerRéponse.obtainMessage( MSG_ERREUR );
                        }

                        handlerRéponse.sendMessage( msg );
                    }
                });
        filEsclaveEnvoyerMessage.start();
    }

    /**
     * rafrachie la vue
     */
    public void rafraichir() {

        if(vue.rvAuMax() && doitCharger && modèle.getMessages().size() <= modèle.getNombreMessageTotale()) {
            lancerCahrgemetnMessage();
            doitCharger = false;
            doitRafrahcir = false;
        }

        if(doitRafrahcir) {
            this.handlerRafraîchir = new Handler();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    getNombreMessagesApi(modèle.getUtilisateurEnRevue());
                }
            };
            handlerRafraîchir.postDelayed(runnable, 4000);
        }
    }

    /**
     * obteint tout les message
     * @param idUtilisateur
     * @param début
     * @param fin
     */
    public void getMessages(final int idUtilisateur, final int début, final int fin){
        filEsclaveEnvoyerMessage = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message msg = null;
                        try {
                            modèle.ajouterListeMessage(InteracteurMessage.getInstance(sourceMessage).getMessagesparUtilisateursEntreDeux(idUtilisateur, début, fin));
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

    private void chargerNoveuMessage(final int idUtilisateur, final int début, final int fin) {
            filEsclaveEnvoyerMessage = new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            Message msg = null;
                            try {
                                modèle.ajouterDebutListe(InteracteurMessage.getInstance(sourceMessage).getMessagesparUtilisateursEntreDeux(idUtilisateur, début, fin));
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

    /**
     * obtient le nombre de message d'un api
     * @param idUtilisateur
     */
    public void getNombreMessagesApi(final int idUtilisateur){
        filEsclaveEnvoyerMessage = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message msg = null;
                        try {
                             modèle.setNombreMessageTotale(InteracteurMessage.getInstance(sourceMessage).obtenirNombreMessageParUtilisateur(idUtilisateur));
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

    /**
     * obitent le nombre de mesasge dans le modèle
     * @return
     */
    public int getNbMessages() {
        if(modèle.getMessages() != null)
            return modèle.getMessages().size();
        return 0;
    }

    /**
     * obtient un mesage par postion du modèle
     * @param id
     * @return
     */
    public com.example.brawler.domaine.entité.Message getMessageParPos(int id){
        return modèle.getMessages().get(id);
    }

    /**
     * cahrge une partie des messages de l'api vers le modèle
     */
    private void lancerCahrgemetnMessage() {
        if(premierCharqement) {
            if(modèle.getNombreMessageTotale() < 10) {
                getMessages(modèle.getUtilisateurEnRevue(), 0, modèle.getNombreMessageTotale());
            } else {
                getMessages(modèle.getUtilisateurEnRevue(), 0, 10);
            }
            premierCharqement = false;
        } else {
            if (modèle.getMessages().size() + 10 > modèle.getNombreMessageTotale())
                getMessages(modèle.getUtilisateurEnRevue(), modèle.getMessages().size(), modèle.getNombreMessageTotale());
            else
                getMessages(modèle.getUtilisateurEnRevue(), modèle.getMessages().size(), modèle.getMessages().size() + 10);
        }
        nbMessageActuel = modèle.getNombreMessageTotale();

    }

    /**
     * envoie la deamdne de partie
     */
    private void lancerFileEsclaveEnvoyerDemandeDePartie() {
        filEsclaveEnvoyerMessage = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message msg = null;
                        try {
                            Thread.sleep(0);
                            InteracteurAquistionPartie.getInstance(sourceParite).enovyerDemandePartie(modèle.getUtilisateurEnRevue());
                            msg = handlerRéponse.obtainMessage( MSG_DEMANDE_ENVOYER );
                        } catch (InterruptedException e) {
                            msg = handlerRéponse.obtainMessage( MSG_ANNULER );
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
     * obtient le id d'utilisateur
     * @return
     */
    public int getIdUtilisateur() {
        return modèle.getUtilisateurEnRevue();
    }

    /**
     * arêtte de racirchir les message
     */
    public void arrêterRafraichir(){
        doitRafrahcir = false;
    }

    /**
     * se remet a rafraichir les messages
     */
    public void commencerRafraichir(){
        doitRafrahcir = true;
    }

    /**
     * retourne la photo de l'utilisateur que l'on envoie un message
     * @return
     */
    public Bitmap getPhotoUtilisateur() {
        return modèle.getBitmapPhoto();
    }

    /**
     * stop l'atctivité
     */
    public void stopActivity() {
        vue.getActivity().finish();
    }




}
