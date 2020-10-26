package com.example.brawler.présentation.vue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brawler.R;
import com.example.brawler.présentation.présenteur.PrésenteurConsulterMessage;
import com.example.brawler.présentation.présenteur.PrésenteurRechercheMatch;

public class VueConsulterMessage extends Fragment {

    PrésenteurConsulterMessage présenteur;
    TextView txtMessage;
    Button  btnEnvoyerMessage;
    RecyclerView rvMessages;
    public void setPrésenteur(PrésenteurConsulterMessage présenteur) {
        this.présenteur = présenteur;
    }

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {

        View vue = inflater.inflate(R.layout.fragement_recherche_match, container, false);

        txtMessage = vue.findViewById(R.id.txtMessage);
        btnEnvoyerMessage = vue.findViewById(R.id.btnEnvoyerMessage);

        btnEnvoyerMessage.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                présenteur.envoyerMessage(txtMessage.getText().toString());
            }
        });

        return vue;
    }

    public void rafraichir() {

    }
}
