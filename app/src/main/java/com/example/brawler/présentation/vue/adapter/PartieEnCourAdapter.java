package com.example.brawler.présentation.vue.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brawler.R;
import com.example.brawler.présentation.présenteur.PrésenteurDemandeDePartie;

public class PartieEnCourAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    PrésenteurDemandeDePartie présenteur;

    public PartieEnCourAdapter(PrésenteurDemandeDePartie présenteur) {
        this.présenteur = présenteur;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout racine = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_objet_partie_en_cour, parent, false);

        return new RecyclerView.ViewHolder(racine){};
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        TextView tvNomAdversaire = holder.itemView.findViewById(R.id.txtNomAdversaire);
        Button btnAcceder = holder.itemView.findViewById(R.id.btn_acceder);

        tvNomAdversaire.setText(présenteur.getDemandeParId(position).getAdversaire().getNom());
        btnAcceder.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                présenteur.accederPartie(position);

                                            }
                                        }
        );
    }

    @Override
    public int getItemCount() {
        if(présenteur==null) return 0;
        return présenteur.getNbDemande();
    }
}
