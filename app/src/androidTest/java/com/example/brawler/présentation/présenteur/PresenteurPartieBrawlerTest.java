package com.example.brawler.présentation.présenteur;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.test.annotation.UiThreadTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.brawler.domaine.entité.Message;
import com.example.brawler.domaine.entité.Mouvement;
import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Partie;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.domaine.intéracteur.PartieException;
import com.example.brawler.domaine.intéracteur.SourceDeroulementPartie;
import com.example.brawler.domaine.intéracteur.SourceMessage;
import com.example.brawler.domaine.intéracteur.SourceUtilisateurs;
import com.example.brawler.domaine.intéracteur.UtilisateursException;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.vue.VueContacts;
import com.example.brawler.présentation.vue.VuePartieBrawler;
import com.example.brawler.ui.activité.ConsulterMessageActivité;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class PresenteurPartieBrawlerTest {

    @Test
    /**
     * Test pour vérifier que le présenteur peut obtenir la partie de la source de données
     * en utilisant le ID de la partie
     */
    public void testChargerPartie(){
        final VuePartieBrawler mockVue = mock(VuePartieBrawler.class);
        final Modèle mockModele = mock(Modèle.class);
        final SourceDeroulementPartie mockSource = mock(SourceDeroulementPartie.class);
        List<Mouvement> mouvements = new ArrayList<>();
        mouvements.add(new Mouvement(1,"Roche","Papier"));
        mouvements.add(new Mouvement(2,"Roche","Ciseau"));
        final Partie partie = new Partie(0,true,1,false,mouvements);

        try{
            when(mockSource.getPartieParID(0)).thenReturn(partie);
        } catch (PartieException e) {
            fail(e.getMessage());
        }

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                final PresenteurPartieBrawler pres = new PresenteurPartieBrawler(mockVue,mockModele);
                pres.chargerPartie(0);
            }
        });
        instrumentation.waitForIdle(new Runnable() {
            @Override
            public void run() {
                verify(mockModele).setPartieChoisi(partie);
            }
        });
    }

    @Test
    /**
     * Test pour charger le mouvement le plus récent qui est reçu de la source de données,
     * chaque fois qu'un nouvelle item s'ajoute à la liste de mouvements
     */
    public void testGetDernierMoveAdv(){
        final VuePartieBrawler mockVue = mock(VuePartieBrawler.class);
        final Modèle mockModele = mock(Modèle.class);
        final SourceDeroulementPartie mockSource = mock(SourceDeroulementPartie.class);
        List<Mouvement> mouvements = new ArrayList<>();
        mouvements.add(new Mouvement(1,"Roche","Papier"));
        mouvements.add(new Mouvement(2,"Roche","Ciseau"));
        final Partie partie = new Partie(0,true,1,false,mouvements);

        try{
            when(mockSource.getPartieParID(0)).thenReturn(partie);
        } catch (PartieException e) {
            fail(e.getMessage());
        }

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                final PresenteurPartieBrawler pres = new PresenteurPartieBrawler(mockVue,mockModele);
                pres.changerNumTour();
            }
        });
        instrumentation.waitForIdle(new Runnable() {
            @Override
            public void run() {
                verify(mockVue).changerStatusBouttonSend(true);
            }
        });
    }

    @Test
    /**
     * Test pour vérifier que les mouvements sont envoyées à la source de données
     */
    public void testEnvoyerMove(){
        final VuePartieBrawler mockVue = mock(VuePartieBrawler.class);
        final Modèle mockModele = mock(Modèle.class);
        final SourceDeroulementPartie mockSource = mock(SourceDeroulementPartie.class);
        List<Mouvement> mouvements = new ArrayList<>();
        mouvements.add(new Mouvement(1,"Roche","Ciseau"));
        final Partie partie = new Partie(0,true,1,false,mouvements);
        mockModele.setPartieChoisi(partie);
        final Mouvement mockMove = new Mouvement(2,"","Papier");

        when(mockModele.getPartieChoisi()).thenReturn(partie);

        try{
            when(mockSource.getPartieParID(0)).thenReturn(partie);
        } catch (PartieException e) {
            fail(e.getMessage());
        }

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                final PresenteurPartieBrawler pres = new PresenteurPartieBrawler(mockVue,mockModele);
                pres.envoyerMouvement("Papier");
            }
        });
        instrumentation.waitForIdle(new Runnable() {
            @Override
            public void run() {
                verify(mockModele).getPartieChoisi().getMouvementsPartie().equals(2);
            }
        });
    }

}