package com.example.brawler.ui.activité;

import android.app.Instrumentation;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.brawler.DAO.SourcePartiesApi;
import com.example.brawler.R;
import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Partie;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.domaine.intéracteur.SourceParties;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.présenteur.PrésenteurDemandeDePartie;
import com.example.brawler.présentation.vue.VueDemandeDePartie;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;
import static org.hamcrest.core.IsAnything.anything;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ConsulterDemandePartieActivitéTest {

    @Rule
    //Lance l'activité avant chaque test et la referme après
    public ActivityScenarioRule<ConsulterDemandePartieActivité> rule  = new  ActivityScenarioRule<>(ConsulterDemandePartieActivité.class);


    @Test
    public void testPrésenceDuFragment() {

        ActivityScenario<ConsulterDemandePartieActivité> scenario = rule.getScenario().launch(ConsulterDemandePartieActivité.class);

        scenario.onActivity( activité -> {
            Fragment f = activité.getSupportFragmentManager().findFragmentById(R.id.layoutPrincipal);

            assertNotNull( f );
            assertTrue( f instanceof VueDemandeDePartie );
        });

    }


    /**
     * test démmarer l'acitivité
     */
    @Test
    public void testDémarer() {
        // Attempt to scroll to an item that contains the special text.
        ActivityScenario<ConsulterDemandePartieActivité> scenario = rule.getScenario().launch(ConsulterDemandePartieActivité.class);

        //On crée un mock de présenteur qu'on passe au fragment
        PrésenteurDemandeDePartie mockPrésenteur = mock(PrésenteurDemandeDePartie.class);
        scenario.onActivity( activité -> ((VueDemandeDePartie)activité.getSupportFragmentManager().findFragmentById(R.id.layoutPrincipal)).setPresenteur( mockPrésenteur ));

        //Grâce au scénario, on peut changer l'état de l'activité et faire comme si elle était mise en pause
        //puis ramenée à l'avant-plan
        scenario.moveToState(Lifecycle.State.STARTED);

        //Grâce à l'instrumentation, on peut attendre que l'activité n'ait plus rien à faire pour à ce moment
        // vérifier que la méthode citationSuivante du  mockPrésenteur a bien été appelée
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.waitForIdle( new Runnable() {
            public void run() {
                verify(mockPrésenteur).démarer();
            }
        });
    }

    /**
     * test si quand on resume l'app tout s'ouvre comme prévue
     */
    @Test
    public void testResumed() {
        // Attempt to scroll to an item that contains the special text.
        ActivityScenario<ConsulterDemandePartieActivité> scenario = rule.getScenario().launch(ConsulterDemandePartieActivité.class);

        //On crée un mock de présenteur qu'on passe au fragment
        PrésenteurDemandeDePartie mockPrésenteur = mock(PrésenteurDemandeDePartie.class);
        scenario.onActivity( activité -> ((VueDemandeDePartie)activité.getSupportFragmentManager().findFragmentById(R.id.layoutPrincipal)).setPresenteur( mockPrésenteur ));

        //Grâce au scénario, on peut changer l'état de l'activité et faire comme si elle était mise en pause
        //puis ramenée à l'avant-plan
        scenario.moveToState(Lifecycle.State.RESUMED);

        //Grâce à l'instrumentation, on peut attendre que l'activité n'ait plus rien à faire pour à ce moment
        // vérifier que la méthode citationSuivante du  mockPrésenteur a bien été appelée
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.waitForIdle( new Runnable() {
            public void run() {
                verify(mockPrésenteur).démarer();
            }
        });
    }

    @Test
    public void vérifieRVPrésent(){
        onView(withId(R.id.listPartie)).check(ViewAssertions.matches(isDisplayed()));
    }


    /**
     * test sur cliquer le bouton accepter d'une partie
     */
    @Test
    public void testCliqueBoutonAccepeter(){
        final List<Partie> parties = new ArrayList<Partie>();
        final Partie partie = new Partie();
        partie.setAdversaire(new Utilisateur(-1, "Jaques", Niveau.DÉBUTANT, "Montréal", "gmail@gmail.com", "cool guy"));

        partie.setId(0);
        parties.add(partie);
        final Modèle mockModèle = mock(Modèle.class);

        when(mockModèle.getParties()).thenReturn(parties);

        ActivityScenario<ConsulterDemandePartieActivité> scenario = rule.getScenario().launch(ConsulterDemandePartieActivité.class);
        scenario.onActivity( activité -> activité.getPrésenteur().setModèle(mockModèle));
        scenario.onActivity( activité -> activité.getPrésenteur().démarer());

        //laisser le temps de charger les donnée
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.btn_accepter))
                .perform(click());


        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.waitForIdle( new Runnable() {
            public void run() {
                verify(scenario.onActivity( activité -> activité.getPrésenteur().accepeterDemande(-1)));
            }
        });

    }

    /**
     * test sur cliquer le bouton refuser d'une partie
     */
    @Test
    public void testCliqueBoutonRefuser(){
        final List<Partie> parties = new ArrayList<Partie>();
        final Partie partie = new Partie();
        partie.setAdversaire(new Utilisateur(-1, "Jaques", Niveau.DÉBUTANT, "Montréal", "gmail@gmail.com", "cool guy"));

        partie.setId(0);
        parties.add(partie);
        final Modèle mockModèle = mock(Modèle.class);

        when(mockModèle.getParties()).thenReturn(parties);

        ActivityScenario<ConsulterDemandePartieActivité> scenario = rule.getScenario().launch(ConsulterDemandePartieActivité.class);
        scenario.onActivity( activité -> activité.getPrésenteur().setModèle(mockModèle));
        scenario.onActivity( activité -> activité.getPrésenteur().démarer());

        //laisser le temps de charger les donnée
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.btn_passer))
                .perform(click());


        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.waitForIdle( new Runnable() {
            public void run() {
                verify(scenario.onActivity( activité -> activité.getPrésenteur().refuserDemande(-1)));
            }
        });

    }

    /**
     * test sur cliquer le bouton refuser d'une partie
     */
    @Test
    public void testCliqueAccederPartie(){
        final List<Partie> parties = new ArrayList<Partie>();
        final Partie partie = new Partie();
        partie.setAdversaire(new Utilisateur(-1, "Jaques", Niveau.DÉBUTANT, "Montréal", "gmail@gmail.com", "cool guy"));

        partie.setId(0);
        parties.add(partie);
        final Modèle mockModèle = mock(Modèle.class);
        final SourceParties mockSource = mock(SourceParties.class);

        when(mockModèle.getParties()).thenReturn(parties);

        try{
            when(mockSource.getPartieEnCour()).thenReturn(parties);
        } catch (SourcePartiesApi.SourcePartieApiException e) {
            fail(e.getMessage());
        }

        ActivityScenario<ConsulterDemandePartieActivité> scenario = rule.getScenario().launch(ConsulterDemandePartieActivité.class);
        scenario.onActivity( activité -> activité.getPrésenteur().changerAffichage(false));
        scenario.onActivity( activité -> activité.getPrésenteur().setSourceParties(mockSource));
        scenario.onActivity( activité -> activité.getPrésenteur().setModèle(mockModèle));
        scenario.onActivity( activité -> activité.getPrésenteur().démarer());
        
        onView(withId(R.id.btn_acceder))
                .perform(click());


        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.waitForIdle( new Runnable() {
            public void run() {
                verify(scenario.onActivity( activité -> activité.getPrésenteur().accederPartie(-1)));
                verify(mockModèle.getParties().get(-1).getId());
            }
        });

    }

    @Test
    public void testScollTopDuRecyclerView() {
        final List<Partie> parties = new ArrayList<Partie>();
        for(int i = 0; i < 10; i++) {
            Partie partie = new Partie();
            partie.setAdversaire(new Utilisateur(-1, "Jaques", Niveau.DÉBUTANT, "Montréal", "gmail@gmail.com", "cool guy"));
            partie.setId(i);
            parties.add(partie);
        }

        final Modèle mockModèle = mock(Modèle.class);
        final SourceParties mockSource = mock(SourceParties.class);

        when(mockModèle.getParties()).thenReturn(parties);

        try {
            when(mockSource.getDemandeParties()).thenReturn(parties);
        } catch (SourcePartiesApi.SourcePartieApiException e) {
            fail(e.getMessage());
        }

        ActivityScenario<ConsulterDemandePartieActivité> scenario = rule.getScenario().launch(ConsulterDemandePartieActivité.class);
        scenario.onActivity(activité -> activité.getPrésenteur().setSourceParties(mockSource));
        scenario.onActivity(activité -> activité.getPrésenteur().setModèle(mockModèle));
        scenario.onActivity(activité -> activité.getPrésenteur().démarer());

        onView(withId(R.id.listPartie))
                .perform(RecyclerViewActions.scrollToPosition(parties.size() - 1));
    }

    @Test
    public void testCliqueBoutonPartieEnCour() {
        final List<Partie> parties = new ArrayList<Partie>();
        final Partie partie = new Partie();
        partie.setAdversaire(new Utilisateur(-1, "Jaques", Niveau.DÉBUTANT, "Montréal", "gmail@gmail.com", "cool guy"));

        partie.setId(0);
        parties.add(partie);
        final Modèle mockModèle = mock(Modèle.class);
        final SourceParties mockSource = mock(SourceParties.class);

        when(mockModèle.getParties()).thenReturn(parties);

        try {
            when(mockSource.getDemandeParties()).thenReturn(parties);
        } catch (SourcePartiesApi.SourcePartieApiException e) {
            fail(e.getMessage());
        }

        ActivityScenario<ConsulterDemandePartieActivité> scenario = rule.getScenario().launch(ConsulterDemandePartieActivité.class);
        scenario.onActivity(activité -> activité.getPrésenteur().setSourceParties(mockSource));
        scenario.onActivity(activité -> activité.getPrésenteur().setModèle(mockModèle));
        scenario.onActivity(activité -> activité.getPrésenteur().démarer());

        onView(withId(R.id.btnPartienEnCour))
                .perform(click());


        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.waitForIdle(new Runnable() {
            public void run() {
                verify(scenario.onActivity(activité -> activité.getPrésenteur().changerAffichage(false)));
            }
        });
    }

    @Test
    public void testCliqueBoutonDemandeDePartie() {
        final List<Partie> parties = new ArrayList<Partie>();
        final Partie partie = new Partie();
        partie.setAdversaire(new Utilisateur(-1, "Jaques", Niveau.DÉBUTANT, "Montréal", "gmail@gmail.com", "cool guy"));

        partie.setId(0);
        parties.add(partie);
        final Modèle mockModèle = mock(Modèle.class);
        final SourceParties mockSource = mock(SourceParties.class);

        when(mockModèle.getParties()).thenReturn(parties);

        try {
            when(mockSource.getDemandeParties()).thenReturn(parties);
        } catch (SourcePartiesApi.SourcePartieApiException e) {
            fail(e.getMessage());
        }

        ActivityScenario<ConsulterDemandePartieActivité> scenario = rule.getScenario().launch(ConsulterDemandePartieActivité.class);
        scenario.onActivity(activité -> activité.getPrésenteur().setSourceParties(mockSource));
        scenario.onActivity(activité -> activité.getPrésenteur().setModèle(mockModèle));
        scenario.onActivity(activité -> activité.getPrésenteur().démarer());

        onView(withId(R.id.btnDemandePartie))
                .perform(click());


        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.waitForIdle(new Runnable() {
            public void run() {
                verify(scenario.onActivity(activité -> activité.getPrésenteur().changerAffichage(false)));
            }
        });
    }
}
