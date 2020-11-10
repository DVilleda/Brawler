package com.example.brawler.ui.activité;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.brawler.DAO.SourceUtilisateursApi;
import com.example.brawler.R;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.présenteur.PrésenteurConnexion;
import com.example.brawler.présentation.vue.VueConnexion;

public class ConnexionActivité extends AppCompatActivity {

    private PrésenteurConnexion présenteur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vide);

        String cléTemp = "";

        Modèle modèle = new Modèle();
        VueConnexion vue = new VueConnexion();
        présenteur = new PrésenteurConnexion(vue, modèle);
        présenteur.setSource(new SourceUtilisateursApi(cléTemp));
        vue.setPrésenteur(présenteur);

        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layoutVide, vue);
        ft.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
