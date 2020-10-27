package com.example.brawler.présentation.vue;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.brawler.R;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.présentation.présenteur.PrésenteurConnexion;
import com.example.brawler.ui.activité.ConnexionActivité;
import com.example.brawler.ui.activité.CréationCompteActivité;
import com.example.brawler.ui.activité.RecherchMatchActivité;

import org.json.JSONException;

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
                Log.i("info: ","New compte creation button pressed");
                openVueCreationCompte();
            }
        };
        ss.setSpan(clickableSpan, 0, 26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        lienCreerCompte.setText(ss);
        lienCreerCompte.setMovementMethod(LinkMovementMethod.getInstance());

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String savedUsername = sharedPref.getString("savedUsername", "");
        String savedPassword = sharedPref.getString("savedMdp", "");

        if(getSeSouvenir()){
            etNomUtilisateur.setText(savedUsername);
            txtPassword.setText(savedPassword);
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

    public void openVueCreationCompte(){
        Intent nouvelleVue = new Intent(getActivity(), CréationCompteActivité.class);
        startActivity(nouvelleVue);
    }

    public String getNomUtilisateur() { return etNomUtilisateur.getText().toString(); }

    public String getMotDePasseUtilisateur() { return txtPassword.getText().toString(); }

    public boolean getSeSouvenir() { return checkBoxSouvenir.isChecked(); }

    public void setMessageErreurMauvaisesCredentials(){etNomUtilisateur.setError(getString(R.string.erreurConnexion) +" "+ getString(R.string.erreurInfoErronées)); }

    public void notifierConnexionSuccès(String reponse) {
        if (reponse.equals("0")) {
            Log.e("Le failure était: ", reponse);
            setMessageErreurMauvaisesCredentials();
            Toast.makeText(getContext(), reponse, Toast.LENGTH_SHORT).show();
        } else {
            Log.e("Le success était: ", reponse);
            Toast.makeText(getContext(), reponse, Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
            SharedPreferences.Editor editor = sharedPref.edit();
            if (getSeSouvenir()) {
                editor.putString("savedUsername", getNomUtilisateur());
                editor.putString("savedMdp", getMotDePasseUtilisateur());
            }
            editor.putString("token", reponse);
            editor.apply();
            getActivity().setResult(Activity.RESULT_OK);
            getActivity().finish();
        }
    }
}
