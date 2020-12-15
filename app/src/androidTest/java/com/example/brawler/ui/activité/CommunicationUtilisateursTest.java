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
import com.example.brawler.présentation.présenteur.PrésenteurContacts;
import com.example.brawler.présentation.vue.VueContacts;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CommunicationUtilisateursTest {
    @Rule
    public ActivityScenarioRule<CommunicationUtilisateurs> rule = new ActivityScenarioRule<>(CommunicationUtilisateurs.class);

    @Test
    public void testPresenceFragment(){
        ActivityScenario<CommunicationUtilisateurs> scenario = rule.getScenario().launch(CommunicationUtilisateurs.class);

        scenario.onActivity(activity -> {
           Fragment f = activity.getSupportFragmentManager().findFragmentById(R.id.layoutContacts);

           assertNotNull(f);
           assertTrue(f instanceof VueContacts);
        });
    }

    @Test
    public void testChargerContacts(){
        ActivityScenario<CommunicationUtilisateurs> scenario = rule.getScenario().launch(CommunicationUtilisateurs.class);

        PrésenteurContacts mockPresenteur = mock(PrésenteurContacts.class);
        scenario.onActivity(activity -> ((VueContacts)activity.getSupportFragmentManager()
                .findFragmentById(R.id.layoutContacts)).setPresenteur(mockPresenteur));

        scenario.moveToState(Lifecycle.State.STARTED);

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.waitForIdle(new Runnable() {
            @Override
            public void run() {
                verify(mockPresenteur).chargerListeContacts();
            }
        });
    }
    @Test
    public void testResumed(){
        ActivityScenario<CommunicationUtilisateurs> scenario = rule.getScenario().launch(CommunicationUtilisateurs.class);

        PrésenteurContacts mockPresenteur = mock(PrésenteurContacts.class);
        scenario.onActivity(activity -> ((VueContacts)activity.getSupportFragmentManager()
                .findFragmentById(R.id.layoutContacts)).setPresenteur(mockPresenteur));

        scenario.moveToState(Lifecycle.State.RESUMED);

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.waitForIdle(new Runnable() {
            @Override
            public void run() {
                verify(mockPresenteur).chargerListeContacts();
            }
        });
    }

    @Test
    public void testClickContact(){
        ActivityScenario<CommunicationUtilisateurs> scenario = rule.getScenario().launch(CommunicationUtilisateurs.class);

        PrésenteurContacts mockPresenteur = mock(PrésenteurContacts.class);
        scenario.onActivity(activity -> ((VueContacts)activity.getSupportFragmentManager()
                .findFragmentById(R.id.layoutContacts)).setPresenteur(mockPresenteur));

        onView(withId(R.id.listeContacts)).perform(click());
        onView(withId(R.layout.fragment_voir_contact)).check(doesNotExist());
    }
}