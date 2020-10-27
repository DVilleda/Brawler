package com.example.brawler.présentation.vue.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brawler.R;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.présentation.présenteur.PrésenteurContacts;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    private List<Utilisateur> listUtilisateurs;
    private PrésenteurContacts présenteur;

    public ContactsAdapter(Context context){
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setListUtilisateurs(List<Utilisateur> list){this.listUtilisateurs=list;}

    public void setPrésenteur(PrésenteurContacts présenteur){this.présenteur = présenteur;}
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
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nomContact;
        private ImageButton suppContact;
        private ImageButton chatContact;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomContact = itemView.findViewById(R.id.nom_contact);
            suppContact = itemView.findViewById(R.id.boutton_supprimer);
            chatContact = itemView.findViewById(R.id.boutton_chat);
            suppContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeContact(getAdapterPosition());
                }
            });
            chatContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    présenteur.chargerConversationUtilisateur(listUtilisateurs.get(getAdapterPosition()).getId());
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
        public void removeContact(int position){
            listUtilisateurs.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,listUtilisateurs.size());
        }
    }
}
