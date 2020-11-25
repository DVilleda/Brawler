package com.example.brawler.ui.activité;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.example.brawler.DAO.SourceLikeApi;
import com.example.brawler.DAO.SourceUtilisateursApi;
import com.example.brawler.R;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.présenteur.PrésenteurRechercheMatch;
import com.example.brawler.présentation.vue.VueRechercheMatch;
import com.example.brawler.ui.activité.Services.ServiceNotificationMessage;

public class RecherchMatchActivité extends AppCompatActivity {

    private PrésenteurRechercheMatch présenteur;
    private String clé;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ServiceNotificationMessage.démarerJob(getApplicationContext());

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

        Modèle modèle = new Modèle();
        VueRechercheMatch vue = new VueRechercheMatch();
        présenteur = new PrésenteurRechercheMatch(vue, modèle);
        présenteur.setSourceUtilisateurs(new SourceUtilisateursApi(clé));
        présenteur.setSourceLike(new SourceLikeApi(clé));
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

    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        clé = sharedPref.getString("token", "");
        présenteur.setSourceUtilisateurs(new SourceUtilisateursApi(clé));
        présenteur.setSourceLike(new SourceLikeApi(clé));
        présenteur.prochainUtilsateur();
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
                break;
            case R.id.menu_contact:
                Intent contact = new Intent(this,CommunicationUtilisateurs.class);
                startActivity(contact);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
