package com.example.brawler.ui.activité;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.brawler.MockDAO.SourceUtilisateurFictif;
import com.example.brawler.R;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.présenteur.PrésenteurRechercheMatch;
import com.example.brawler.présentation.vue.VueRechercheMatch;

public class RecherchMatchActivité extends AppCompatActivity {

    private PrésenteurRechercheMatch présenteur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Modèle modèle = new Modèle();
        VueRechercheMatch vue = new VueRechercheMatch();
        présenteur = new PrésenteurRechercheMatch(vue, modèle);
        présenteur.setSource(new SourceUtilisateurFictif());
        vue.setPrésenteur(présenteur);

        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layoutPrincipal, vue);
        ft.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        présenteur.prochainUtilsateur();
    }
}
