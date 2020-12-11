package com.example.brawler.présentation.vue;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.brawler.R;
import com.example.brawler.présentation.présenteur.PrésenteurConnexion;

public class VueConnexion extends Fragment {

    private PrésenteurConnexion présenteur;
    private TextView lienCreerCompte;
    private EditText etNomUtilisateur;
    private EditText txtPassword;
    private CheckBox checkBoxSouvenir;
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
        checkBoxSouvenir = vue.findViewById(R.id.checkBoxSouvenir);
        btnEnter = vue.findViewById(R.id.btnEnter);
        lienCreerCompte = vue.findViewById(R.id.tvCreerCompte);
        String creerCompteText = "Pas de compte? Creez-en un";

        SpannableString ss = new SpannableString(creerCompteText);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                présenteur.openVueCreationCompte();
            }
        };
        ss.setSpan(clickableSpan, 0, 26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        lienCreerCompte.setText(ss);
        lienCreerCompte.setMovementMethod(LinkMovementMethod.getInstance());

        if(getSeSouvenir()){
            etNomUtilisateur.setText(présenteur.getUsernameFromSharedPreferences());
            txtPassword.setText(présenteur.getMdpFromSharedPreferences());
        }
        btnEnter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String reponse = "0";

                présenteur.ThreadDeAuthentifer(getNomUtilisateur(), getMotDePasseUtilisateur());
            }
        });

        return vue;
    }

    public String getNomUtilisateur() { return etNomUtilisateur.getText().toString(); }

    public String getMotDePasseUtilisateur() { return txtPassword.getText().toString(); }

    public boolean getSeSouvenir() { return checkBoxSouvenir.isChecked(); }

    public void setMessageErreurMauvaisesCredentials(){
        etNomUtilisateur.setError(getString(R.string.erreurConnexion)
                +" "+ getString(R.string.erreurInfoErronées));
    }

    public void notifierConnexionSuccès(String reponse) {
        if (reponse.equals("0")) {
            setMessageErreurMauvaisesCredentials();
        } else {
            getActivity().setResult(Activity.RESULT_OK);
            getActivity().finish();
        }
    }

}
