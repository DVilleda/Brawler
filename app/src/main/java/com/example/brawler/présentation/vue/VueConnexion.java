package com.example.brawler.présentation.vue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.CheckedTextView;

import androidx.fragment.app.Fragment;


import com.example.brawler.R;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.présentation.présenteur.PrésenteurConnexion;

public class VueConnexion extends Fragment {

    private PrésenteurConnexion présenteur;
    private EditText etNomUtilisateur;
    private EditText txtPassword;
    private CheckedTextView checkedtvSouvenir;
    private Button btnEnter;

    public void setPrésenteur(PrésenteurConnexion présenteur) {
        this.présenteur = présenteur;
    }


    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {

        View vue = inflater.inflate(R.layout.activity_connexion, container, false);

        etNomUtilisateur = vue.findViewById(R.id.etNomUtilisateur);
        txtPassword = vue.findViewById(R.id.txtPassword);
        checkedtvSouvenir = vue.findViewById(R.id.checkedtvSouvenir);
        btnEnter = vue.findViewById(R.id.btnEnter);

        btnEnter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                boolean valide = présenteur.VerifierInformations(getNomUtilisateur(), getMotDePasseUtilisateur());
                if (valide){
                    VueRechercheMatch VueRechercheMatch = new VueRechercheMatch();
                }
                else {
                    setMessageErreurMauvaisesCredentials();
                }
            }
        });

        return vue;
    }

    public String getNomUtilisateur() { return etNomUtilisateur.getText().toString(); }

    public String getMotDePasseUtilisateur() { return txtPassword.getText().toString(); }

    public boolean getSeSouvenir() { return checkedtvSouvenir.isChecked(); }

    public void setMessageErreurMauvaisesCredentials(){etNomUtilisateur.setError(getString(R.string.erreurConnexion) +" "+ getString(R.string.erreurInfoErronées)); }

}
