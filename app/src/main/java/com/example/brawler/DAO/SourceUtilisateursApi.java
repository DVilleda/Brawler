package com.example.brawler.DAO;

import android.util.JsonReader;

import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.domaine.intéracteur.SourceUtilisateurs;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

public class SourceUtilisateursApi implements SourceUtilisateurs {

    private URL url;

    public SourceUtilisateursApi (URL url){
        if ( url == null ) {
            throw new IllegalArgumentException("url ne peut être null");
        }
        this.url = url;
    }

    @Override
    public List<Utilisateur> getNouvelleUtilisateurParNiveau(String location, Niveau niveau) {
        List<Utilisateur> utilisateursRecue = null;

        return utilisateursRecue;
    }

    @Override
    public List<Utilisateur> getUtilisateur(String location) {
        List<Utilisateur> utilisateursRecue = null;

        return utilisateursRecue;
    }

    private List<Utilisateur> décoderUtilisateurs ( InputStream utilisateursEncoder) throws IOException {
        InputStreamReader responseBodyReader =
                new InputStreamReader(utilisateursEncoder, "UTF-8");

        JsonReader jsonReader = new JsonReader(responseBodyReader);
        jsonReader.beginObject();


    }
}