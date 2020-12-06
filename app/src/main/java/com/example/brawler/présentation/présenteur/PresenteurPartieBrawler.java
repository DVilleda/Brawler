package com.example.brawler.présentation.présenteur;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.brawler.domaine.entité.Partie;
import com.example.brawler.domaine.intéracteur.InteracteurJouerPartie;
import com.example.brawler.domaine.intéracteur.PartieException;
import com.example.brawler.domaine.intéracteur.SourceDeroulementPartie;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.vue.VuePartieBrawler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class PresenteurPartieBrawler {

    VuePartieBrawler vuePartieBrawler;
    SourceDeroulementPartie _source;
    Modèle modèle;
    Partie partieEnCours;

    private final Handler handlerReponse;
    private Thread filEsclave = null;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private final int MSG_CHARGER_MOUVEMENT = 0;
    private final int MSG_ERREUR = 1;
    private final int MSG_ANNULER = 2;

    public PresenteurPartieBrawler(VuePartieBrawler vue, Modèle leModele){
        this.vuePartieBrawler = vue;
        this.modèle = leModele;

        this.handlerReponse = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                filEsclave = null;

                if (msg.what == MSG_CHARGER_MOUVEMENT){
                    setPartieEnCours(modèle.getPartieChoisi());
                    changerNumTour();
                    ChangerIMGMouvementADV();
                }
                else if (msg.what == MSG_ERREUR){
                    Log.e("Brawler", "Erreur d'accès à l'API", (Throwable) msg.obj);
                }
            }
        };
    }

    public void chargerPartie(final int idPartie){
        filEsclave = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = null;
                try{
                    Thread.sleep(0);
                    modèle.setPartieChoisi(InteracteurJouerPartie.getInstance(_source).getPartieParID(idPartie));

                    msg = handlerReponse.obtainMessage(MSG_CHARGER_MOUVEMENT);
                } catch (PartieException e) {
                    msg = handlerReponse.obtainMessage(MSG_ERREUR, e);
                }catch ( InterruptedException e){
                    msg = handlerReponse.obtainMessage( MSG_ANNULER, e );
                }
                handlerReponse.sendMessage(msg);
            }
        });
        filEsclave.start();
    }

    public void scheduleRafraichirPartie(){
        final Runnable rafraichirPartie = new Runnable() {
            @Override
            public void run() {
                Message msg = null;
                try{
                    Thread.sleep(0);
                    modèle.setPartieChoisi(InteracteurJouerPartie.getInstance(_source).getPartieParID(modèle.getPartieChoisi().getIdPartie()));

                    msg = handlerReponse.obtainMessage(MSG_CHARGER_MOUVEMENT);
                } catch (PartieException e) {
                    msg = handlerReponse.obtainMessage(MSG_ERREUR, e);
                }catch ( InterruptedException e){
                    msg = handlerReponse.obtainMessage( MSG_ANNULER, e );
                }
                handlerReponse.sendMessage(msg);
            }
        };
        final ScheduledFuture<?> refreshHandler =
                scheduler.scheduleAtFixedRate(rafraichirPartie,5, 5L, TimeUnit.SECONDS);
    }

    public void chargerUtilisateur(){

    }

    public void SetSourcePartie(SourceDeroulementPartie sourceDeroulementPartie){
        this._source = sourceDeroulementPartie;
    }

    public void setPartieEnCours(Partie partie){
        this.partieEnCours = partie;
    }

    public void envoyerMoument(String move){
        final String mouvement = move;
        filEsclave = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = null;
                try{
                    InteracteurJouerPartie.getInstance(_source).envoyerMouvement(3,2,mouvement);
                    msg = handlerReponse.obtainMessage(MSG_CHARGER_MOUVEMENT);
                }catch (PartieException e) {
                    msg = handlerReponse.obtainMessage(MSG_ERREUR, e);
                }
            }
        });
        filEsclave.start();
    }

    public void QuitterVue(){
        vuePartieBrawler.getActivity().finish();
    }

    public void ChangerIMGMouvement(int i){
        vuePartieBrawler.changerIMGMoveSoi(i);
    }

    public void ChangerIMGMouvementADV(){
        int dernierElementList = modèle.getPartieChoisi().getMouvementsPartie().size() - 1;
        String mouvementADV = modèle.getPartieChoisi().getMouvementsPartie().get(dernierElementList).getMouvementAdv();
        vuePartieBrawler.changerIMGMoveADV(mouvementADV);
    }

    public void changerNumTour(){
        int dernierElementList = modèle.getPartieChoisi().getMouvementsPartie().size() - 1;
        vuePartieBrawler.changerNumTour(modèle.getPartieChoisi().getMouvementsPartie().get(dernierElementList).getTour());
    }
}
