package com.example.brawler.présentation.vue.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brawler.R;
import com.example.brawler.présentation.présenteur.PrésenteurDemandeDePartie;

public class DemandeDePartieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    PrésenteurDemandeDePartie présenteur;

    public DemandeDePartieAdapter(PrésenteurDemandeDePartie présenteur) {
        this.présenteur = présenteur;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout racine = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_objet_partie, parent, false);

        return new RecyclerView.ViewHolder(racine){};
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
