package com.example.brawler.ui.activité;


import android.app.Instrumentation;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.brawler.R;
import com.example.brawler.présentation.présenteur.PrésenteurProfil;
import com.example.brawler.présentation.vue.VueProfil;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ConsulterProfilActivitéTest {

    @Rule
    public ActivityScenarioRule<ConsulterProfilActivité> rule  = new ActivityScenarioRule<>(ConsulterProfilActivité.class);

    @Test
    public void testPresenceFragment(){

        ActivityScenario<ConsulterProfilActivité> scenario = rule.getScenario().launch(ConsulterProfilActivité.class);

        scenario.onActivity(activity -> {
           Fragment f = activity.getSupportFragmentManager().findFragmentById(R.id.layoutPrincipal);

           assertNotNull(f);
           assertTrue(f instanceof VueProfil);
        });
    }

    @Test
    public void testChargerUtilisateur(){
        ActivityScenario<ConsulterProfilActivité> scenario = rule.getScenario().launch(ConsulterProfilActivité.class);

        PrésenteurProfil mockPresenteur = mock(PrésenteurProfil.class);
        scenario.onActivity(activity -> ((VueProfil)activity.getSupportFragmentManager().findFragmentById(R.id.layoutPrincipal)).setPresenteur(mockPresenteur));

        scenario.moveToState(Lifecycle.State.STARTED);

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.waitForIdle(new Runnable() {
            @Override
            public void run() {
                verify(mockPresenteur).chargerUtilisateur();
            }
        });
    }

    @Test
    public void testResumed(){
        ActivityScenario<ConsulterProfilActivité> scenario = rule.getScenario().launch(ConsulterProfilActivité.class);

        PrésenteurProfil mockPresenteur = mock(PrésenteurProfil.class);
        scenario.onActivity(activity -> ((VueProfil)activity.getSupportFragmentManager().findFragmentById(R.id.layoutPrincipal)).setPresenteur(mockPresenteur));

        scenario.moveToState(Lifecycle.State.RESUMED);

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.waitForIdle(new Runnable() {
            @Override
            public void run() {
                verify(mockPresenteur).chargerUtilisateur();
            }
        });
    }

    @Test
    public void testClickBtnModifer(){
       onView(withId(R.id.aller_modif_profil)).perform(click());
       onView(withId(R.layout.fragment_profil_view)).check(doesNotExist());
    }

    @Test
    public void testClickLayoutProfil(){
        onView(withId(R.id.Infos_version_court)).perform(click());
        onView(withId(R.id.expandable_view)).check(matches(isDisplayed()));
    }
    
    //TODO Enlever le test d'en commentaire seulment pour tester le log off car ca elle peut pas se reconnecter automatiquement!
    /**
    @Test
    public void testClickBtnDeconnecter(){
        onView(withId(R.id.Btn_Deconect)).perform(click());
        onView(withId(R.layout.activity_profil)).check(doesNotExist());
    }
    */
}