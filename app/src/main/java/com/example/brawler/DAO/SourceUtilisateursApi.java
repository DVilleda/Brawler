package com.example.brawler.DAO;

import android.util.JsonReader;

import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.domaine.intéracteur.SourceUtilisateurs;
import com.example.brawler.domaine.intéracteur.UtilisateursException;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SourceUtilisateursApi implements SourceUtilisateurs {

    public class SourceUtilisateursApiException extends UtilisateursException {
        public SourceUtilisateursApiException (int noEreur) { super("Erreur no: " +noEreur);}
    }

    private URL url;
    private String urlUtilisateur = "52.3.68.3/utilisateur/";

    @Override
    public List<Utilisateur> getNouvelleUtilisateurParNiveau(String location, Niveau niveau) {
        List<Utilisateur> utilisateursRecue = null;

        return utilisateursRecue;
    }

    @Override
    public List<Utilisateur> getUtilisateur() throws UtilisateursException {
        List<Utilisateur> utilisateursRecue = null;
        try {
            url = new URL(urlUtilisateur + "location");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try{
            HttpURLConnection connexion =
                    (HttpURLConnection)url.openConnection();

            if(connexion.getResponseCode()==200){
                utilisateursRecue = décoderUtilisateurs(connexion.getInputStream());
            }
            else{
                throw new SourceUtilisateursApi.SourceUtilisateursApiException( connexion.getResponseCode() );
            }
        }
        catch(IOException e){
            throw new UtilisateursException( e );
        }

        return utilisateursRecue;
    }

    private List<Utilisateur> décoderUtilisateurs ( InputStream utilisateursEncoder) throws IOException {
        InputStreamReader responseBodyReader =
                new InputStreamReader(utilisateursEncoder, "UTF-8");

        List<Utilisateur> utilisateursArrayList= new ArrayList<Utilisateur>();
        int id = 0;

        JsonReader jsonReader = new JsonReader(responseBodyReader);
        jsonReader.beginObject();
        new JSONArray();

        while(jsonReader.hasNext()) {
            String key = jsonReader.nextName();
            if(key.equals("id")){
                id = jsonReader.nextInt();
                utilisateursArrayList.add(décoderUtilisateur(id));
            }

        }
        return utilisateursArrayList;
    }

    private Utilisateur décoderUtilisateur ( int id)    throws MalformedURLException {
        try {
            URL urlUnUtilisateur = new URL(urlUtilisateur + String.valueOf(id));
        } catch (MalformedURLException e) {
            //try/catch obligatoire pour satisfaire le compilateur.
        }
        String nom="";
        Niveau niveau;
        String location="";
    }


}