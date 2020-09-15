package com.example.brawler.présentation.vue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.brawler.R;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.présentation.présenteur.PrésenteurProfil;

public class VueProfil extends Fragment {

    //Variables
    private PrésenteurProfil _presenteur;
    private Button modifier;
    private TextView txtNom;
    private TextView txtEmplacement;
    private TextView txtNiveau;

    public void setPresenteur(PrésenteurProfil présenteurProfil){
        _presenteur = présenteurProfil;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vue=inflater.inflate(R.layout.fragment_profil,container,false);
        txtNom = vue.findViewById(R.id.Nom);
        txtEmplacement = vue.findViewById(R.id.Location);
        txtNiveau = vue.findViewById(R.id.Niveau);
        return vue;
    }

    public void afficherUtilisateur(Utilisateur utilisateur){
        txtNom.setText(utilisateur.getNom());
        txtEmplacement.setText(utilisateur.getLocation());
        txtNiveau.setText(utilisateur.getNiveau().toString());
    }
}
