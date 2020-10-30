package com.example.brawler.ui.activité;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.example.brawler.DAO.SourceUtilisateursApi;
import com.example.brawler.R;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.présenteur.PrésenteurConnexion;
import com.example.brawler.présentation.présenteur.PrésenteurCréationCompte;
import com.example.brawler.présentation.présenteur.PrésenteurRechercheMatch;
import com.example.brawler.présentation.vue.VueConnexion;
import com.example.brawler.présentation.vue.VueCréationCompte;
import com.example.brawler.présentation.vue.VueRechercheMatch;

public class ConnexionActivité extends AppCompatActivity {

    private PrésenteurConnexion présenteur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String cléTemp = "";

        Modèle modèle = new Modèle();
        VueConnexion vue = new VueConnexion();
        présenteur = new PrésenteurConnexion(vue, modèle);
        présenteur.setSource(new SourceUtilisateursApi(cléTemp));
        vue.setPrésenteur(présenteur);

        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layoutPrincipal, vue);
        ft.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
