package com.example.brawler.présentation.vue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brawler.R;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.présentation.présenteur.AdapteursVues.ContactsAdapter;
import com.example.brawler.présentation.présenteur.PrésenteurContacts;

import java.util.List;

public class VueContacts extends Fragment {
    private PrésenteurContacts _presenteur;
    private RecyclerView rvContacts;
    private ContactsAdapter contactsAdapter;

    public void setPresenteur(PrésenteurContacts presenteur){_presenteur = presenteur;}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voir_contact,container,false);
        rvContacts = (RecyclerView)view.findViewById(R.id.listeContacts);
        contactsAdapter = new ContactsAdapter(this.getContext());
        rvContacts.setAdapter(contactsAdapter);
        rvContacts.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return view;
    }

    public void afficherContacts(List<Utilisateur> listContact){
        if(listContact.size()>0)
            contactsAdapter.setListUtilisateurs(listContact);
        rafraichirVue();
    }

    public void rafraichirVue(){
        if(contactsAdapter!=null)
            contactsAdapter.notifyDataSetChanged();
    }
}
