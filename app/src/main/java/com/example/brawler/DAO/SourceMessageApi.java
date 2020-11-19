package com.example.brawler.DAO;

import android.net.Uri;
import android.util.JsonReader;
import android.util.Log;

import com.example.brawler.domaine.entité.Message;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.domaine.intéracteur.MessageException;
import com.example.brawler.domaine.intéracteur.SourceMessage;
import com.example.brawler.domaine.intéracteur.UtilisateursException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SourceMessageApi implements SourceMessage {

    public class SourceMessageApiException extends MessageException {
        public SourceMessageApiException(int noEreur) {
            super("Erreur no: " + noEreur);
        }
    }

    private URL url;
    private String urlMessage = "http://52.3.68.3/messageContact/";
    private String urlEnvoyerMessage = "http://52.3.68.3/envoyerMessages/";
    private String clé;
    private String cléBearer;

    public  SourceMessageApi (String clé){
        this.clé = clé;
        this.cléBearer = "Bearer " + clé;
    }

    @Override
    public List<Message> getMessagesparUtilisateurs(int idUtilisateur) throws MessageException, UtilisateursException {
        try {
            url = new URL(urlMessage + idUtilisateur);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return lancerConnexionRecevoirMessage();
    }

    @Override
    public List<Message> getMessages() throws MessageException, UtilisateursException {
        try {
            url = new URL(urlMessage);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return lancerConnexionRecevoirMessage();
    }

    @Override
    public List<Message> getMessageÀNotifier() throws MessageException, UtilisateursException {
        try {
            url = new URL(urlMessage + "/ANotifier");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return lancerConnexionRecevoirMessage();
    }

    @Override
    public void marquerNotifier(int idMessage) throws MessageException, UtilisateursException {
        try {
            url = new URL(urlMessage + "/marquerNotifier/" + idMessage);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        lancerConnexionMarquerNotifier();
    }

    @Override
    public void envoyerMessage(int idUtilisateur, String message) throws MessageException {
        try {
            url = new URL(urlEnvoyerMessage + idUtilisateur);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        lancerConnexionEnvoyerMessage(message);
    }

    private List<Message> lancerConnexionRecevoirMessage() throws MessageException, UtilisateursException {
        List<Message> messages = null;

        try{
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

    private void lancerConnexionEnvoyerMessage(String message) throws MessageException {
        try{
            HttpURLConnection connexion =
                    (HttpURLConnection)url.openConnection();
            connexion.setRequestProperty("Authorization", cléBearer);
            connexion.setReadTimeout(10000);
            connexion.setConnectTimeout(15000);
            connexion.setRequestMethod("POST");
            connexion.setDoInput(true);
            connexion.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("message", message);
            String query = builder.build().getEncodedQuery();

            OutputStream os = connexion.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            connexion.connect();


            if(connexion.getResponseCode()==200){
            }
            else{
                throw new SourceMessageApi.SourceMessageApiException( connexion.getResponseCode() );
            }
        }

        catch(IOException e){
            throw new MessageException( e );
        }
    }

    private boolean lancerConnexionMarquerNotifier() throws MessageException {
        try {
            HttpURLConnection connexion =
                    (HttpURLConnection) url.openConnection();
            connexion.setRequestProperty("Authorization", cléBearer);
            if (connexion.getResponseCode() == 200) {
                return true;
            } else {
                throw new SourceMessageApi.SourceMessageApiException(connexion.getResponseCode());
            }
        }catch(IOException e){
                throw new MessageException( e );
        }
    }

    private List<Message> décoderJSON(InputStream messagesEncoder) throws MessageException, IOException, UtilisateursException {
        List<Message> messages = new ArrayList<>();
        InputStreamReader responseBodyReader =
                new InputStreamReader(messagesEncoder, "UTF-8");

        JsonReader jsonReader = new JsonReader(responseBodyReader);
        jsonReader.beginObject();

        while(jsonReader.hasNext()) {
            String key = jsonReader.nextName();
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

    private  Message décoderMessage(JsonReader jsonReader) throws IOException, UtilisateursException {
        Message message = null;
        int id = -1;
        Utilisateur utilisateur = null;
        String texte = "";
        Date temps = null;
        Boolean lue = false;

        jsonReader.beginObject();
        while (jsonReader.hasNext()){
            String key = jsonReader.nextName();
            if(key.equals("idEnvoyeur")){
                SourceUtilisateurApi sourceUtilisateur = new SourceUtilisateurApi(clé);
                utilisateur = sourceUtilisateur.getUtilisateurParId(jsonReader.nextInt());
            } else if (key.equals("message")){
                texte = jsonReader.nextString();
            } else if (key.equals("temps")) {
                temps = décoderTemps(jsonReader.nextString());
            } else if (key.equals("lue")) {
                int i = jsonReader.nextInt();
                if(i == 0)
                    lue = false;
                else
                    lue =true;
            } else if (key.equals("id")) {
                id = jsonReader.nextInt();
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();

        Log.d("id", String.valueOf(id));
        message = new Message(id, texte, utilisateur, temps, lue);
        return message;
    }

    //exemple de date: Thu, 19 Nov 2020 03:49:50
    private Date décoderTemps(String temps){
        Date date = null;

        String[] dateATraiter = null;
        String[] tempsATraiter = null;
        String[] partie = temps.split(" ");
        dateATraiter = partie[0].split("-");
        tempsATraiter = partie[1].split(":");

        if(dateATraiter != null && tempsATraiter != null) {
            int année = Integer.parseInt(dateATraiter[0]);
            int mois = Integer.parseInt(dateATraiter[1]);
            int journée = Integer.parseInt(dateATraiter[2]);
            int heure = Integer.parseInt(tempsATraiter[0]);
            int minute = Integer.parseInt(tempsATraiter[1]);
            date = new Date(année, mois, journée, heure, minute);
        }

        return  date;
    }
}
