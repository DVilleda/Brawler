package com.example.brawler.présentation.vue;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brawler.R;
import com.example.brawler.présentation.présenteur.PrésenteurConsulterMessage;
import com.example.brawler.présentation.vue.adapter.MessageAdapter;

public class VueConsulterMessage extends Fragment {


    private PrésenteurConsulterMessage présenteur;
    private TextView txtMessage;
    private ImageButton btnEnvoyerMessage;
    private RecyclerView rvMessages;
    private MessageAdapter messageAdapter;
    private ImageButton btnBack;
    private TextView txtConversation;
    private ImageView imgNomConversation;

    public void setPrésenteur(PrésenteurConsulterMessage présenteur) {
        this.présenteur = présenteur;
    }

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {

        View vue = inflater.inflate(R.layout.fragment_consulter_message, container, false);
        txtMessage = vue.findViewById(R.id.txtMessage);
        btnEnvoyerMessage = vue.findViewById(R.id.btnEnvoyerMessage);
        rvMessages = vue.findViewById(R.id.rvMessages);
        btnBack = vue.findViewById(R.id.btnBack);
        imgNomConversation = vue.findViewById(R.id.imgConversation);
        txtConversation = vue.findViewById(R.id.txtNomConversation);
        btnEnvoyerMessage.setEnabled(false);
        txtMessage.addTextChangedListener(envoyerMessageTextWatcher);
        messageAdapter = new MessageAdapter(présenteur);
        rvMessages.setAdapter(messageAdapter);

        LinearLayoutManager rvLayoutManager = new LinearLayoutManager(getContext());
        rvLayoutManager.setReverseLayout(true);
        rvMessages.setLayoutManager(rvLayoutManager);


        btnBack.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                présenteur.stopActivity();
            }
        });


        btnEnvoyerMessage.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                présenteur.envoyerMessage(txtMessage.getText().toString());
            }
        });

        return vue;
    }



    public void rafraîchir(){
        if(messageAdapter!=null){
            messageAdapter.notifyDataSetChanged();
        }
    }

    public void allerPremierMessage() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) rvMessages.getLayoutManager();
        layoutManager.scrollToPositionWithOffset(0, 0);
    }

    public void viderTxtMessage(){
        txtMessage.setText(null);
    }

    public void changerBtnEnvoyer(boolean bool){
        btnEnvoyerMessage.setEnabled(bool);
    }

    public boolean rvAuMax(){
        LinearLayoutManager layoutManager = (LinearLayoutManager) rvMessages.getLayoutManager();
        return layoutManager.findLastCompletelyVisibleItemPosition() == présenteur.getNbMessages() -1;
    }

    public void setInfoUtilisateur(String texte, byte[] photo){
        txtConversation.setText(texte);
        if(présenteur.getPhotoUtilisateur() != null)
            imgNomConversation.setImageBitmap(présenteur.getPhotoUtilisateur());
    }

    private TextWatcher envoyerMessageTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if(txtMessage.getText().toString().trim().isEmpty() || txtMessage.getText().toString() == "")
                btnEnvoyerMessage.setEnabled(false);
            else
                btnEnvoyerMessage.setEnabled(true);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(txtMessage.getText().toString().trim().isEmpty() || txtMessage.getText().toString() == "")
                btnEnvoyerMessage.setEnabled(false);
            else
                btnEnvoyerMessage.setEnabled(true);
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(txtMessage.getText().toString().trim().isEmpty() || txtMessage.getText().toString() == "")
                btnEnvoyerMessage.setEnabled(false);
            else
                btnEnvoyerMessage.setEnabled(true);
        }
    };

}
