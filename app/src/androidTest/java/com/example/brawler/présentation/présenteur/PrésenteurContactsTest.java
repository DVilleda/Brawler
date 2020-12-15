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
import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.domaine.intéracteur.SourceMessage;
import com.example.brawler.domaine.intéracteur.SourceUtilisateurs;
import com.example.brawler.domaine.intéracteur.UtilisateursException;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.vue.VueContacts;
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
public class PrésenteurContactsTest {

    @Test
    /**
     * Test pour vérifier que le présenteur peut obtenir une liste d'utilisateur de la source de
     * données
     */
    public void testChargerListeContacts(){
        final ConsulterMessageActivité mockActivity = mock(ConsulterMessageActivité.class);
        final VueContacts mockVue = mock(VueContacts.class);
        final Modèle mockModele = mock(Modèle.class);
        final SourceUtilisateurs mockSourceContacts = mock(SourceUtilisateurs.class);

        final List<Utilisateur> utilisateurs = new ArrayList<>();
        utilisateurs.add(new Utilisateur(0,"Dan", Niveau.LÉGENDAIRE,"MTL","email@email.com","TEST"));
        utilisateurs.add(new Utilisateur(1,"Danny", Niveau.LÉGENDAIRE,"MTL","email@email.com","TEST"));

        try{
            when(mockSourceContacts.getContact()).thenReturn(utilisateurs);
        } catch (UtilisateursException e) {
            fail(e.getMessage());
        }
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                final PrésenteurContacts _pres = new PrésenteurContacts(mockVue,mockModele,mockActivity);
                _pres.chargerListeContacts();
            }
        });
        instrumentation.waitForIdle(new Runnable() {
            @Override
            public void run() {
                verify(mockModele).setListeUtilisateurs(utilisateurs);
                verify(mockVue).afficherContacts();
            }
        });
    }

    @UiThreadTest
    /**
     * Test pour vérifier que le présenteur peut initer le chargement de conversation et enlever
     * la vue actuelle.
     */
    public void testChargerConversationUtilisateur(){
        final ConsulterMessageActivité mockActivity = mock(ConsulterMessageActivité.class);
        final Context context = mock(Context.class);
        final VueContacts mockVue = mock(VueContacts.class);
        final Modèle mockModele = mock(Modèle.class);
        final Utilisateur user = new Utilisateur(0,"Dan", Niveau.LÉGENDAIRE,"MTL","email@email.com","TEST");

        final Intent intent = new Intent(context,ConsulterMessageActivité.class);
        final Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        final Instrumentation.ActivityMonitor monitor = new Instrumentation.ActivityMonitor();
        instrumentation.callActivityOnNewIntent(mockActivity,intent);
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                final PrésenteurContacts presenteur=
                        new PrésenteurContacts(mockVue,mockModele,mockActivity);
                presenteur.chargerConversationUtilisateur(user.getId());
            }
        });

        instrumentation.waitForIdle(new Runnable() {
            @Override
            public void run() {
                assertEquals(mockActivity,monitor.getLastActivity());
            }
        });
    }

}