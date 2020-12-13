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
    /**
     * Paramètres
     */
    VuePartieBrawler vuePartieBrawler;
    SourceDeroulementPartie _source;
    Modèle modèle;
    Partie partieEnCours;

    //Objets pour les threads
    private final Handler handlerReponse;
    private Thread filEsclave = null;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    //Codes de résultat
    private final int MSG_CHARGER_MOUVEMENT = 0;
    private final int MSG_CHARGER_RESULTAT = 3;
    private final int MSG_ERREUR = 1;
    private final int MSG_ANNULER = 2;

    /**
     * Creer un objet de type présenteur
     * @param vue la vue à utiliser
     * @param leModele le modèle de l'application
     */
    public PresenteurPartieBrawler(final VuePartieBrawler vue, Modèle leModele){
        this.vuePartieBrawler = vue;
        this.modèle = leModele;

        this.handlerReponse = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                filEsclave = null;
                /**
                 * Si on obtient une partie pas finie on charge le numero du tour
                 * le dernier mouvement de l'adversaire
                 */
                if (msg.what == MSG_CHARGER_MOUVEMENT){
                    setPartieEnCours(modèle.getPartieChoisi());

                    /**
                     * Changer le numero du tour et changer l'image du dernier mouvement
                     */
                    changerNumTour();
                    ChangerIMGMouvementADV();
                    ChangerDernierIMGSoi();
                }
                /**
                 * Si on obtient une partie qui est déjà finie on affiche le résultat selon
                 * si on a gagné ou perdu
                 */
                else if(msg.what == MSG_CHARGER_RESULTAT){
                    vuePartieBrawler.changerStatusBouttonSend(InteracteurJouerPartie
                            .getInstance(_source).getBoolTourChange());
                    if(modèle.getPartieChoisi().isGagne()){
                        vuePartieBrawler.changerMSGResultat("Victoire");
                    }else if(!modèle.getPartieChoisi().isGagne()){
                        vuePartieBrawler.changerMSGResultat("Défaite");
                    }
                }
                else if (msg.what == MSG_ERREUR){
                    Log.e("Brawler", "Erreur d'accès à l'API", (Throwable) msg.obj);
                }
            }
        };
    }

    /**
     * Fonction qui lance le fil esclave pour charger la partie
     * @param idPartie le id de la partie qu'on veut
     */
    public void chargerPartie(final int idPartie){
        filEsclave = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = null;
                try{
                    Thread.sleep(0);
                    modèle.setPartieChoisi(InteracteurJouerPartie
                            .getInstance(_source).getPartieParID(idPartie));
                    if(modèle.getPartieChoisi().isEnCours()) {
                        msg = handlerReponse.obtainMessage(MSG_CHARGER_MOUVEMENT);
                    }else if(!modèle.getPartieChoisi().isEnCours()){
                        msg = handlerReponse.obtainMessage(MSG_CHARGER_RESULTAT);
                    }
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

    /**
     * Fonction en chargee de lancer un fil esclave à chaque quantité de temps
     * Le fil esclave sera lancé tant qu'on est dans cette activité et obtiens les résultats
     * plus récent de la partie
     */
    public void scheduleRafraichirPartie(){
        final Runnable rafraichirPartie = new Runnable() {
            @Override
            public void run() {
                Message msg = null;
                try{
                    Thread.sleep(0);
                    modèle.setPartieChoisi(InteracteurJouerPartie.getInstance(_source)
                            .getPartieParID(modèle.getPartieChoisi().getIdPartie()));
                    if(modèle.getPartieChoisi().isEnCours()) {
                        msg = handlerReponse.obtainMessage(MSG_CHARGER_MOUVEMENT);
                    }else if(!modèle.getPartieChoisi().isEnCours()){
                        msg = handlerReponse.obtainMessage(MSG_CHARGER_RESULTAT);
                    }
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

    /**
     * Set la source de parties
     * @param sourceDeroulementPartie Source de la partie voulu
     */
    public void SetSourcePartie(SourceDeroulementPartie sourceDeroulementPartie){
        this._source = sourceDeroulementPartie;
    }

    /**
     * Set la partie qui se déroule
     * @param partie
     */
    public void setPartieEnCours(Partie partie){
        this.partieEnCours = partie;
    }

    /**
     * Le thread en charge d'enovyer le mouvement qu'on a joué
     * @param move un string qui représente le mouvement choisi
     */
    public void envoyerMouvement(String move){
        //Le mouvement qu'on va jouer
        final String mouvement = move;
        //La partie actuelle dans le modèle
        final Partie partie= modèle.getPartieChoisi();
        filEsclave = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = null;
                try{
                    InteracteurJouerPartie.getInstance(_source)
                            .envoyerMouvement(partie.getIdPartie(),partie.getIdAdv(),mouvement);
                    msg = handlerReponse.obtainMessage(MSG_CHARGER_MOUVEMENT);
                }catch (PartieException | Exception e) {
                    msg = handlerReponse.obtainMessage(MSG_ERREUR, e);
                }
            }
        });
        filEsclave.start();
    }

    /**
     * Quitter la vue de la partie
     */
    public void QuitterVue(){
        vuePartieBrawler.getActivity().finish();
    }

    /**
     * Changer l'image de son mouvement selon celui choisi
     * @param i l'index de l'image qu'on veut
     */
    public void ChangerIMGMouvement(int i){
        vuePartieBrawler.changerIMGMoveSoi(i);
    }

    /**
     * Change l'image du mouvement de l'adversaire selon le mouvement du tour actuel
     */
    public void ChangerIMGMouvementADV(){
        int dernierElementList = modèle.getPartieChoisi().getMouvementsPartie().size() - 1;
        if(dernierElementList > -1) {
            String mouvementADV = modèle.getPartieChoisi().getMouvementsPartie()
                    .get(dernierElementList).getMouvementAdv();
            vuePartieBrawler.changerIMGMoveADV(mouvementADV);
        }
    }

    /**
     * Change l'image du mouvement de l'adversaire selon le mouvement du tour actuel
     */
    public void ChangerDernierIMGSoi(){
        int dernierElementList = modèle.getPartieChoisi().getMouvementsPartie().size() - 1;
        if(dernierElementList > -1) {
            String dernierMoveSoi = modèle.getPartieChoisi().getMouvementsPartie()
                    .get(dernierElementList).getMouvementJoueur();
            vuePartieBrawler.changerDernierMoveSoi(dernierMoveSoi);
        }
    }

    /**
     * Change le numéro du tour actuel selon le tour plus récent dans la liste de mouvements
     */
    public void changerNumTour(){
        int tour =InteracteurJouerPartie.getInstance(_source).getLeTour();
        if(tour > 0) {
            vuePartieBrawler.changerStatusBouttonSend(InteracteurJouerPartie
                    .getInstance(_source).getBoolTourChange());
        }
        vuePartieBrawler.changerNumTour(tour);
    }
}
