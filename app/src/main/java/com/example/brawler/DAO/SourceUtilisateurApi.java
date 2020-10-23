package com.example.brawler.DAO;

import android.util.JsonReader;
import android.util.Log;

import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.domaine.intéracteur.UtilisateursException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SourceUtilisateurApi {

    public class SourceUtilisateursApiException extends UtilisateursException {
        public SourceUtilisateursApiException (int noEreur) { super("Erreur no: " +noEreur);}
    }

    private URL url;
    private URL urlUnUtilisateur;
    private String urlUtilisateur = "http://52.3.68.3/utilisateur/";
    private  String clé;

    public SourceUtilisateurApi(String clé){
        this.clé = "Bearer " + clé;
    }

    public Utilisateur getUtilisateurParId (int id) throws UtilisateursException {


        Utilisateur utilisateur = null;
        try {
            urlUnUtilisateur = new URL(urlUtilisateur + String.valueOf(id));
        } catch (MalformedURLException e) {
            //try/catch obligatoire pour satisfaire le compilateur.
        }

        try{
            utilisateur = lancerConnexion();
        }
        catch(IOException e) {
            throw new UtilisateursException(e);
        }
        return utilisateur;

    }

    private Utilisateur lancerConnexion() throws IOException {
        Utilisateur utilisateur= null;
        HttpURLConnection connexion =
                (HttpURLConnection)urlUnUtilisateur.openConnection();
        connexion.setRequestProperty("Authorization", clé);

        if(connexion.getResponseCode()==200){
            utilisateur = décoderUtilisateur(connexion.getInputStream());
        }
        return utilisateur;
    }

    private Utilisateur décoderUtilisateur (InputStream utilisateurEncoder) throws IOException {
        InputStreamReader responseBodyReader =
                new InputStreamReader(utilisateurEncoder, "UTF-8");
        Utilisateur utilisateur = null;

        JsonReader jsonReader = new JsonReader(responseBodyReader);
        jsonReader.beginObject();

        while(jsonReader.hasNext()) {
            String key = jsonReader.nextName();
            if(key.equals("data")){
                utilisateur =  lireUtilisateur(jsonReader);
            } else {
                jsonReader.skipValue();
            }
        }
        return utilisateur;
    }

    private Utilisateur lireUtilisateur (JsonReader jsonReader) throws IOException {
        Utilisateur utilisateur = null;

        int id = -1;
        String nom = "";
        Niveau niveau = null;
        String location = "";
        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            String key = jsonReader.nextName();

            if (key.equals("prénom")) {
                nom = jsonReader.nextString();

            } else if (key.equals("niveau")) {
                niveau = stringVersNiveau(jsonReader.nextString());
            } else if (key.equals("location")) {
                location = jsonReader.nextString();
            } else if (key.equals("id")) {
                id = jsonReader.nextInt();
            } else {
                jsonReader.skipValue();
            }
        }
        utilisateur = new Utilisateur(id, nom, niveau, location);
        return utilisateur;
    }

    private Niveau stringVersNiveau(String niveau) {
        Niveau unNiveau = null;

        if(niveau.equals("DÉBUTANT")){
            unNiveau = Niveau.DÉBUTANT;
        } else if (niveau.equals("INTERMÉDIAIRE")){
            unNiveau = Niveau.INTERMÉDIAIRE;
        } else if (niveau.equals("EXPERT")){
            unNiveau = Niveau.EXPERT;
        } else if (niveau.equals("LÉGENDAIRE")){
            unNiveau = Niveau.LÉGENDAIRE;
        }

        return  unNiveau;
    }
}
