package com.example.brawler.présentation.présenteur;


import android.app.Instrumentation;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.brawler.DAO.SourcePartiesApi;
import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Partie;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.domaine.intéracteur.SourceParties;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.vue.VueDemandeDePartie;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PrésenteurDemandeDePartieTest {

    @Test
    public void testDémmarer(){
        final List<Partie> parties = new ArrayList<Partie>();
        final Partie partie = new Partie();

        partie.setAdversaire(new Utilisateur(0, "Jaques", Niveau.DÉBUTANT, "Montréal", "gmail@gmail.com", "cool guy"));
        partie.setIdPartie(0);
        parties.add(partie);

        final VueDemandeDePartie mockVue = mock(VueDemandeDePartie.class);
        final Modèle mockModèle = mock(Modèle.class);
        final SourceParties mockSource = mock(SourceParties.class);

        when(mockModèle.getParties()).thenReturn(parties);

        try{
            when(mockSource.getDemandeParties()).thenReturn(parties);
        } catch (SourcePartiesApi.SourcePartieApiException e) {
            fail(e.getMessage());
        }

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();

        instrumentation.runOnMainSync(
                new Runnable(){
                    public void run(){
                        final PrésenteurDemandeDePartie présenteur = new PrésenteurDemandeDePartie( mockVue, mockModèle );
                        présenteur.setSourceParties( mockSource );
                        présenteur.démarer();
                    }
                });

        instrumentation.waitForIdle( new Runnable() {
            public void run() {
                verify(mockModèle).setParties(parties);
                verify(mockVue).rafraichirVue();
            }
        });

    }

    @Test
    public void testAccepterDemande(){
        final List<Partie> parties = new ArrayList<Partie>();
        final Partie partie = new Partie();

        partie.setAdversaire(new Utilisateur(0, "Jaques", Niveau.DÉBUTANT, "Montréal", "gmail@gmail.com", "cool guy"));
        partie.setIdPartie(0);
        parties.add(partie);

        final VueDemandeDePartie mockVue = mock(VueDemandeDePartie.class);
        final Modèle mockModèle = mock(Modèle.class);
        final SourceParties mockSource = mock(SourceParties.class);

        when(mockModèle.getParties()).thenReturn(parties);

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();

        instrumentation.runOnMainSync(
                new Runnable(){
                    public void run(){
                        final PrésenteurDemandeDePartie présenteur = new PrésenteurDemandeDePartie( mockVue, mockModèle );
                        présenteur.setSourceParties( mockSource );
                        présenteur.accepeterDemande(0);
                    }
                });

        instrumentation.waitForIdle( new Runnable() {
            public void run() {
                verify(mockModèle).getParties().remove(partie);
                verify(mockVue).rafraichirVue();
            }
        });

    }

    @Test
    public void testRefuserDemande(){
        final List<Partie> parties = new ArrayList<Partie>();
        final Partie partie = new Partie();

        partie.setAdversaire(new Utilisateur(0, "Jaques", Niveau.DÉBUTANT, "Montréal", "gmail@gmail.com", "cool guy"));
        partie.setIdPartie(0);
        parties.add(partie);

        final VueDemandeDePartie mockVue = mock(VueDemandeDePartie.class);
        final Modèle mockModèle = mock(Modèle.class);
        final SourceParties mockSource = mock(SourceParties.class);

        when(mockModèle.getParties()).thenReturn(parties);

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();

        instrumentation.runOnMainSync(
                new Runnable(){
                    public void run(){
                        final PrésenteurDemandeDePartie présenteur = new PrésenteurDemandeDePartie( mockVue, mockModèle );
                        présenteur.setSourceParties( mockSource );
                        présenteur.accepeterDemande(0);
                    }
                });

        instrumentation.waitForIdle( new Runnable() {
            public void run() {
                verify(mockModèle).getParties().remove(partie);
                verify(mockVue).rafraichirVue();
            }
        });

    }

    /**
     * terste de cahnger la recherche pour les partie en cour
     */
    @Test
    public void testchangerRecherche(){
        final List<Partie> parties = new ArrayList<Partie>();
        final Partie partie = new Partie();

        partie.setAdversaire(new Utilisateur(0, "Jaques", Niveau.DÉBUTANT, "Montréal", "gmail@gmail.com", "cool guy"));
        partie.setIdPartie(0);
        parties.add(partie);

        final VueDemandeDePartie mockVue = mock(VueDemandeDePartie.class);
        final Modèle mockModèle = mock(Modèle.class);
        final SourceParties mockSource = mock(SourceParties.class);

        when(mockModèle.getParties()).thenReturn(parties);

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();

        instrumentation.runOnMainSync(
                new Runnable(){
                    public void run(){
                        final PrésenteurDemandeDePartie présenteur = new PrésenteurDemandeDePartie( mockVue, mockModèle );
                        présenteur.setSourceParties( mockSource );
                        présenteur.changerAffichage(false);
                    }
                });

        instrumentation.waitForIdle( new Runnable() {
            public void run() {
                verify(mockModèle).getParties().removeAll(mockModèle.getParties());
                verify(mockVue).rafraichirVue();
            }
        });

    }
}