package com.example.brawler.présentation.présenteur.AdapteursVues;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brawler.R;
import com.example.brawler.domaine.entité.Utilisateur;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    private List<Utilisateur> listUtilisateurs;

    public ContactsAdapter(Context context){
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setListUtilisateurs(List<Utilisateur> list){this.listUtilisateurs=list;}

    @NonNull
    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_objet_contact,parent,false);
        return new ContactsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.ViewHolder holder, int position) {
        String nom = listUtilisateurs.get(position).getNom();
        holder.nomContact.setText(nom);

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return listUtilisateurs.size();
    }

    //CLasse pour la carte du RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nomContact;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomContact = itemView.findViewById(R.id.nom_contact);
        }
    }
}
