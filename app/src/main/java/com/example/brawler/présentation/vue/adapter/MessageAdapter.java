package com.example.brawler.présentation.vue.adapter;

import android.graphics.Color;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        Message message = présenteur.getMessageParPos(position);
        String date = message.getTemps().getYear() + "-"+ message.getTemps().getMonth() + "-"+message.getTemps().getDay() + " " + message.getTemps().getHours() + ":" + message.getTemps().getMinutes();
        ((TextView) holder.itemView.findViewById(R.id.txtMessage)).setText(message.getTexte());
        ((TextView) holder.itemView.findViewById(R.id.txtNom)).setText(message.getUtilisateur().getNom());
        ((TextView) holder.itemView.findViewById(R.id.txtTemps)).setText(date);
        if(message.getUtilisateur().getId() == présenteur.getIdUtilisateur()) {
            ((LinearLayout) holder.itemView.findViewById(R.id.layoutMessage)).setGravity(Gravity.LEFT);
            ((TextView) holder.itemView.findViewById(R.id.txtMessage)).setBackgroundResource(R.drawable.autre_utilsateur_message);
        } else {
            ((LinearLayout) holder.itemView.findViewById(R.id.layoutMessage)).setGravity(Gravity.RIGHT);
            ((TextView) holder.itemView.findViewById(R.id.txtMessage)).setBackgroundResource(R.drawable.utilisateur_messsage);
        }


        ((TextView) holder.itemView.findViewById(R.id.txtMessage)).setOnClickListener(new View.OnClickListener() {
                                                                                          @Override
                                                                                          public void onClick(View view) {
                                                                                              int estVisible = ((TextView) holder.itemView.findViewById(R.id.txtTemps)).getVisibility();
                                                                                              Log.d("pos", String.valueOf(position));
                                                                                              if(estVisible == View.GONE) {
                                                                                                  ((TextView) holder.itemView.findViewById(R.id.txtTemps)).setVisibility(View.VISIBLE);
                                                                                              } else {
                                                                                                  ((TextView) holder.itemView.findViewById(R.id.txtTemps)).setVisibility(View.GONE);
                                                                                              }
                                                                                          }
                                                                                      }
        );
    }

    @Override
    public int getItemCount() {
        if(présenteur==null) return 0;
        return présenteur.getNbMessages();
    }
}
