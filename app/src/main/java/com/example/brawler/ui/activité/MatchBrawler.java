package com.example.brawler.ui.activité;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.example.brawler.DAO.SourcePartieJoueurApi;
import com.example.brawler.R;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.présenteur.PresenteurPartieBrawler;
import com.example.brawler.présentation.vue.VuePartieBrawler;

public class MatchBrawler extends AppCompatActivity {
    PresenteurPartieBrawler _presenteur;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_brawler);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = sharedPref.getString("token", "");
        if(token.trim().isEmpty()){
            startActivity(new Intent(this, ConnexionActivité.class));
        }
        Modèle modèle = new Modèle();
        VuePartieBrawler vuePartieBrawler = new VuePartieBrawler();
        _presenteur = new PresenteurPartieBrawler(vuePartieBrawler,modèle);
        _presenteur.SetSourcePartie(new SourcePartieJoueurApi(token));
        vuePartieBrawler.setPresenteur(_presenteur);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layoutBrawler,vuePartieBrawler);
        ft.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //TODO UTILISER LE BUNDLE POUR AVOIR LE ID
        _presenteur.chargerPartie(3);
    }
}