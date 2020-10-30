package com.example.brawler.présentation.présenteur;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.domaine.intéracteur.InteracteurChargementProfil;
import com.example.brawler.domaine.intéracteur.SourceUtilisateur;
import com.example.brawler.domaine.intéracteur.UtilisateursException;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.vue.VueProfil;
import com.example.brawler.présentation.vue.VueProfilModif;

public class PrésenteurProfil {

    /**
     * Paramètres du présenteur
     */
    VueProfil vueProfil;
    VueProfilModif vueProfilModif;
    SourceUtilisateur _source;
    Modèle _modèle;

    private final Handler handlerRéponse;

    private Thread filEsclave = null;

    private final int MSG_CHARGER_UTILISATEUR = 0;
    private final int MSG_ERREUR = 1;
    private final int MSG_ANNULER = 2;

    /**
     * Constructeur du présenteur de la vue Profil
     */
    public PrésenteurProfil(VueProfil vue, Modèle modèle){
        this._modèle = modèle;
        this.vueProfil = vue;

        this.handlerRéponse = new Handler(){

            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);

                filEsclave = null;

                if ( msg.what == MSG_CHARGER_UTILISATEUR) {
                    vueProfil.afficherUtilisateur(_modèle.getUtilisateur());
                }
                else if ( msg.what == MSG_ERREUR ) {
                    Log.e("Brawler", "Erreur d'accès à l'API", (Throwable) msg.obj);
                }
            }
        };
    }
    /**
    public PrésenteurProfil(VueProfilModif vue, Modèle modèle){
        vueProfilModif = vue;
        _modèle = modèle;
    }
    **/

    /**
     * Méthode qui permet de définir la source de données pour le profil
     * @param source
     */
    public void setSourceUtilisateur(SourceUtilisateur source){
        _source = source;
    }

    /**
     * Place l'utilisateur dans le modèle puis affiche ce même utilisateur dans le fragment profil
     */
    public void chargerUtilisateur(){
        filEsclave = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message msg = null;
                        try
                        {
                            Thread.sleep(0);
                            if(_modèle.getUtilisateur() == null) {
                                _modèle.setUtilisateur(InteracteurChargementProfil.getInstance(_source).getUtilisateur());
                                msg = handlerRéponse.obtainMessage(MSG_CHARGER_UTILISATEUR);
                            }
                        } catch (UtilisateursException e) {
                            msg = handlerRéponse.obtainMessage( MSG_ERREUR, e );
                        } catch ( InterruptedException e){
                            msg = handlerRéponse.obtainMessage( MSG_ANNULER, e );
                        }
                        handlerRéponse.sendMessage(msg);
                    }
                }
        );
        filEsclave.start();
    }

    public Utilisateur getUtilisateur(){
        Utilisateur utilisateur=null;
        try {
            utilisateur= InteracteurChargementProfil.getInstance(_source).chargerUtilisateurActuel();
        } catch (UtilisateursException e) {
            e.printStackTrace();
        }
        return utilisateur;
    }

    public void rafraichirPage(){
        if(_modèle.getUtilisateur()!=null)
        vueProfil.afficherUtilisateur(_modèle.getUtilisateur());
    }

    /**
     * Permet de changer la visibilité des données personnelles dans le profil publique
     */
    public void setVisibleInfos(){
        vueProfil.expandInfosProfil();
    }

    /**
     * Permet de modifier le profil de l'utilisateur stocke dans le modele
     * @param utilisateur
     */
    public void modifierUtilisateur(final Utilisateur utilisateur){
        filEsclave = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Message msg = null;
                        try {
                            InteracteurChargementProfil.getInstance(_source).setUtilisateur(utilisateur);
                            _modèle.setUtilisateur(utilisateur);
                            msg = handlerRéponse.obtainMessage(MSG_CHARGER_UTILISATEUR);
                        } catch (UtilisateursException e) {
                            msg = handlerRéponse.obtainMessage(MSG_ERREUR, e);
                        }
                        handlerRéponse.sendMessage(msg);
                    }
                }
        );
        filEsclave.start();
    }
}
