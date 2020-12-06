package com.example.brawler.ui.activité;

import android.app.Instrumentation;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
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
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
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

}
