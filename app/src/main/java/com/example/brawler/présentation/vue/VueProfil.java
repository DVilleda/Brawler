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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.brawler.R;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.présentation.présenteur.PrésenteurProfil;

public class VueProfil extends Fragment {

    /**
     * Paramètres
     */
    private PrésenteurProfil _presenteur;
    private TextView txtNom;
    private TextView txtEmplacement;
    private TextView txtNiveau;
    private LinearLayout expandableView;
    private CardView cardView;
    private Button modifierProfil;

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
        modifierProfil = vue.findViewById(R.id.aller_modif_profil);

        modifierProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VueProfilModif vueProfilModif = new VueProfilModif(_presenteur);
                vueProfilModif.chargerInfosActuel(_presenteur.getUtilisateur());
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(((ViewGroup)getView().getParent()).getId(),vueProfilModif);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

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
     * Pour utiliser la méthode on doit cliquer sur la photo de profil
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
