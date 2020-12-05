package com.example.brawler;

import com.example.brawler.DAO.SourcePartiesApi;
import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Partie;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.domaine.intéracteur.SourceParties;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.présenteur.PrésenteurDemandeDePartie;
import com.example.brawler.présentation.vue.VueDemandeDePartie;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import android.app.Instrumentation;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TestPrésenteurDemandePartie {

    @Test
    public void testDémmarer(){
        final List<Partie> parties = new ArrayList<Partie>();
        final Partie partie = new Partie();

        partie.setAdversaire(new Utilisateur(0, "Jaques", Niveau.DÉBUTANT, "Montréal", "gmail@gmail.com", "cool guy"));
        partie.setId(0);
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
        partie.setId(0);
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
        partie.setId(0);
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
}
