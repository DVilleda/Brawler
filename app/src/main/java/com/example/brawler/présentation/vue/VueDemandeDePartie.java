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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brawler.R;
import com.example.brawler.présentation.présenteur.PrésenteurContacts;
import com.example.brawler.présentation.présenteur.PrésenteurDemandeDePartie;
import com.example.brawler.présentation.vue.adapter.ContactsAdapter;
import com.example.brawler.présentation.vue.adapter.DemandeDePartieAdapter;
import com.example.brawler.présentation.vue.adapter.PartieEnCourAdapter;

public class VueDemandeDePartie extends Fragment {
    private PrésenteurDemandeDePartie présenteur;
    private RecyclerView rvPartie;
    private TextView tvTypePartie;
    private Button btnDemandePartie;
    private Button btnPartienEnCour;
    private DemandeDePartieAdapter demandeDePartieAdapter;
    private PartieEnCourAdapter partieEnCourAdapter;

    public void setPresenteur(PrésenteurDemandeDePartie présenteur){
        this.présenteur = présenteur;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demande_de_partie,container,false);

        /**
         * Création du recyclerview et de l'adapteur. Fontion pour initer la liste et vue.
         */
        rvPartie = (RecyclerView)view.findViewById(R.id.listPartie);
        tvTypePartie = (TextView)view.findViewById(R.id.txtTypePartie);
        btnDemandePartie = (Button) view.findViewById(R.id.btnDemandePartie);
        btnPartienEnCour = (Button) view.findViewById(R.id.btnPartienEnCour);

        btnDemandePartie.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    présenteur.changerAffichage(true);
                                                    présenteur.démarer();

                                                }
                                            }
        );

        btnPartienEnCour.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View view) {
                                                     présenteur.changerAffichage(false);
                                                     présenteur.démarer();
                                                 }
                                             }
        );

        demandeDePartieAdapter = new DemandeDePartieAdapter(présenteur);
        partieEnCourAdapter = new PartieEnCourAdapter(présenteur);

        rvPartie.setAdapter(demandeDePartieAdapter);
        rvPartie.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return view;
    }

    /**
     * rafrachit la vue et met a jour les objet dans le Recyclerview
     */
    public void rafraichirVue(){
        if(demandeDePartieAdapter!=null)
            demandeDePartieAdapter.notifyDataSetChanged();
    }

    /**
     * Chagne l'Adapter du recyclerView
     * @param voirDemandeDePartie
     */
    public void chargerAdapterListePartie(boolean voirDemandeDePartie){
        if (voirDemandeDePartie) {
            rvPartie.setAdapter(demandeDePartieAdapter);
            rvPartie.setLayoutManager(new LinearLayoutManager(this.getContext()));
        } else {
            rvPartie.setAdapter(partieEnCourAdapter);
            rvPartie.setLayoutManager(new LinearLayoutManager(this.getContext()));
        }

    }

    public void setTextTvTypePartite(int ressourceId){
        tvTypePartie.setText(getString(ressourceId));
    }
}
