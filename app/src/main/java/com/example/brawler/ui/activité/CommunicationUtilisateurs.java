package com.example.brawler.ui.activité;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.brawler.DAO.SourceUtilisateursApi;
import com.example.brawler.R;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.présenteur.PrésenteurContacts;
import com.example.brawler.présentation.vue.VueContacts;

public class CommunicationUtilisateurs extends AppCompatActivity {
    /**
     * Paramètres de l'activité
     */
    private PrésenteurContacts présenteurContacts;
    private String clé;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        /**
         * Obtenir le token du compte storé dans les prefs
         */
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        clé = sharedPref.getString("token", "");
        if(clé.trim().isEmpty()){
            startActivity(new Intent(this, ConnexionActivité.class));
        }

        /**
         * Aller chercher le custom toolbar et l'afficher au haut de l'activité
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.navigation_app);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        /**
         * Initier le présenteur, le modèle, la source et afficher le fragment
         */
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
        /**
         * Switch case pour naviguer les activités principales de l'application
         */
        switch (item.getItemId()){
            case R.id.menu_profil:
                Intent profil = new Intent(this, ConsulterProfilActivité.class);
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