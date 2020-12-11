package com.example.brawler.présentation.vue.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brawler.R;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.présentation.présenteur.PrésenteurContacts;
import com.example.brawler.présentation.présenteur.PrésenteurDemandeDePartie;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    private List<Utilisateur> listUtilisateurs;
    private PrésenteurContacts présenteur;

    public ContactsAdapter(Context context){
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setListUtilisateurs(){
        listUtilisateurs = présenteur.getListContact();}

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
        holder.txtMessage.setText(nom);

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        if (listUtilisateurs == null)
            return 0;
        return listUtilisateurs.size();
    }

    //CLasse pour la carte du RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nomContact;
        private TextView txtMessage;
        private LinearLayout layoutContact;

        private ImageButton chatContact;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutContact = itemView.findViewById(R.id.contact_layout);
            nomContact = itemView.findViewById(R.id.nom_contact);
            txtMessage = itemView.findViewById(R.id.dernier_message_lbl);

            layoutContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    présenteur.chargerConversationUtilisateur(listUtilisateurs.get(getAdapterPosition()).getId());
                }
            });
        }
    }
    public void removeContact(int position){
        listUtilisateurs.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,listUtilisateurs.size());
    }
}
