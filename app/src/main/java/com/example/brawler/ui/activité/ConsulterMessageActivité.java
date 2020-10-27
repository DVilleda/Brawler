package com.example.brawler.ui.activité;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.example.brawler.DAO.SourceMessageApi;
import com.example.brawler.DAO.SourceUtilisateursApi;
import com.example.brawler.R;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.présenteur.PrésenteurConsulterMessage;
import com.example.brawler.présentation.présenteur.PrésenteurRechercheMatch;
import com.example.brawler.présentation.vue.VueConsulterMessage;
import com.example.brawler.présentation.vue.VueRechercheMatch;

public class ConsulterMessageActivité extends AppCompatActivity {

    private PrésenteurConsulterMessage présenteur;
    private String clé;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO remplacer la clé temporaire par la clé donner par l'Activité connexion
        clé = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDU0OTQzMTcsImlhdCI6MTYwMzY3OTkxNywic3ViIjoxfQ.Y2j0GjsT_CkvIbz1mGotuhjP-e9YwNxBbXLdMbJk9DY";

        Toolbar toolbar = (Toolbar) findViewById(R.id.navigation_app);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        Modèle modèle = new Modèle();
        modèle.setUtilisateurEnRevue(2);
        VueConsulterMessage vue = new VueConsulterMessage();
        présenteur = new PrésenteurConsulterMessage(vue, modèle);
        présenteur.setSource(new SourceMessageApi(clé));
        //TODO remplacr par l'id de l'utilisateur actuel
        présenteur.getMessages(2);
        vue.setPrésenteur(présenteur);

        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layoutMain, vue);
        ft.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
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
        }
        return super.onOptionsItemSelected(item);
    }

}
