package com.example.brawler.DAO;

import android.util.JsonReader;

import com.example.brawler.domaine.entité.Message;
import com.example.brawler.domaine.entité.Partie;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.domaine.intéracteur.MessageException;
import com.example.brawler.domaine.intéracteur.PartieException;
import com.example.brawler.domaine.intéracteur.SourceParties;
import com.example.brawler.domaine.intéracteur.UtilisateursException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SourcePartiesApi implements SourceParties {

    public class SourcePartieApiException extends PartieException {
        public SourcePartieApiException(int noEreur) {
            super("Erreur no: " + noEreur);
        }
        public SourcePartieApiException(Exception e){
            super("Erreur: " + e);
        }
    }

    private URL url;
    private String urlParties = "http://52.3.68.3/demandePartie";
    private String clé;
    private String cléBearer;
    
    public SourcePartiesApi (String clé) {
        this.clé = clé;
        this.cléBearer = "Bearer " + clé;
    }
    
    @Override
    public List<Partie> getDemandeParties() throws SourcePartieApiException {
        try {
            url = new URL(urlParties);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return lancerConnexionObtenirParties();
    }

    private List<Partie> lancerConnexionObtenirParties() throws SourcePartieApiException {
        List<Partie> parties = null;

        try{
            HttpURLConnection connexion =
                    (HttpURLConnection)url.openConnection();
            connexion.setRequestProperty("Authorization", cléBearer);
            if(connexion.getResponseCode()==200){
                parties = décoderJSON(connexion.getInputStream());
            }
            else{
                throw new SourcePartiesApi.SourcePartieApiException( connexion.getResponseCode() );
            }
        } catch(IOException | UtilisateursException e){
            throw new SourcePartiesApi.SourcePartieApiException((Exception) e);
        }

        return parties;
    }

    private List<Partie> décoderJSON(InputStream inputStream) throws IOException, UtilisateursException {
        List<Partie> parties = new ArrayList<>();
        InputStreamReader responseBodyReader =
                new InputStreamReader(inputStream, "UTF-8");

        JsonReader jsonReader = new JsonReader(responseBodyReader);
        jsonReader.beginObject();

        while(jsonReader.hasNext()) {
            String key = jsonReader.nextName();
            if(key.equals("demande")){
                jsonReader.beginArray();
                while (jsonReader.hasNext()){
                    parties.add(décoderDemandePartie(jsonReader));
                }
                jsonReader.endArray();
            } else {
                jsonReader.skipValue();
            }

        }
        return parties;
    }

    private Partie décoderDemandePartie(JsonReader jsonReader) throws IOException, UtilisateursException {
        Partie partie = null;
        int id = -1;
        Utilisateur adversaire = null;

        jsonReader.beginObject();
        while (jsonReader.hasNext()){
            String key = jsonReader.nextName();
            if(key.equals("idEnvoyeur")){
                SourceUtilisateurApi sourceUtilisateur = new SourceUtilisateurApi(clé);
                adversaire = sourceUtilisateur.getUtilisateurParId(jsonReader.nextInt(), false);
            }else if (key.equals("id")) {
                id = jsonReader.nextInt();
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();

        partie = new Partie();
        partie.setAdversaire(adversaire);
        partie.setId(id);
        return partie;
    }
}
