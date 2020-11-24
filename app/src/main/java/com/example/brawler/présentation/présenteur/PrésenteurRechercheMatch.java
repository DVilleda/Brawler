package com.example.brawler.présentation.présenteur;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.intéracteur.InteracteurAquisitionUtilisateurs;
import com.example.brawler.domaine.intéracteur.InteracteurLikeUtilisateur;
import com.example.brawler.domaine.intéracteur.InterfaceUtiliasteur;
import com.example.brawler.domaine.intéracteur.LocalisationUtilisateur;
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
    private final int MSG_LOCALISATION_A_JOUR = 3;


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
                    Log.d("passe", "MSG_NOUVEAU_LIKE" );
                    prochainUtilsateur();
                    vue.toggleÉtatBouton();
                }

            }
        };
    }

    public void setSourceUtilisateurs(SourceUtilisateurs source) {
        Log.d("set:", "source");
        this.sourceUtilisateurs = source;
    }

    public void setSourceLike(SourceLike sourceLike) {
        this.sourceLike = sourceLike;
    }

    public void jugerUtilisateur(boolean liker){
        if(liker){
            vue.toggleÉtatBouton();
            lancerFileEsclaveLikerUtilisateur(modèle.getUtilisateurActuel().getId());
        } else {
            prochainUtilsateur();
        }
    }

    public void prochainUtilsateur() {
        modèle.prochainUtilisateur();
        lancerChargerUtilisateur();
    }

    public void lancerChargerUtilisateur(){
        if (modèle.getListUtilisateurs().size() < 1 || modèle.getListUtilisateurs().size() == modèle.getUtilisateurEnRevue()) {
            chargerNouvelleUtilisateur();
        } else if(modèle.getListUtilisateurs().size() > modèle.getUtilisateurEnRevue()){
            vue.afficherUtilisateur(modèle.getUtilisateurActuel());
            Log.d("id uti:", String.valueOf(modèle.getUtilisateurActuel().getId()));
        }
    }

    public void changerRecherche(Boolean bool) {
        if(bool != parNiveau){
            parNiveau = bool;
            //modèle.viderListe();
            prochainUtilsateur();
        }
    }

    private void chargerNouvelleUtilisateur() {
        vue.toggleÉtatBouton();
        lancerFileEsclaveChargerUtilisateur();

    }

    private void lancerFileEsclaveChargerUtilisateur(){
      
        filEsclave = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message msg = null;
                        try {
                            Thread.sleep(0);
                            modèle.viderListeUtilisateurs();

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

                            InteracteurLikeUtilisateur.getInstance(sourceLike).likerUtilisateur(utilisateurLiker);

                            msg = handlerRéponseApi.obtainMessage( MSG_NOUVEAU_LIKE );
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

    public void lancerFilEsclaveMettreLocalisationAJour (final String clé, final String localisation){
        filEsclave = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message msg = null;
                        try {
                            LocalisationUtilisateur source = new LocalisationUtilisateur();
                            boolean verify = source.setLocalisation(clé, localisation);
                            Log.e("Clé was: ", clé);
                            Log.e("Update was: ", String.valueOf(verify));
                            msg = handlerRéponseApi.obtainMessage( MSG_LOCALISATION_A_JOUR );
                        } catch (Exception e) {
                            msg = handlerRéponseApi.obtainMessage( MSG_ERREUR, e );
                        }

                        handlerRéponseApi.sendMessage( msg );
                    }
                });
        filEsclave.start();
    }

}
