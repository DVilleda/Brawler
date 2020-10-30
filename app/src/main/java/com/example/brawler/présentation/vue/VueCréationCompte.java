package com.example.brawler.présentation.vue;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.brawler.R;
import com.example.brawler.présentation.présenteur.PrésenteurCréationCompte;
import com.example.brawler.ui.activité.ConnexionActivité;

public class VueCréationCompte extends Fragment {

    PrésenteurCréationCompte présenteur;

    EditText etEmail, etMdp, etPrenom, etLocation, etDescription;
    Button btnEnter;

    public void setPrésenteur(PrésenteurCréationCompte présenteur) {
        this.présenteur = présenteur;
    }

    @Override
    public View onCreateView (LayoutInflater inflater,
                          ViewGroup container,
                          Bundle savedInstanceState) {

        View vue = inflater.inflate(R.layout.activity_creation_compte, container, false);

        etEmail = vue.findViewById(R.id.ptEmail);
        etMdp = vue.findViewById(R.id.etPassword);
        etPrenom = vue.findViewById(R.id.etNom);
        etLocation = vue.findViewById(R.id.etLocation);
        etDescription = vue.findViewById(R.id.etDescription);
        btnEnter = vue.findViewById(R.id.btnEnter);

        btnEnter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email = getEmailText();
                String mdp = getMdpText();
                String prénom = getPrénomText();
                String location = getLocationText();
                String description = getDescriptionText();

                String reponse = présenteur.ThreadDeCreationDeCompte(email, mdp, prénom, location, description);
                Log.i("La reponse était : ", reponse);
                if (reponse.equals("succès")){
                    Toast.makeText(getContext(),reponse,Toast.LENGTH_SHORT).show();
                    Intent nouvelleVue = new Intent(getActivity(), ConnexionActivité.class);
                    startActivity(nouvelleVue);
                }else {
                    Toast.makeText(getContext(),reponse,Toast.LENGTH_SHORT).show();
                }
            }
        });

        return vue;
    }

    public String getEmailText(){ return etEmail.getText().toString(); }
    public String getMdpText(){ return etMdp.getText().toString(); }
    public String getPrénomText(){ return etPrenom.getText().toString(); }
    public String getLocationText(){ return etLocation.getText().toString(); }
    public String getDescriptionText(){ return etDescription.getText().toString(); }
}
