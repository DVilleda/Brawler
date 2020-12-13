package com.example.brawler.ui.activité;

import android.app.Instrumentation;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.brawler.R;
import com.example.brawler.présentation.présenteur.PresenteurPartieBrawler;
import com.example.brawler.présentation.vue.VuePartieBrawler;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
@LargeTest
public class MatchBrawlerTest {

    @Rule
    //Permet de commencer l'activité à chaque test
    public ActivityScenarioRule<MatchBrawler> rule = new ActivityScenarioRule<>(MatchBrawler.class);

    @Test
    /**
     * Test pour vérifier la présence du fragment
     */
    public void testPresenceFragment(){
        ActivityScenario<MatchBrawler> scenario = rule.getScenario().launch(MatchBrawler.class);

        scenario.onActivity(activity -> {
            Fragment f = activity.getSupportFragmentManager().findFragmentById(R.id.layoutBrawler);

            assertNotNull(f);
            assertTrue(f instanceof VuePartieBrawler);
        });
    }

    @Test
    /**
     * Test pour vérifier que l'activité commence et peut utiliser le présenteur pour charger
     * des données
     */
    public void testChargerPartie(){
        ActivityScenario<MatchBrawler> scenario = rule.getScenario().launch(MatchBrawler.class);
        PresenteurPartieBrawler mockPresenteur = mock(PresenteurPartieBrawler.class);
        scenario.onActivity(activity -> ((VuePartieBrawler)activity
                .getSupportFragmentManager().findFragmentById(R.id.layoutBrawler)).setPresenteur(mockPresenteur));

        scenario.moveToState(Lifecycle.State.STARTED);

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.waitForIdle(new Runnable() {
            @Override
            public void run() {
                verify(mockPresenteur).chargerPartie(1);
            }
        });
    }

    @Test
    /**
     * Test pour vérifier que l'activité peut continuer après interruption
     */
    public void testResumed(){
        ActivityScenario<MatchBrawler> scenario = rule.getScenario().launch(MatchBrawler.class);
        PresenteurPartieBrawler mockPresenteur = mock(PresenteurPartieBrawler.class);
        scenario.onActivity(activity -> ((VuePartieBrawler)activity
                .getSupportFragmentManager().findFragmentById(R.id.layoutBrawler)).setPresenteur(mockPresenteur));

        scenario.moveToState(Lifecycle.State.RESUMED);

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.waitForIdle(new Runnable() {
            @Override
            public void run() {
                verify(mockPresenteur).chargerPartie(1);
            }
        });
    }

    @Test
    /**
     * Test pour vérifier les bouttons de main qui change l'image du joueur
     */
    public void testChangerMouvement(){
        VuePartieBrawler mockVue = mock(VuePartieBrawler.class);
        onView(withId(R.id.btn_roche)).perform(click());
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.waitForIdle(new Runnable() {
            @Override
            public void run() {
                verify(mockVue).changerIMGMoveSoi(1);
            }
        });
        onView(withId(R.id.btn_papier)).perform(click());
        instrumentation.waitForIdle(new Runnable() {
            @Override
            public void run() {
                verify(mockVue).changerIMGMoveSoi(2);
            }
        });
        onView(withId(R.id.btn_ciseaux)).perform(click());
        instrumentation.waitForIdle(new Runnable() {
            @Override
            public void run() {
                verify(mockVue).changerIMGMoveSoi(3);
            }
        });
    }

    @Test
    /**
     * Test pour vérifier le changement du numero de tour
     */
    public void testChangerTour(){
        VuePartieBrawler mockVue = mock(VuePartieBrawler.class);
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.waitForIdle(new Runnable() {
            @Override
            public void run() {
                verify(mockVue).changerNumTour(1);
            }
        });
    }

    @Test
    /**
     * Test pour verifier le changement des images selon le resultat du dernier tour
     */
    public void testChangerResultatsDernierTour(){
        VuePartieBrawler mockVue = mock(VuePartieBrawler.class);
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.waitForIdle(new Runnable() {
            @Override
            public void run() {
                verify(mockVue).changerIMGMoveADV("Roche");
                verify(mockVue).changerDernierMoveSoi("Papier");
            }
        });
    }
    @Test
    /**
     * Test pour vérifier l'affichage du message de résultat et changement des layouts
     */
    public void testAfficherMessageResultat(){
        VuePartieBrawler mockVue = mock(VuePartieBrawler.class);
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.waitForIdle(new Runnable() {
            @Override
            public void run() {
                verify(mockVue).changerMSGResultat("Votre Victoire est confirmée :)");
            }
        });
    }

    @Test
    /**
     * Test pour verifier que le click du boutton pour envoyer le mouvement n'est pas actif après
     * un click pour envoyer le mouvement
     */
    public void testClickEnvoyerMove(){
        onView(withId(R.id.btn_roche)).perform(click());
        onView(withId(R.id.btnJouer)).perform(click());
        onView(withId(R.id.btnJouer)).check(matches(not(isEnabled())));
    }
}

