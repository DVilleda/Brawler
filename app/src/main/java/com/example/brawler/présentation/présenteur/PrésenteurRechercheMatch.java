package com.example.brawler.présentation.présenteur;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.intéracteur.InteracteurAquisitionUtilisateurs;
import com.example.brawler.domaine.intéracteur.InteracteurLikeUtilisateur;
import com.example.brawler.domaine.intéracteur.InterfaceUtiliasteur;
import com.example.brawler.domaine.intéracteur.SourceLike;
import com.example.brawler.domaine.intéracteur.SourceUtilisateurs;
import com.example.brawler.domaine.intéracteur.UtilisateursException;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.vue.VueRechercheMatch;

import java.io.IOException;

public class PrésenteurRechercheMatch {

    private VueRechercheMatch vue;
    private Modèle modèle;
    private SourceUtilisateurs sourceUtilisateurs;
    private SourceLike sourceLike;
    private Boolean parNiveau;

    private final Handler handlerRéponseApi;

    private Thread filEsclave = null;

    private final int MSG_NOUVEAU_UTILISATEUR = 0;
    private final int MSG_NOUVEAU_LIKE=1;
    private final int MSG_ERREUR = 2;
    private final int MSG_ANNULER = 3;


    public PrésenteurRechercheMatch(VueRechercheMatch nouvelleVue, Modèle nouveauModèle) {
        this.vue = nouvelleVue;
        this.modèle = nouveauModèle;
        this.parNiveau = false;

        this.handlerRéponseApi = new Handler(){

            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);

                filEsclave = null;

                if ( msg.what == MSG_NOUVEAU_UTILISATEUR ) {
                    vue.afficherUtilisateur(modèle.getUtilisateurActuel());
                    vue.toggleÉtatBouton();
                }
                else if ( msg.what == MSG_ERREUR ) {
                    Log.d("Brawler", "Erreur d'accès à l'API", (Throwable) msg.obj);
                    vue.toggleÉtatBouton();
                }
                else if (msg.what == MSG_NOUVEAU_LIKE) {
                    prochainUtilsateur();
                    vue.toggleÉtatBouton();
                }

            }
        };
    }

    public void setSourceUtilisateurs(SourceUtilisateurs source) {
        this.sourceUtilisateurs = source;
    }

    public void setSourceLike(SourceLike sourceLike) {
        this.sourceLike = sourceLike;
    }

    public void jugerUtilisateur(boolean liker){
        if(liker){
            vue.toggleÉtatBouton();
            Log.d("id: ", String.valueOf(modèle.getUtilisateurActuel().getId()));
            lancerFileEsclaveLikerUtilisateur(modèle.getUtilisateurActuel().getId());
        } else {
            prochainUtilsateur();
        }
    }

    public void prochainUtilsateur() {
        modèle.prochainUtilisateur();
        if (modèle.getListUtilisateurs().size() < 1 || modèle.getListUtilisateurs().size() <= modèle.getUtilisateurEnRevue()) {
            chargerNouvelleUtilisateur();
        } else if(modèle.getListUtilisateurs().size() > modèle.getUtilisateurEnRevue()){
            vue.afficherUtilisateur(modèle.getUtilisateurActuel());
        }
    }

    public void changerRecherche(Boolean bool) {
        if(bool != parNiveau){
            parNiveau = bool;
            modèle.viderListeUtilisateurs();
            prochainUtilsateur();
        }
    }

    private void chargerNouvelleUtilisateur() {
        vue.toggleÉtatBouton();
        lancerFileEsclaveChargerUtilisateur();

    }

    private void lancerFileEsclaveChargerUtilisateur(){
        Log.d("passe:", "file esclave utilisateurs");
        modèle.viderListeUtilisateurs();
        filEsclave = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message msg = null;
                        try {
                            Thread.sleep(0);
                            modèle.viderListe();

                            //TODO mettre le niveau de l'utilisteur actuel
                            if (parNiveau) {
                                modèle.setListeUtilisateurs(InteracteurAquisitionUtilisateurs.getInstance(sourceUtilisateurs).getNouvelUtilsaiteurParNiveau(Niveau.DÉBUTANT));
                            } else {
                                modèle.setListeUtilisateurs(InteracteurAquisitionUtilisateurs.getInstance(sourceUtilisateurs).getNouvelleUtilisateur());
                            }

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

    private void lancerFileEsclaveLikerUtilisateur(final int utilisateurLiker){
        Log.d("passe:", "file esclave");
        filEsclave = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message msg = null;
                        try {
                            Thread.sleep(0);

                            modèle.viderListe();
                            InteracteurLikeUtilisateur.getInstance(sourceLike).likerUtilisateur(utilisateurLiker);

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



}
