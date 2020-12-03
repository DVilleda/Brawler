package com.example.brawler.ui.activité;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.example.brawler.R;
import com.example.brawler.domaine.intéracteur.SourceParties;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.présenteur.PrésenteurDemandeDePartie;
import com.example.brawler.présentation.vue.VueDemandeDePartie;
import com.example.brawler.présentation.vue.VueRechercheMatch;
import com.example.brawler.ui.activité.Services.ServiceNotificationMessage;
import com.google.android.gms.location.LocationServices;

public class ConsulterDemandePartieActivité extends AppCompatActivity {
    PrésenteurDemandeDePartie présenteur;
    String clé;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ServiceNotificationMessage.démarerJob(getApplicationContext());

        chargerClé();

        Toolbar toolbar = (Toolbar) findViewById(R.id.navigation_app);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        Modèle modèle = new Modèle();
        VueDemandeDePartie vue = new VueDemandeDePartie();
        présenteur = new PrésenteurDemandeDePartie(vue, modèle);
        présenteur.setSourceParties(new SourceParties());
        présenteur.démarer();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layoutPrincipal, vue);
        ft.commit();
    }

    private void chargerClé(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        clé = sharedPref.getString("token", "");
        if (clé.trim().isEmpty()) {
            startActivity(new Intent(this, ConnexionActivité.class));
        }
    }
}
