package com.example.brawler.présentation.vue.adapter;

import android.app.Instrumentation;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.brawler.DAO.SourcePartiesApi;
import com.example.brawler.R;
import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Partie;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.domaine.intéracteur.SourceParties;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.ui.activité.ConsulterDemandePartieActivité;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static java.lang.Thread.sleep;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class DemandeDePartieAdapterTest {

    @Rule
    public ActivityScenarioRule<ConsulterDemandePartieActivité> rule  = new  ActivityScenarioRule<>(ConsulterDemandePartieActivité.class);

    @Test
    public void vérifieRVPrésent(){
        onView(withId(R.id.listPartie)).check(matches(isDisplayed()));
    }


    @Test
    public void testCliqueBoutonAccepeter(){
        final List<Partie> parties = new ArrayList<Partie>();
        final Partie partie = new Partie();
        partie.setAdversaire(new Utilisateur(-1, "Jaques", Niveau.DÉBUTANT, "Montréal", "gmail@gmail.com", "cool guy"));

        partie.setId(0);
        parties.add(partie);
        final Modèle mockModèle = mock(Modèle.class);
        final SourceParties mockSource = mock(SourceParties.class);

        when(mockModèle.getParties()).thenReturn(parties);

        try{
            when(mockSource.getDemandeParties()).thenReturn(parties);
        } catch (SourcePartiesApi.SourcePartieApiException e) {
            fail(e.getMessage());
        }

        ActivityScenario<ConsulterDemandePartieActivité> scenario = rule.getScenario().launch(ConsulterDemandePartieActivité.class);
        scenario.onActivity( activité -> activité.getPrésenteur().setSourceParties(mockSource));
        scenario.onActivity( activité -> activité.getPrésenteur().setModèle(mockModèle));
        scenario.onActivity( activité -> activité.getPrésenteur().démarer());

        onView(withId(R.id.btn_accepter))
                .perform(click());


        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.waitForIdle( new Runnable() {
            public void run() {
                verify(scenario.onActivity( activité -> activité.getPrésenteur().accepeterDemande(-1)));
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
    public void testCliqueBoutonRefuser(){
        final List<Partie> parties = new ArrayList<Partie>();
        final Partie partie = new Partie();
        partie.setAdversaire(new Utilisateur(-1, "Jaques", Niveau.DÉBUTANT, "Montréal", "gmail@gmail.com", "cool guy"));

        partie.setId(0);
        parties.add(partie);
        final Modèle mockModèle = mock(Modèle.class);
        final SourceParties mockSource = mock(SourceParties.class);

        when(mockModèle.getParties()).thenReturn(parties);

        try{
            when(mockSource.getDemandeParties()).thenReturn(parties);
        } catch (SourcePartiesApi.SourcePartieApiException e) {
            fail(e.getMessage());
        }

        ActivityScenario<ConsulterDemandePartieActivité> scenario = rule.getScenario().launch(ConsulterDemandePartieActivité.class);
        scenario.onActivity( activité -> activité.getPrésenteur().setSourceParties(mockSource));
        scenario.onActivity( activité -> activité.getPrésenteur().setModèle(mockModèle));
        scenario.onActivity( activité -> activité.getPrésenteur().démarer());

        onView(withId(R.id.btn_passer))
                .perform(click());


        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.waitForIdle( new Runnable() {
            public void run() {
                verify(scenario.onActivity( activité -> activité.getPrésenteur().refuserDemande(-1)));
            }
        });

    }



}