package com.example.brawler.DAO;

import android.os.Debug;
import android.util.JsonReader;
import android.util.Log;

import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.domaine.intéracteur.SourceUtilisateurs;
import com.example.brawler.domaine.intéracteur.UtilisateursException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SourceUtilisateursApi implements SourceUtilisateurs {

    public class SourceUtilisateursApiException extends UtilisateursException {
        public SourceUtilisateursApiException (int noEreur) { super("Erreur no: " +noEreur);}
    }

    private URL url;
    private URL urlUnUtilisateur;
    private String urlUtilisateur = "http://52.3.68.3/utilisateur/";
    private  String clé;

    public SourceUtilisateursApi(String clé){
        this.clé = "Bearer " + clé;
    }

    @Override
    public List<Utilisateur> getNouvelleUtilisateurParNiveau(Niveau niveau) throws UtilisateursException {
        List<Utilisateur> utilisateursRecue = null;

        try {
            url = new URL(urlUtilisateur + niveau.toString());
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
            Log.d("URL", String.valueOf(url));
            HttpURLConnection connexion =
                    (HttpURLConnection)url.openConnection();
            connexion.setRequestProperty("Authorization", clé);
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

    private List<Utilisateur> décoderUtilisateurs ( InputStream utilisateursEncoder) throws IOException, UtilisateursException {
        InputStreamReader responseBodyReader =
                new InputStreamReader(utilisateursEncoder, "UTF-8");
        Log.d("passe:" , "DÉcoderUtilsaiteur");
        List<Utilisateur> utilisateursArrayList= null;

        JsonReader jsonReader = new JsonReader(responseBodyReader);
        jsonReader.beginObject();

        while(jsonReader.hasNext()) {
            String key = jsonReader.nextName();
            Log.d("clé:", key);
            if(key.equals("utilisateurs")){
                utilisateursArrayList = commencerDécoderutilsaiteur(jsonReader);
                Log.d("id: ", key);
            } else if(key.equals("réponse")) {
                Log.d("problème", jsonReader.nextString());
            } else {
                jsonReader.skipValue();
            }

        }
        return utilisateursArrayList;
    }

    private List<Utilisateur> commencerDécoderutilsaiteur(JsonReader jsonReader) throws IOException, UtilisateursException {
        List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
        int id = -1;
        jsonReader.beginArray();
        jsonReader.beginObject();
        while(jsonReader.hasNext()) {
            String key = jsonReader.nextName();
            Log.d("id", key );
            if(key.equals("id")){
                id = jsonReader.nextInt();
                utilisateurs.add(getUtilisateur(id));
            } else {
                jsonReader.skipValue();
            }
        }
        return  utilisateurs;

    }
    private Utilisateur getUtilisateur ( int id) throws UtilisateursException {
        Utilisateur utilisateur = null;
        try {
            urlUnUtilisateur = new URL(urlUtilisateur + String.valueOf(id));
        } catch (MalformedURLException e) {
            //try/catch obligatoire pour satisfaire le compilateur.
        }

        try{
            HttpURLConnection connexion =
                    (HttpURLConnection)urlUnUtilisateur.openConnection();
            connexion.setRequestProperty("Authorization", clé);
            if(connexion.getResponseCode()==200){
                utilisateur = décoderUtilisateur(connexion.getInputStream());
            }
            else{
                throw new SourceUtilisateursApi.SourceUtilisateursApiException( connexion.getResponseCode() );
            }
        }
        catch(IOException e) {
            throw new UtilisateursException(e);
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

        return new Utilisateur(id, nom, niveau, location);
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

    public JSONObject creerNouveauUtilisateur(String email, String mdp, String prénom, String location, String description ){

        String serviceResponseInText ="";
        JSONObject serviceResponse = new JSONObject();
        String urlParameters  =
                "email=" + email +
                "&mdp=" + mdp +
                "&prénom=" + prénom +
                "&location=" + location +
                "&description=" + description;

        try {
            url = new URL("http://52.3.68.3/inscription");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection connexion = (HttpURLConnection)url.openConnection();
            connexion.setRequestMethod("POST");
            connexion.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
            connexion.setRequestProperty( "charset", "utf-8");
            connexion.setDoOutput(true);
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int postDataLength    = postData.length;
            connexion.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));

            try( DataOutputStream wr = new DataOutputStream( connexion.getOutputStream())) {
                wr.write( postData );
            }

            int responseCode = connexion.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        connexion.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // store result on variable
                serviceResponseInText = response.toString();
                serviceResponse = new JSONObject(serviceResponseInText);


            } else {
                serviceResponse = new JSONObject("{\"r\\u00e9ponse\":\"inscription échouée\",\"statut\":\"échec\"}");
            }


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return serviceResponse;
    }

    public String Authentifier(String leEmail, String leMdp) {

        String serviceResponse = "0";
        String urlParameters  = "email=" + leEmail + "&mdp=" + leMdp;

        try {
            url = new URL("http://52.3.68.3/connexion");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection connexion = (HttpURLConnection)url.openConnection();
            connexion.setRequestMethod("POST");
            connexion.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
            connexion.setRequestProperty( "charset", "utf-8");
            connexion.setDoOutput(true);
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int postDataLength    = postData.length;
            connexion.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));

            try( DataOutputStream wr = new DataOutputStream( connexion.getOutputStream())) {
                wr.write( postData );
            }

            int responseCode = connexion.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        connexion.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // store result on variable
                serviceResponse = response.toString();
            } else {
                serviceResponse = "0";
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return serviceResponse;
    }
}