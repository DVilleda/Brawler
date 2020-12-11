package com.example.brawler.présentation.présenteur;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.intéracteur.ILocalisationUtilisateur;
import com.example.brawler.domaine.intéracteur.InteracteurAquisitionUtilisateur;
import com.example.brawler.domaine.intéracteur.InteracteurAquisitionUtilisateurs;
import com.example.brawler.domaine.intéracteur.InteracteurLikeUtilisateur;
import com.example.brawler.domaine.intéracteur.InteracteurNiveaux;
import com.example.brawler.domaine.intéracteur.InterfaceUtiliasteur;
import com.example.brawler.domaine.intéracteur.LocalisationUtilisateur;
import com.example.brawler.domaine.intéracteur.SourceLike;
import com.example.brawler.domaine.intéracteur.SourceUtilisateur;
import com.example.brawler.domaine.intéracteur.SourceUtilisateurs;
import com.example.brawler.domaine.intéracteur.UtilisateursException;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.vue.VueRechercheMatch;

import java.io.IOException;

public class PrésenteurRechercheMatch {

    private VueRechercheMatch vue;
    private Modèle modèle;
    private SourceUtilisateurs sourceUtilisateurs;
    private SourceUtilisateur sourceUtilisateur;
    private SourceLike sourceLike;
    private Boolean parNiveau;

    private final Handler handlerRéponseApi;

    private Thread filEsclave = null;

    private final int MSG_NOUVEAU_UTILISATEURS = 0;
    private final int MSG_AFFICHER_UTILISATEUR = 1;
    private final int MSG_NOUVEAU_LIKE=2;
    private final int MSG_ERREUR = 3;
    private final int MSG_ANNULER = 4;
    private final int MSG_LOCALISATION_A_JOUR = 5;
    private final int MSG_CHARGER_UTILISATEUR_ACTUEL = 6;


    /**
     * Constructeur
     * @param nouvelleVue
     * @param nouveauModèle
     */
    public PrésenteurRechercheMatch(VueRechercheMatch nouvelleVue, Modèle nouveauModèle) {
        this.vue = nouvelleVue;
        this.modèle = nouveauModèle;
        this.parNiveau = false;

        this.handlerRéponseApi = new Handler(){

            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);

                filEsclave = null;

                if ( msg.what == MSG_NOUVEAU_UTILISATEURS ) {
                    lancerFileEsclaveObtenirUtilisateurParId();
                } else if(msg.what == MSG_CHARGER_UTILISATEUR_ACTUEL) {
                    lancerChargerUtilisateur();
                } else if (msg.what == MSG_AFFICHER_UTILISATEUR) {
                    if(modèle.getListUtilisateursId().size() > 0) {
                        vue.afficherUtilisateur(modèle.getUtilisateur());
                        vue.toggleÉtatBouton();
                    }
                } else if ( msg.what == MSG_ERREUR ) {
                    Log.e("Brawler", "Erreur d'accès à l'API", (Throwable) msg.obj);
                    vue.toggleÉtatBouton();
                }
                else if (msg.what == MSG_NOUVEAU_LIKE) {
                    prochainUtilsateur();
                    vue.toggleÉtatBouton();
                }

            }
        };
    }

    public void démmarerPrésenteur() {
        lancerFileEsclaveObtenirUtilisateurActuel();
    }

    /**
     * Permet de mettre la source pour obtenir des utilisateurs
     * @param source
     */
    public void setSourceUtilisateurs(SourceUtilisateurs source) {
        this.sourceUtilisateurs = source;
    }

    /**
     * Permet de mettre la source pour obtenir un utilisateurs
     * @param source
     */
    public void setSourceUtilisateur(SourceUtilisateur source) {
        this.sourceUtilisateur = source;
    }


    /**
     * Permet de mettre le source pour liker un utilisateur
     * @param sourceLike
     */
    public void setSourceLike(SourceLike sourceLike) {
        this.sourceLike = sourceLike;
    }

    /**
     * Permet a un utilisateur de juger si il aime un utilisateur ou nom et de passer au prochain
     * @param liker
     */
    public void jugerUtilisateur(boolean liker){
        vue.toggleÉtatBouton();
        if(liker){
            lancerFileEsclaveLikerUtilisateur(modèle.getUtilisateur().getId());
        } else {
            prochainUtilsateur();
        }
    }


    /**
     * Lance le chargement du prochain utilisateur dans la liste
     */
    public void prochainUtilsateur() {
        modèle.prochainUtilisateur();
        if (modèle.getListUtilisateursId().size() < 1 || modèle.getListUtilisateursId().size() == modèle.getUtilisateurEnRevue()) {
            lancerChargerUtilisateur();
        } else {
            lancerFileEsclaveObtenirUtilisateurParId();
        }
    }


    /**
     * Lance le chargement de novuel utilsaiteur afin d'en avoir plus lorsque utilisat
     */
    public void lancerChargerUtilisateur(){
        if (modèle.getListUtilisateurs().size() < 1 || modèle.getListUtilisateursId().size() == modèle.getUtilisateurEnRevue()) {
            vue.toggleÉtatBouton();
            lancerFileEsclaveChargerUtilisateur();
        } else if(modèle.getListUtilisateursId().size() > modèle.getUtilisateurEnRevue()){

            vue.afficherUtilisateur(modèle.getUtilisateur());
        }
    }


    /**
     * Change le paramêmtr de recherhce selon le booléen envoyer. La recherche est par niveau si true
     * @param bool
     */
    public void changerRecherche(Boolean bool) {
        if(bool != parNiveau){
            parNiveau = bool;
            lancerFileEsclaveChargerUtilisateur();
        }
    }

    /**
     * Lance une fil esclave pour charger un nouvelle utilisateur
     */
    private void lancerFileEsclaveChargerUtilisateur(){

        filEsclave = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message msg = null;
                        try {
                            Thread.sleep(0);
                            modèle.viderListeUtilisateurs();
                            if(parNiveau) {
                                modèle.setListUtilisateursId(InteracteurAquisitionUtilisateurs.getInstance(sourceUtilisateurs).getNouvelleUtilisateurgetNouvelUtilsaiteurParNiveauIdSeulement(modèle.getUtilisateurDeApplication().getNiveau()));
                            } else {
                                modèle.setListUtilisateursId(InteracteurAquisitionUtilisateurs.getInstance(sourceUtilisateurs).getNouvelleUtilisateurIdSeulement());
                            }



                            msg = handlerRéponseApi.obtainMessage( MSG_NOUVEAU_UTILISATEURS );
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

    /**
     * Lance la file escave pour envoyer le like de un utilisateur selon son id
     * @param utilisateurLiker
     */
    private void lancerFileEsclaveLikerUtilisateur(final int utilisateurLiker){
        filEsclave = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message msg = null;
                        try {
                            Thread.sleep(0);

                            InteracteurLikeUtilisateur.getInstance(sourceLike).likerUtilisateur(utilisateurLiker);
                            modèle.setListUtilisateursId(InteracteurAquisitionUtilisateurs.getInstance(sourceUtilisateurs).getNouvelleUtilisateurIdSeulement());
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

    public void MettreLocalisationAJour(String clé, String localisation){
        LocalisationUtilisateur localisationUtilisateur = LocalisationUtilisateur.getInstance();
        localisationUtilisateur.setLocalisation(clé, localisation);
    }
    /**
     * Lance la file esclave
     * @param clé
     * @param localisation
     */
    public void lancerFilEsclaveMettreLocalisationAJour (final String clé, final String localisation){
        filEsclave = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message msg = null;
                        try {
                            MettreLocalisationAJour(clé, localisation);
                            msg = handlerRéponseApi.obtainMessage( MSG_LOCALISATION_A_JOUR );
                        } catch (Exception e) {
                            msg = handlerRéponseApi.obtainMessage( MSG_ERREUR, e );
                        }

                        handlerRéponseApi.sendMessage( msg );
                    }
                });
        filEsclave.start();
    }

    /**
     * Lance la fille escalve pour obtenir un utilisateur dans l'api par id
     */
    private void lancerFileEsclaveObtenirUtilisateurParId () {
        filEsclave = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message msg = null;
                        try {
                            Thread.sleep(0);
                            if(modèle.getListUtilisateursId().size() > 0){
                                if(parNiveau) {
                                    modèle.setListUtilisateursId(InteracteurAquisitionUtilisateurs.getInstance(sourceUtilisateurs).getNouvelleUtilisateurgetNouvelUtilsaiteurParNiveauIdSeulement(modèle.getUtilisateurDeApplication().getNiveau()));
                                } else {

                                        modèle.setUtilisateur(InteracteurAquisitionUtilisateur.getInstance(sourceUtilisateur).getUtilisateurParId(modèle.getListUtilisateursId().get(modèle.getUtilisateurIdActuel())));
                                }
                            }
                            msg = handlerRéponseApi.obtainMessage( MSG_AFFICHER_UTILISATEUR );
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

    /**
     * Lance la file esclave qui permet d'obtenir les info de l'utilisateur atctuel
     */
    private void lancerFileEsclaveObtenirUtilisateurActuel () {
        filEsclave = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message msg = null;
                        try {
                            Thread.sleep(0);
                            modèle.setUtilisateurDeApplication(InteracteurAquisitionUtilisateur.getInstance(sourceUtilisateur).getUtilisateurActuel());
                            msg = handlerRéponseApi.obtainMessage( MSG_CHARGER_UTILISATEUR_ACTUEL );
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

    public Niveau updateNiveauUserConnecté(String clé){
        InteracteurNiveaux instance = InteracteurNiveaux.getInstance();
        return instance.getNiveu(clé);
    }

    public Niveau ThreadDeUpdateNiveauUserConnecté(String clé){
        final Niveau[] newLevel = new Niveau[1];
        filEsclave = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        newLevel[0] = updateNiveauUserConnecté(clé);
                    }
                });
        filEsclave.start();
        return newLevel[0];
    }

}
