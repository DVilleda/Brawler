package com.example.brawler.DAO;

import android.util.JsonReader;
import android.util.Log;

import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.domaine.intéracteur.SourceUtilisateur;
import com.example.brawler.domaine.intéracteur.SourceUtilisateurs;
import com.example.brawler.domaine.intéracteur.UtilisateursException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Source;

public class SourceUtilisateursApi implements SourceUtilisateurs {

    public class SourceUtilisateursApiException extends UtilisateursException {
        public SourceUtilisateursApiException (int noEreur) { super("Erreur no: " +noEreur);}
    }

    private URL url;
    private URL urlUnUtilisateur;
    private String urlUtilisateur = "http://52.3.68.3/utilisateur/";
    private String clé;
    private String cléBearer;

    public SourceUtilisateursApi(String clé){
        this.clé = clé;
        this.cléBearer = "Bearer " + clé;
    }

    @Override
    public List<Utilisateur> getNouvelleUtilisateurParNiveau(Niveau niveau) throws UtilisateursException {
        List<Utilisateur> utilisateursRecue = null;

        try {
            url = new URL(urlUtilisateur + "/niveau/" + niveau.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        utilisateursRecue = lancerConnexion();

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

        utilisateursRecue = lancerConnexion();

        return utilisateursRecue;
    }

    private List<Utilisateur> lancerConnexion() throws UtilisateursException {
        List<Utilisateur> utilisateursRecue = null;

        try{
            HttpURLConnection connexion =
                    (HttpURLConnection)url.openConnection();
            connexion.setRequestProperty("Authorization", cléBearer);
            if(connexion.getResponseCode()==200){
                utilisateursRecue = décoderJson(connexion.getInputStream());
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

    private List<Utilisateur> décoderJson ( InputStream utilisateursEncoder) throws IOException, UtilisateursException {
        InputStreamReader responseBodyReader =
                new InputStreamReader(utilisateursEncoder, "UTF-8");
        List<Utilisateur> utilisateursArrayList= null;

        JsonReader jsonReader = new JsonReader(responseBodyReader);
        jsonReader.beginObject();

        while(jsonReader.hasNext()) {
            String key = jsonReader.nextName();
            if(key.equals("utilisateurs")){
                utilisateursArrayList = commencerDécoderUtilasteur(jsonReader);
            } else if(key.equals("réponse")) {
            } else {
                jsonReader.skipValue();
            }

        }
        return utilisateursArrayList;
    }

    private List<Utilisateur> commencerDécoderUtilasteur(JsonReader jsonReader) throws IOException, UtilisateursException {
        List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();

        jsonReader.beginArray();
        while (jsonReader.hasNext()){
            utilisateurs.add(lireUtilisateur(jsonReader));
        }
        jsonReader.endArray();
        return utilisateurs;
    }

    private Utilisateur lireUtilisateur(JsonReader jsonReader) throws IOException, UtilisateursException {
        int id = -1;
        Utilisateur utilisateur = null;
        jsonReader.beginObject();
        while(jsonReader.hasNext()) {
            String key = jsonReader.nextName();
            if(key.equals("id")){
                id = jsonReader.nextInt();
                SourceUtilisateurApi nouvelleSource = new SourceUtilisateurApi(clé);
                utilisateur = nouvelleSource.getUtilisateurParId(id);
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return  utilisateur;

    }

}