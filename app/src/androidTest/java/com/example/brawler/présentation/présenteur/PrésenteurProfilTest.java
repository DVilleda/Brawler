package com.example.brawler.présentation.présenteur;

import android.app.Instrumentation;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.domaine.intéracteur.SourceUtilisateur;
import com.example.brawler.domaine.intéracteur.UtilisateursException;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.vue.VueProfil;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



@RunWith(AndroidJUnit4.class)
public class PrésenteurProfilTest {
    @Test
    public void testchargerUtilisateur(){
        final VueProfil mockVue = mock(VueProfil.class);
        final Modèle mockModele = mock(Modèle.class);
        final SourceUtilisateur mockSource = mock(SourceUtilisateur.class);
        final Utilisateur utilisateur = new Utilisateur(0,"Dan", Niveau.LÉGENDAIRE,"MTL","email@email.com","TEST");

        when(mockModele.getUtilisateurActuel()).thenReturn(utilisateur);

        try{
            when(mockSource.getUtilisateur()).thenReturn(utilisateur);
        } catch (UtilisateursException e){
            fail(e.getMessage());
        }

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();

        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                final PrésenteurProfil presenteur = new PrésenteurProfil(mockVue,mockModele);
                presenteur.setSourceUtilisateur(mockSource);
                presenteur.chargerUtilisateur();
            }
        });

        instrumentation.waitForIdle(new Runnable() {
            @Override
            public void run() {
                verify(mockModele).setUtilisateur(utilisateur);
                verify(mockVue).afficherUtilisateur(utilisateur);
            }
        });
    }

    @Test
    public void testModifierUtilisateur(){
        final VueProfil mockVue = mock(VueProfil.class);
        final Modèle mockModele = mock(Modèle.class);
        final SourceUtilisateur mockSource = mock(SourceUtilisateur.class);
        final Utilisateur utilisateur = new Utilisateur(0,"Dan", Niveau.LÉGENDAIRE,"MTL","email@email.com","TEST");
        mockModele.setUtilisateur(utilisateur);

        when(mockModele.getUtilisateurActuel()).thenReturn(utilisateur);

        try{
            when(mockSource.getUtilisateur()).thenReturn(utilisateur);
        } catch (UtilisateursException e){
            fail(e.getMessage());
        }

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                final PrésenteurProfil presenteur = new PrésenteurProfil(mockVue,mockModele);
                presenteur.getUtilisateur();
            }
        });

        instrumentation.waitForIdle(new Runnable() {
            @Override
            public void run() {
                verify(mockModele).setUtilisateur(utilisateur);
                verify(mockVue).afficherUtilisateur(utilisateur);
            }
        });
    }
}