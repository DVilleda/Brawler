package com.example.brawler.présentation.vue;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.brawler.R;
import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.présentation.présenteur.PrésenteurProfil;

public class VueProfilModif extends Fragment {

    private PrésenteurProfil _presenteur;

    private EditText nomProfil;
    private EditText locationProfil;
    private EditText ageProfil;
    private EditText distanceProfil;
    private TextView niveauAdversaire;
    private EditText descriptionProfil;
    private Button confirmerModif;
    private Button annulerModif;
    private Utilisateur utilisateurActuel;

    public void setPresenteur(PrésenteurProfil présenteurProfil){
        _presenteur = présenteurProfil;
        utilisateurActuel = présenteurProfil.getUtilisateur();
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

        registerForContextMenu(niveauAdversaire);

        chargerInfosActuel(utilisateurActuel);

        confirmerModif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utilisateurActuel.setNom(nomProfil.getText().toString());
                utilisateurActuel.setLocation(locationProfil.getText().toString());
                utilisateurActuel.setNiveau(Niveau.valueOf(niveauAdversaire.getText().toString().toUpperCase()));
                utilisateurActuel.setDescription(descriptionProfil.getText().toString());
                _presenteur.modifierUtilisateur(utilisateurActuel);
                Toast.makeText(getContext(),"Modifications apportées",Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        annulerModif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        return vue;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.niveau_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.option_1:
                niveauAdversaire.setText("Débutant");
                return true;
            case R.id.option_2:
                niveauAdversaire.setText("Intermédiaire");
                return true;
            case R.id.option_3:
                niveauAdversaire.setText("Expert");
                return true;
            case R.id.option_4:
                niveauAdversaire.setText("Légendaire");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void chargerInfosActuel(Utilisateur utilisateur){
        if(utilisateur != null) {
            nomProfil.setText(utilisateur.getNom());
            locationProfil.setText(utilisateur.getLocation());
            niveauAdversaire.setText(utilisateur.getNiveau().toString().toLowerCase());
            descriptionProfil.setText(utilisateur.getDescription());
        }else{
            Toast.makeText(getContext(),"Erreur de chargement du profil...",Toast.LENGTH_SHORT).show();
        }
    }
}
