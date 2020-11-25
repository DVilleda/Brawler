package com.example.brawler.présentation.présenteur;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.domaine.intéracteur.InteracteurChargementProfil;
import com.example.brawler.domaine.intéracteur.SourceUtilisateur;
import com.example.brawler.domaine.intéracteur.UtilisateursException;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.vue.VueProfil;
import com.example.brawler.présentation.vue.VueProfilModif;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
                            if(InteracteurChargementProfil.getInstance(_source).chargerUtilisateurActuel() != null)
                            {
                                _modèle.setUtilisateur(InteracteurChargementProfil.getInstance(_source).chargerUtilisateurActuel());
                            }else {
                                _modèle.setUtilisateur(InteracteurChargementProfil.getInstance(_source).getUtilisateur());
                            }
                            msg = handlerRéponse.obtainMessage(MSG_CHARGER_UTILISATEUR);
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

    public void DeconnecterUtilisateur()
    {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(vueProfil.getActivity().getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("token", "");
        editor.apply();
    }

    /**
     * Cette ce charge de verifier l'orientation de la photo puis changer cette orientation selon un angle
     * @param bitmap la photo
     * @param angle l'angle de rotation
     * @return la photo avec une nouvelle rotation
     */
    public static Bitmap changerOrientationImage(Bitmap bitmap, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                matrix, true);
    }
}
