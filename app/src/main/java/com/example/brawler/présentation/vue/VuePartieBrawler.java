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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.brawler.R;
import com.example.brawler.présentation.présenteur.PresenteurPartieBrawler;

public class VuePartieBrawler extends Fragment {
    private PresenteurPartieBrawler _presenteur;
    private ImageButton btnRoche;
    private ImageButton btnPapier;
    private ImageButton btnCiseaux;
    private Button btnEnvoyerMove;
    private ImageButton btnRetourner;
    private ImageView imgMoveSoi;
    private ImageView imgDernierMoveADV;
    private ImageView imgPFPADV;
    private ImageView imgDernierMoveSoi;
    private TextView nomAdversaire;
    private TextView txtResultat;
    private TextView txtRondes;
    private ConstraintLayout layoutResultats;

    /**
     * Set le présenteur du fragment
     * @param presenteur
     */
    public void setPresenteur(PresenteurPartieBrawler presenteur){
        this._presenteur =presenteur;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vue=inflater.inflate(R.layout.fragment_jouer_partie,container,false);
        btnRoche = vue.findViewById(R.id.btn_roche);
        btnPapier = vue.findViewById(R.id.btn_papier);
        btnCiseaux = vue.findViewById(R.id.btn_ciseaux);
        btnEnvoyerMove = vue.findViewById(R.id.btnJouer);
        btnRetourner = vue.findViewById(R.id.btnRetourner);
        imgMoveSoi = vue.findViewById(R.id.mouvement_soi);
        imgDernierMoveADV = vue.findViewById(R.id.mouvement_adv);
        imgDernierMoveSoi =vue.findViewById(R.id.move_soi_dernier);
        txtResultat = vue.findViewById(R.id.txt_result);
        txtRondes = vue.findViewById(R.id.num_tour);
        layoutResultats = vue.findViewById(R.id.resultat_last_round);
        txtResultat.setVisibility(View.INVISIBLE);

        /**
         * Assigner l'image à chaque boutton des mains
         */
        btnRoche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changerIMGMoveSoi(1);
            }
        });
        btnPapier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changerIMGMoveSoi(2);
            }
        });
        btnCiseaux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changerIMGMoveSoi(3);
            }
        });

        /**
         * Fonction pour désactiver le boutton et envoyer le mouvement
         */
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
                btnEnvoyerMove.setEnabled(false);
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

    /**
     * changer le status d'utilisation pour le boutton
     * @param bool
     */
    public void changerStatusBouttonSend(boolean bool){
        btnEnvoyerMove.setEnabled(bool);
    }

    /**
     * Cette fonction change l'image du mouvement personnel avec le png qu'on choisit
     * @param mouvement le mouvement fourni
     */
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

    /**
     * Cette fonction change l'image du mouvement de l'adversaire
     * avec le png qu'on choisit selon le mouvement fourni
     * @param mouvement le mouvement fourni
     */
    public void changerIMGMoveADV(String mouvement){
        switch (mouvement) {
            case "Roche":
                imgDernierMoveADV.setImageResource(R.drawable.rock);
                imgDernierMoveADV.setTag("Roche");
                break;
            case "Papier":
                imgDernierMoveADV.setImageResource(R.drawable.paper);
                imgDernierMoveADV.setTag("Papier");
                break;
            case "Ciseau":
                imgDernierMoveADV.setImageResource(R.drawable.scissors);
                imgDernierMoveADV.setTag("Ciseau");
                break;
            default:
                imgDernierMoveADV.setImageResource(R.drawable.rockpaperscissors);
                imgDernierMoveADV.setTag("RIEN");
                break;
        }
    }

    /**
     * Cette fonction change l'image du mouvement qu'on a fait au dernier tour
     * avec le png qu'on choisit selon le mouvement fourni
     * @param mouvement le mouvement fourni
     */
    public void changerDernierMoveSoi(String mouvement){
        switch (mouvement) {
            case "Roche":
                imgDernierMoveSoi.setImageResource(R.drawable.rock);
                imgDernierMoveSoi.setTag("Roche");
                break;
            case "Papier":
                imgDernierMoveSoi.setImageResource(R.drawable.paper);
                imgDernierMoveSoi.setTag("Papier");
                break;
            case "Ciseau":
                imgDernierMoveSoi.setImageResource(R.drawable.scissors);
                imgDernierMoveSoi.setTag("Ciseau");
                break;
            default:
                imgDernierMoveSoi.setImageResource(R.drawable.rockpaperscissors);
                imgDernierMoveSoi.setTag("RIEN");
                break;
        }
    }

    /**
     * Cette fonction envoi un mouvement selon le tag de l'image actuel
     */
    public void envoyerMouvementChoisi(){
        if(imgMoveSoi.getTag() !=null && imgMoveSoi.getTag().equals("Roche")){
            _presenteur.envoyerMouvement("Roche");
        }else if(imgMoveSoi.getTag() !=null && imgMoveSoi.getTag().equals("Papier")){
            _presenteur.envoyerMouvement("Papier");
        }else if (imgMoveSoi.getTag() !=null && imgMoveSoi.getTag().equals("Ciseau")){
            _presenteur.envoyerMouvement("Ciseau");
        }else{
            Toast.makeText(this.getContext(),"Aucune option sélectionné...",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Changer le numéro du tour
     * @param i le numéro du tour
     */
    public void changerNumTour(int i){
        String msg ="Tour numéro : "+ i;
        txtRondes.setText(msg);
    }

    /**
     * Changer le message du résultat et change la visibilité des layouts
     * @param msg le message du résultat
     */
    public void changerMSGResultat(String msg){
        txtResultat.setVisibility(View.VISIBLE);
        txtResultat.setText(msg);
        layoutResultats.setVisibility(View.INVISIBLE);
        txtRondes.setVisibility(View.INVISIBLE);
    }
}
