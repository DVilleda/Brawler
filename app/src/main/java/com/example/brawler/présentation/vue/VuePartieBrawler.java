package com.example.brawler.présentation.vue;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.brawler.R;
import com.example.brawler.présentation.présenteur.PresenteurPartieBrawler;

public class VuePartieBrawler extends Fragment {
    private PresenteurPartieBrawler _presenteur;
    private Button btnChoisirMove;
    private ImageButton btnAbandonner;
    private Button btnEnvoyerMove;
    private ImageButton btnRetourner;
    private ImageView imgMoveSoi;
    private ImageView imgMoveADV;
    private ImageView imgPFPADV;
    private TextView nomAdversaire;
    private TextView txtResultat;
    private TextView txtRondes;

    public void setPresenteur(PresenteurPartieBrawler presenteur){
        this._presenteur =presenteur;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vue=inflater.inflate(R.layout.fragment_jouer_partie,container,false);
        btnChoisirMove = vue.findViewById(R.id.choisirArme);
        btnAbandonner = vue.findViewById(R.id.btnGiveUp);
        btnEnvoyerMove = vue.findViewById(R.id.btnJouer);
        btnRetourner = vue.findViewById(R.id.btnRetourner);
        imgMoveSoi = vue.findViewById(R.id.mouvement_soi);
        imgMoveADV = vue.findViewById(R.id.mouvement_adv);
        imgPFPADV = vue.findViewById(R.id.imgAdversaire);
        nomAdversaire = vue.findViewById(R.id.txtNomAdversaire);
        txtResultat = vue.findViewById(R.id.txt_result);
        txtRondes = vue.findViewById(R.id.num_tour);

        btnChoisirMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerForContextMenu(btnChoisirMove);
            }
        });

        btnRetourner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _presenteur.QuitterVue();
            }
        });
        btnEnvoyerMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                envoyerMouvementChoisi();
                //btnEnvoyerMove.setEnabled(false);
            }
        });
        _presenteur.scheduleRafraichirPartie();
        return vue;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.option_jouer_main_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.JouerRoche:
                _presenteur.ChangerIMGMouvement(1);
                return true;
            case R.id.JouerPapier:
                _presenteur.ChangerIMGMouvement(2);
                return true;
            case R.id.JouerCiceaux:
                _presenteur.ChangerIMGMouvement(3);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void changerIMGMoveSoi(int mouvement){
        if(mouvement == 1){
            imgMoveSoi.setImageResource(R.drawable.rock);
            imgMoveSoi.setTag("Roche");
        }else if(mouvement == 2){
            imgMoveSoi.setImageResource(R.drawable.paper);
            imgMoveSoi.setTag("Papier");
        }else if(mouvement ==3){
            imgMoveSoi.setImageResource(R.drawable.scissors);
            imgMoveSoi.setTag("Ciseau");
        }else{
            imgMoveSoi.setImageResource(R.drawable.rockpaperscissors);
            imgMoveSoi.setTag("RIEN");
        }
    }

    public void changerIMGMoveADV(String mouvement){
        switch (mouvement) {
            case "Roche":
                imgMoveADV.setImageResource(R.drawable.rock);
                imgMoveADV.setTag("Roche");
                break;
            case "Papier":
                imgMoveADV.setImageResource(R.drawable.paper);
                imgMoveADV.setTag("Papier");
                break;
            case "Ciseau":
                imgMoveADV.setImageResource(R.drawable.scissors);
                imgMoveADV.setTag("Ciseau");
                break;
            default:
                imgMoveADV.setImageResource(R.drawable.rockpaperscissors);
                imgMoveADV.setTag("RIEN");
                break;
        }
    }

    public void envoyerMouvementChoisi(){
        String move="";
        if(imgMoveSoi.getTag() !=null && imgMoveSoi.getTag().equals("Roche")){
            _presenteur.envoyerMoument("Roche");
        }else if(imgMoveSoi.getTag() !=null && imgMoveSoi.getTag().equals("Papier")){
            _presenteur.envoyerMoument("Papier");
        }else if (imgMoveSoi.getTag() !=null && imgMoveSoi.getTag().equals("Ciseau")){
            _presenteur.envoyerMoument("Ciseau");
        }else{
            Toast.makeText(this.getContext(),"Aucune option sélectionné...",Toast.LENGTH_SHORT).show();
        }
    }

    public void changerNumTour(int i){
        String msg ="Tour numéro : "+ i;
        txtRondes.setText(msg);
    }

    public void changerMSGResultat(String msg){
        txtResultat.setText(msg);
    }
}
