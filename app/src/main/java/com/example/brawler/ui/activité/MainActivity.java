package com.example.brawler.ui.activité;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.brawler.DAO.SourceProfilApi;
import com.example.brawler.R;
import com.example.brawler.MockDAO.MockUtilisateur;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.présenteur.PrésenteurProfil;
import com.example.brawler.présentation.vue.VueProfil;

public class MainActivity extends AppCompatActivity {
    /**
     * Paramètres
     */
    private PrésenteurProfil présenteurProfil;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO remplacer la clé temporaire par la clé donner par l'Activité connexion
        token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDQ1ODg5NzUsImlhdCI6MTYwMjc3NDU3NSwic3ViIjoxfQ.orQR0Y5ge7tAjcJTEQ33MGvSZc2yMlhSg7lX_Yh3Lsc";

        Toolbar toolbar = (Toolbar) findViewById(R.id.navigation_app);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

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
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Appel de la méthode du présenteur pour changer la visibilté des informations du profil
     */
    public void ShowInfoProfil(View view){
        présenteurProfil.setVisibleInfos();
    }
}
