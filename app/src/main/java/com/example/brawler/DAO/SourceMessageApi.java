package com.example.brawler.DAO;

import android.util.Log;

import com.example.brawler.domaine.entité.Message;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.domaine.intéracteur.MessageException;
import com.example.brawler.domaine.intéracteur.SourceMessage;
import com.example.brawler.domaine.intéracteur.UtilisateursException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
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

    @Override
    public List<Message> getMessagesparUtilisateurs() throws MessageException {
        List<Message> messages = null;


        return messages;
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
            connexion.setRequestProperty("Authorization", clé);
            if(connexion.getResponseCode()==200){

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
}
