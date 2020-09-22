package com.example.brawler.ui.activité;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.brawler.R;
import com.example.brawler.SourceDonnées.MockUtilisateur;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.présenteur.PrésenteurProfil;
import com.example.brawler.présentation.vue.VueProfil;

public class MainActivity extends AppCompatActivity {
    /**
     * Paramètres
     */
    private PrésenteurProfil présenteurProfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Modèle modèle = new Modèle();

        VueProfil vueProfil = new VueProfil();
        présenteurProfil = new PrésenteurProfil(vueProfil,modèle);
        présenteurProfil.setSourceUtilisateur(new MockUtilisateur());
        vueProfil.setPresenteur(présenteurProfil);

        //Transaction pour changer au fragement
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.layoutMain,vueProfil);
        fragmentTransaction.commit();
    }
    @Override
    public void onStart(){
        super.onStart();
        présenteurProfil.setUtilisateur();
    }

    /**
     * Appel de la méthode du présenteur pour changer la visibilté des informations du profil
     */
    public void ShowInfoProfil(View view){
        présenteurProfil.setVisibleInfos();
    }
}