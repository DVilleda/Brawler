package com.example.brawler.présentation.vue.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brawler.R;
import com.example.brawler.présentation.présenteur.PrésenteurConsulterMessage;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private PrésenteurConsulterMessage présenteur;

    public MessageAdapter(PrésenteurConsulterMessage présenteur) {
        this.présenteur = présenteur;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LinearLayout racine = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_vue, parent, false);

        return new RecyclerView.ViewHolder(racine){};
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        if(présenteur==null) return 0;
        return présenteur.getNbMessages();
    }
}
