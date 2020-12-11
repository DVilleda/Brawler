package com.example.brawler.ui.activité;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.brawler.DAO.SourceUtilisateursApi;
import com.example.brawler.MockDAO.SourceUtilisateurFictif;
import com.example.brawler.R;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.présenteur.PrésenteurContacts;
import com.example.brawler.présentation.vue.VueContacts;

public class CommunicationUtilisateurs extends AppCompatActivity {
    private PrésenteurContacts présenteurContacts;
    private String clé;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        clé = sharedPref.getString("token", "");
        if(clé.trim().isEmpty()){
            startActivity(new Intent(this, ConnexionActivité.class));
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.navigation_app);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        VueContacts vueContacts= new VueContacts();
        Modèle modèle = new Modèle();
        présenteurContacts = new PrésenteurContacts(vueContacts,modèle, this);
        présenteurContacts.setSource(new SourceUtilisateursApi(clé));
        vueContacts.setPresenteur(présenteurContacts);

        //Transaction pour changer au fragement
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.layoutContacts,vueContacts);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        présenteurContacts.chargerListeContacts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        présenteurContacts.chargerListeContacts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_navigation_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_profil:
                Intent profil = new Intent(this,MainActivity.class);
                startActivity(profil);
                break;
            case R.id.menu_match:
                Intent matcher = new Intent(this,RecherchMatchActivité.class);
                startActivity(matcher);
                break;
            case R.id.menu_contact:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}