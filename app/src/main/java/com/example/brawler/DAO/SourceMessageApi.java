package com.example.brawler.DAO;

import android.util.JsonReader;
import android.util.Log;

import com.example.brawler.domaine.entité.Message;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.domaine.intéracteur.MessageException;
import com.example.brawler.domaine.intéracteur.SourceMessage;
import com.example.brawler.domaine.intéracteur.UtilisateursException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

public class SourceMessageApi implements SourceMessage {

    public class SourceMessageApiException extends MessageException {
        public SourceMessageApiException(int noEreur) {
            super("Erreur no: " + noEreur);
        }
    }

    private URL url;
    private String urlMessage = "http://52.3.68.3/messageContact/";
    private String clé;
    private String cléBearer;

    public  SourceMessageApi (String clé){
        clé = clé;
        cléBearer = "Bearer " + clé;
    }

    @Override
    public List<Message> getMessagesparUtilisateurs() throws MessageException {
        try {
            url = new URL(urlMessage);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return lancerConnexionMessage();
    }

    @Override
    public void envoyerMessage(int idUtilisateur, String message) throws MessageException {

    }

    private List<Message> lancerConnexionMessage() throws MessageException {
        List<Message> messages = null;

        try{
            Log.d("URL", String.valueOf(url));
            HttpURLConnection connexion =
                    (HttpURLConnection)url.openConnection();
            connexion.setRequestProperty("Authorization", cléBearer);
            if(connexion.getResponseCode()==200){
                messages = décoderJSON(connexion.getInputStream());
            }
            else{
                throw new SourceMessageApi.SourceMessageApiException( connexion.getResponseCode() );
            }
        }
        catch(IOException e){
            throw new MessageException( e );
        }

        return messages;
    }

    private List<Message> décoderJSON(InputStream messagesEncoder) throws MessageException, IOException {
        List<Message> messages = null;
        InputStreamReader responseBodyReader =
                new InputStreamReader(messagesEncoder, "UTF-8");

        JsonReader jsonReader = new JsonReader(responseBodyReader);
        jsonReader.beginObject();

        while(jsonReader.hasNext()) {
            String key = jsonReader.nextName();
            Log.d("clé:", key);

            if(key.equals("message")){
                jsonReader.beginArray();
                while (jsonReader.hasNext()){
                    messages.add(décoderMessage((jsonReader)));
                }
                jsonReader.endArray();
            } else {
                jsonReader.skipValue();
            }

        }
        return messages;
    }

    private  Message décoderMessage(JsonReader jsonReader) throws IOException {
        Message messages = null;
        Utilisateur utilisateur;
        String texte;
        Date temps;

        jsonReader.beginObject();
        while (jsonReader.hasNext()){

        }

        return messages;
    }
}
