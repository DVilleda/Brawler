package com.example.brawler.présentation.vue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.brawler.présentation.présenteur.PrésenteurRechercheMatch;

public class VueRechercheMatch extends Fragment {

    private PrésenteurRechercheMatch présenteur;

    public void setPrésenteur(PrésenteurRechercheMatch présenteur) {
        this.présenteur = présenteur;
    }

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {

        View vue = null;
        
        return vue;
    }
    
}
