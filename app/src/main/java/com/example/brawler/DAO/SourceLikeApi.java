package com.example.brawler.DAO;

import android.util.JsonReader;

import com.example.brawler.domaine.intéracteur.SourceLike;
import com.example.brawler.domaine.intéracteur.UtilisateursException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SourceLikeApi implements SourceLike {

    public class SourceLikeApiException extends UtilisateursException {
        public SourceLikeApiException (int noEreur) { super("Erreur no: " +noEreur);}
    }

    private URL url;
    private String urlLike = "http://52.3.68.3/confirmerLike/";
    private String clé;
    private String cléBearer;

    public SourceLikeApi(String clé){
        this.clé = clé;
        this.cléBearer = "Bearer " + clé;
    }

    @Override
    public boolean confirmerLike(int id) throws UtilisateursException {
        Boolean utilisateurAjoutéContact = null;
        try {
            url = new URL(urlLike + id);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        utilisateurAjoutéContact = lancerConnexion();

        return utilisateurAjoutéContact;
    }

    private boolean lancerConnexion() throws UtilisateursException {
        Boolean utilisateurAjoutéContact = null;
        try {
            HttpURLConnection connexion =
                    (HttpURLConnection) url.openConnection();
            connexion.setRequestProperty("Authorization", cléBearer);

            if (connexion.getResponseCode() == 200) {
                utilisateurAjoutéContact = décoderJson(connexion.getInputStream());
            } else {
                throw new SourceLikeApi.SourceLikeApiException(connexion.getResponseCode());
            }
        } catch (IOException e){
            throw new UtilisateursException( e );
        }
        return utilisateurAjoutéContact;
    }

    private boolean décoderJson(InputStream requêteEncoder) throws IOException {
        Boolean utilisateurAjoutéContact = null;
        InputStreamReader responseBodyReader =
                new InputStreamReader(requêteEncoder, "UTF-8");

        JsonReader jsonReader = new JsonReader(responseBodyReader);
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String key = jsonReader.nextName();
            if(key .equals("Réponse")) {
                utilisateurAjoutéContact = jsonReader.nextBoolean();
            } else {
                jsonReader.skipValue();
            }

        }

        return utilisateurAjoutéContact;
    }
}
