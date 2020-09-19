package com.example.brawler.présentation.vue;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.brawler.R;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.présentation.présenteur.PrésenteurProfil;

public class VueProfil extends Fragment {

    /**
     * Paramètres
     */
    private PrésenteurProfil _presenteur;
    private Button modifier;
    private TextView txtNom;
    private TextView txtEmplacement;
    private TextView txtNiveau;
    LinearLayout expandableView;
    CardView cardView;

    /**
     * Méthode pour changer le présenteur du fragment
     * @param présenteurProfil
     */
    public void setPresenteur(PrésenteurProfil présenteurProfil){
        _presenteur = présenteurProfil;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vue=inflater.inflate(R.layout.fragment_profil_view,container,false);
        txtNom = vue.findViewById(R.id.nom_profil);
        txtEmplacement = vue.findViewById(R.id.txt_description);
        txtNiveau = vue.findViewById(R.id.niveau_profil);
        expandableView = vue.findViewById(R.id.expandable_view);
        cardView= vue.findViewById(R.id.profil_cardview);

        return vue;
    }

    /**
     * Méthode qui permet d'Affichier les informations de l'utilisateur en utilisant un
     * objet de type Utilisateur
     * @param utilisateur
     */
    public void afficherUtilisateur(Utilisateur utilisateur){
        txtNom.setText(utilisateur.getNom());
        txtEmplacement.setText(utilisateur.getLocation());
        txtNiveau.setText(utilisateur.getNiveau().toString());
    }

    /**
     * Cette méthode permet de changer la visibilité du Expandable View qui contient les
     * informations du profil public. Le TransitionManager est charge de gérer les animations
     * et la visibilité des informations.
     */
    public void expandInfosProfil() {
        if (expandableView.getVisibility() == View.INVISIBLE) {
            TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
            expandableView.setVisibility(View.VISIBLE);
        } else if (expandableView.getVisibility() == View.VISIBLE) {
            TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
            expandableView.setVisibility(View.INVISIBLE);
        }
    }
}
