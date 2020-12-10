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
import androidx.fragment.app.FragmentTransaction;

import com.example.brawler.DAO.SourceMessageApi;
import com.example.brawler.DAO.SourceUtilisateurApi;
import com.example.brawler.R;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.présenteur.PrésenteurConsulterMessage;
import com.example.brawler.présentation.vue.VueConsulterMessage;
import com.example.brawler.ui.activité.Services.ServiceNotificationMessage;

public class ConsulterMessageActivité extends AppCompatActivity {
    private static final String EXTRA_ID_UTILSAITEUR = "com.brawler.idUtilisateur";

    private PrésenteurConsulterMessage présenteur;
    private String clé;
    Modèle modèle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_message);


        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        clé = sharedPref.getString("token", "");
        if(clé.trim().isEmpty()){
            startActivity(new Intent(this, ConnexionActivité.class));
        }

        modèle = new Modèle();
        VueConsulterMessage vue = new VueConsulterMessage();
        présenteur = new PrésenteurConsulterMessage(vue, modèle);
        présenteur.setSourceMessage(new SourceMessageApi(clé));
        présenteur.setSourceUtilisateur(new SourceUtilisateurApi(clé));
        vue.setPrésenteur(présenteur);

        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layoutMain, vue);
        ft.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        ServiceNotificationMessage.arrêterJob(getApplicationContext());

        int idUtilisateurConversation = getIntent().getIntExtra(EXTRA_ID_UTILSAITEUR, -1);
        modèle.setUtilisateurEnRevue(idUtilisateurConversation);
        présenteur.commencerVoirMessage();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ServiceNotificationMessage.arrêterJob(getApplicationContext());
        présenteur.commencerRafraichir();
    }

    @Override
    protected void onPause(){
        super.onPause();
        ServiceNotificationMessage.démarerJob(getApplicationContext());
        présenteur.arrêterRafraichir();
    }

    @Override
    protected void onStop(){
        super.onStop();
        ServiceNotificationMessage.démarerJob(getApplicationContext());
        présenteur.arrêterRafraichir();
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
                Intent profil = new Intent(this, ConsulterProfilActivité.class);
                startActivity(profil);
                break;
            case R.id.menu_match:
                Intent matcher = new Intent(this,RecherchMatchActivité.class);
                startActivity(matcher);
                break;
            case R.id.menu_contact:
                Intent contact = new Intent(this,CommunicationUtilisateurs.class);
                startActivity(contact);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
