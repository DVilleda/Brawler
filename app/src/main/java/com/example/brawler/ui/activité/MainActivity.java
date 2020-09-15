package com.example.brawler.ui.activité;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.brawler.R;
import com.example.brawler.SourceDonnées.MockUtilisateur;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.présenteur.PrésenteurProfil;
import com.example.brawler.présentation.vue.VueProfil;

public class MainActivity extends AppCompatActivity {

    private PrésenteurProfil _presenteur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Modèle modèle = new Modèle();

        VueProfil vueProfil = new VueProfil();
        _presenteur = new PrésenteurProfil(this,vueProfil,modèle);
        _presenteur.setSourceUtilisateur(new MockUtilisateur());
        vueProfil.setPresenteur(_presenteur);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.layoutMain,vueProfil);
        fragmentTransaction.commit();
    }
    @Override
    public void onStart(){
        super.onStart();
        _presenteur.setUtilisateur();
    }
}