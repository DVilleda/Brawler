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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.navigation_app);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

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
