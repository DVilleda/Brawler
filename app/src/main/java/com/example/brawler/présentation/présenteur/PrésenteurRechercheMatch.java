package com.example.brawler.présentation.présenteur;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.intéracteur.InteracteurAquisitionUtilisateur;
import com.example.brawler.domaine.intéracteur.SourceUtilisateurs;
import com.example.brawler.domaine.intéracteur.UtilisateursException;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.vue.VueRechercheMatch;

public class PrésenteurRechercheMatch {

    private VueRechercheMatch vue;
    private Modèle modèle;
    private SourceUtilisateurs source;
    private Boolean parNiveau;

    private final Handler handlerRéponse;

    private Thread filEsclave = null;

    private final int MSG_NOUVEAU_UTILISATEUR = 0;
    private final int MSG_ERREUR = 1;
    private final int MSG_ANNULER = 2;

    public PrésenteurRechercheMatch(VueRechercheMatch nouvelleVue, Modèle nouveauModèle) {
        this.vue = nouvelleVue;
        this.modèle = nouveauModèle;
        this.parNiveau = false;

        this.handlerRéponse = new Handler(){

            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);

                filEsclave = null;

                if ( msg.what == MSG_NOUVEAU_UTILISATEUR ) {
                    vue.afficherUtilisateur(modèle.getUtilisateurActuel());
                }
                else if ( msg.what == MSG_ERREUR ) {
                    Log.e("Brawler", "Erreur d'accès à l'API", (Throwable) msg.obj);
                }
            }
        };
    }

    public void setSource(SourceUtilisateurs source) {
        this.source = source;
    }

    public void prochainUtilsateur() {

        if (modèle.getListUtilisateurs().size() < 1 || modèle.getListUtilisateurs().size() > modèle.getUtilisateurEnRevue()) {
                chargerNouvelleUtilisateur();
        } else {
            modèle.prochainUtilisateur();
            vue.afficherUtilisateur(modèle.getUtilisateurActuel());
        }


    }

    public void changerRecherche(Boolean bool) {
        if(bool != parNiveau){
            parNiveau = bool;
            modèle.viderListe();
            prochainUtilsateur();
        }
    }

    private void chargerNouvelleUtilisateur() {
        modèle.viderListe();
        filEsclave = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message msg = null;
                        try {
                            Thread.sleep(0);

                            if (parNiveau) {
                                modèle.setListeUtilisateurs(InteracteurAquisitionUtilisateur.getInstance(source).getNouvelUtilsaiteurParNiveau(Niveau.DÉBUTANT));
                            } else {
                                modèle.setListeUtilisateurs(InteracteurAquisitionUtilisateur.getInstance(source).getNouvelleUtilisateur());
                            }
                            msg = handlerRéponse.obtainMessage( MSG_NOUVEAU_UTILISATEUR );
                        } catch (UtilisateursException e) {
                            msg = handlerRéponse.obtainMessage( MSG_ERREUR, e );
                        } catch (InterruptedException e) {
                            msg = handlerRéponse.obtainMessage( MSG_ANNULER );
                        }

                        handlerRéponse.sendMessage( msg );
                    }
                });
        filEsclave.start();

    }

}
