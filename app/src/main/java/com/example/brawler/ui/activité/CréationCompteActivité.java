package com.example.brawler.ui.activité;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.brawler.DAO.SourceUtilisateursApi;
import com.example.brawler.R;
import com.example.brawler.domaine.intéracteur.InscriptionUtilisateur;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.présenteur.PrésenteurConnexion;
import com.example.brawler.présentation.présenteur.PrésenteurCréationCompte;
import com.example.brawler.présentation.vue.VueConnexion;
import com.example.brawler.présentation.vue.VueCréationCompte;

public class CréationCompteActivité extends AppCompatActivity {

    private PrésenteurCréationCompte présenteur;
    private InscriptionUtilisateur interacteur;
    private String clé;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String cléTemp = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDQ1ODg5NzUsImlhdCI6MTYwMjc3NDU3NSwic3ViIjoxfQ.orQR0Y5ge7tAjcJTEQ33MGvSZc2yMlhSg7lX_Yh3Lsc";
        SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);
        String token = sharedPref.getString("token", cléTemp);

        clé = token;

        Modèle modèle = new Modèle();
        VueCréationCompte vue = new VueCréationCompte();
        présenteur = new PrésenteurCréationCompte(vue, modèle);
        interacteur = new InscriptionUtilisateur();
        présenteur.setSource(new SourceUtilisateursApi(clé));
        présenteur.setInteracteur(new InscriptionUtilisateur());


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
