package com.example.brawler.présentation.vue.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brawler.R;
import com.example.brawler.domaine.entité.Message;
import com.example.brawler.présentation.présenteur.PrésenteurConsulterMessage;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
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
        Message message = présenteur.getMessageParPos(position);
        String date = message.getTemps().getYear() + "-"+ message.getTemps().getMonth() + "-"+message.getTemps().getDay() + " " + message.getTemps().getHours() + ":" + message.getTemps().getHours();
        ((TextView) holder.itemView.findViewById(R.id.txtMessage)).setText(message.getTexte());
        ((TextView) holder.itemView.findViewById(R.id.txtNom)).setText(message.getUtilisateur().getNom());
        ((TextView) holder.itemView.findViewById(R.id.txtTemps)).setText(date);
        if(message.getUtilisateur().getId() == présenteur.getIdUtilisateur()) {
            ((TextView) holder.itemView.findViewById(R.id.txtMessage)).setBackgroundColor(Color.parseColor("#a8a2a8"));
        } else {
            ((TextView) holder.itemView.findViewById(R.id.txtMessage)).setBackgroundColor(Color.parseColor("#e80c3f"));
        }
    }

    @Override
    public int getItemCount() {
        if(présenteur==null) return 0;
        return présenteur.getNbMessages();
    }
}
