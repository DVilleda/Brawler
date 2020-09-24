package com.example.brawler.présentation.vue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.brawler.R;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.présentation.présenteur.PrésenteurProfil;

public class VueProfilModif extends Fragment {

    private PrésenteurProfil _presenteur;

    private EditText nomProfil;
    private EditText locationProfil;
    private EditText ageProfil;
    private EditText distanceProfil;
    private EditText niveauAdversaire;
    private EditText descriptionProfil;
    private Button confirmerModif;
    private Button annulerModif;

    public VueProfilModif(PrésenteurProfil présenteurProfil){
        _presenteur = présenteurProfil;
    }
    public void setPresenteur(PrésenteurProfil présenteurProfil){
        _presenteur = présenteurProfil;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vue = inflater.inflate(R.layout.fragment_profil_modif,container,false);
        nomProfil = vue.findViewById(R.id.Nom);
        locationProfil = vue.findViewById(R.id.Location);
        ageProfil = vue.findViewById(R.id.Age);
        distanceProfil = vue.findViewById(R.id.Distance);
        niveauAdversaire = vue.findViewById(R.id.Niveau);
        descriptionProfil = vue.findViewById(R.id.Description);
        confirmerModif = vue.findViewById(R.id.ModifierProfil);
        annulerModif = vue.findViewById(R.id.AnnulerModif);
        return vue;
    }

    public void chargerInfosActuel(Utilisateur utilisateur){
        if(utilisateur != null) {
            //nomProfil.setText(utilisateur.getNom(), TextView.BufferType.EDITABLE);
            //locationProfil.setText(utilisateur.getLocation(), TextView.BufferType.EDITABLE);
            //niveauAdversaire.setText(utilisateur.getNiveau().toString().toLowerCase(), TextView.BufferType.EDITABLE);
        }
    }
}
