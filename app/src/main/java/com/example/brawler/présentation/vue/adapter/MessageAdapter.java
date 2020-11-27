package com.example.brawler.présentation.vue.adapter;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.text.BoringLayout;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        TextView tvTexte = holder.itemView.findViewById(R.id.txtMessage);
        TextView tvNom = holder.itemView.findViewById(R.id.txtNom);
        TextView tvTemps = holder.itemView.findViewById(R.id.txtTemps);
        ImageView imageView = (ImageView) holder.itemView.findViewById(R.id.imgUtilisateur);

        tvNom.setText(message.getUtilisateur().getNom());
        tvTexte.setText(message.getTexte());
        tvTemps.setText(date);

        if(message.getUtilisateur().getId() == présenteur.getIdUtilisateur()) {
            ((LinearLayout) holder.itemView.findViewById(R.id.layoutMessage)).setGravity(Gravity.LEFT);
            tvTexte.setTextColor(Color.BLACK);
            imageView.setVisibility(View.VISIBLE);
            if(présenteur.getPhotoUtilisateur() != null)
                ((ImageView) holder.itemView.findViewById(R.id.imgUtilisateur)).setImageBitmap(présenteur.getPhotoUtilisateur());
        } else {
            ((LinearLayout) holder.itemView.findViewById(R.id.layoutMessage)).setGravity(Gravity.RIGHT);
            tvTexte.setTextColor(Color.BLUE);
            imageView.setVisibility(View.INVISIBLE);
        }

        ((TextView) holder.itemView.findViewById(R.id.txtMessage)).setOnClickListener(new View.OnClickListener() {
                                                                                          @Override
                                                                                          public void onClick(View view) {
                                                                                              int estVisible = ((TextView) holder.itemView.findViewById(R.id.txtTemps)).getVisibility();
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
