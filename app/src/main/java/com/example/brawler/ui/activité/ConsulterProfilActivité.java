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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import com.example.brawler.DAO.SourceProfilApi;
import com.example.brawler.R;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.présenteur.PrésenteurProfil;
import com.example.brawler.présentation.vue.VueProfil;

public class ConsulterProfilActivité extends AppCompatActivity {
    /**
     * Paramètres de l'activité
     */
    private PrésenteurProfil présenteurProfil;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        /**
         * Obtenir le token du compte storé dans les prefs
         */
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = sharedPref.getString("token", "");
        if(token.trim().isEmpty()){
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
        Modèle modèle = new Modèle();
        VueProfil vueProfil = new VueProfil();
        présenteurProfil = new PrésenteurProfil(vueProfil,modèle);
        présenteurProfil.setSourceUtilisateur(new SourceProfilApi(token));
        vueProfil.setPresenteur(présenteurProfil);

        //Transaction pour changer au fragement
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.layoutPrincipal,vueProfil);
        fragmentTransaction.commit();
    }
    @Override
    public void onStart(){
        super.onStart();
        /**
         * Charger le thread pour obtenir l'utilisateur
         */
        présenteurProfil.chargerUtilisateur();
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
                break;
            case R.id.menu_match:
                Intent matcher = new Intent(this,RecherchMatchActivité.class);
                startActivity(matcher);
                break;
            case R.id.menu_contact:
                Intent contact = new Intent(this,CommunicationUtilisateurs.class);
                startActivity(contact);
                break;
            case R.id.menu_partie:
                Intent demandePartie = new Intent(this,ConsulterDemandePartieActivité.class);
                startActivity(demandePartie);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Appel de la méthode du présenteur pour changer la visibilté des informations du profil
     */
    public void ShowInfoProfil(View view){
        présenteurProfil.setVisibleInfos();
    }

    /**
     * Cette fonction a pour but de déconnecter l'utilisateur et le retourner à la page de connection
     * @param view
     */
    public void DeconnecterProfil(View view)
    {
        présenteurProfil.DeconnecterUtilisateur();
        finish();
        Intent connection = new Intent(this,ConnexionActivité.class);
        startActivity(connection);
    }
}
