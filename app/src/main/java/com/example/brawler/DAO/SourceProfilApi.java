package com.example.brawler.DAO;

import android.net.Uri;
import android.util.Base64;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.util.Pair;

import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Utilisateur;
import com.example.brawler.domaine.intéracteur.SourceUtilisateur;
import com.example.brawler.domaine.intéracteur.UtilisateursException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SourceProfilApi implements SourceUtilisateur{



    public class SourceProfilApiException extends UtilisateursException{
        public SourceProfilApiException (int numErreur){super("Erreur num: "+numErreur);}
    }
    //Parametres
    private URL url;
    private String urlBaseUtilisateur = "http://52.3.68.3/utilisateur";
    private String urlBaseModifier = "http://52.3.68.3/modifierInfo";
    private String token;

    public SourceProfilApi(String token){
        this.token = token;
    }

    @Override
    public Utilisateur getUtilisateur() throws UtilisateursException {
        Utilisateur utilisateur = null;
        try {
            url = new URL(urlBaseUtilisateur);
        } catch (MalformedURLException e) {
            //try/catch obligatoire pour satisfaire le compilateur.
        }

        try{
            HttpURLConnection connexion =
                    (HttpURLConnection)url.openConnection();
            connexion.setRequestProperty("Authorization","Bearer "+token);
            if(connexion.getResponseCode()==200){
                utilisateur = décoderUtilisateur(connexion.getInputStream());
            }
            else{
                throw new SourceProfilApi.SourceProfilApiException(connexion.getResponseCode());
            }
        }
        catch(IOException e) {
            throw new UtilisateursException(e);
        }

        return utilisateur;
    }

    @Override
    public Utilisateur getUtilisateurParId(int id, boolean bool) throws UtilisateursException {
        return null;
    }

    @Override
    public void setUtilisateur(Utilisateur utilisateur) throws UtilisateursException {
        try {
            url = new URL(urlBaseModifier);
        } catch (MalformedURLException e) {
            //try/catch obligatoire pour satisfaire le compilateur.
        }

        try{
            HttpURLConnection connexion =
                    (HttpURLConnection)url.openConnection();
            connexion.setRequestProperty("Authorization","Bearer "+token);
            connexion.setRequestMethod("POST");
            connexion.setDoOutput(true);
            connexion.setDoInput(true);

            //Préparer la photo pour la requête
            String photoString = null;
            try
            {
                System.gc();
                photoString = Base64.encodeToString(utilisateur.getPhoto(),Base64.DEFAULT);
            }catch (Exception e) {
                e.printStackTrace();
            }catch (OutOfMemoryError e){
                Log.e("Conversion Image","Out of memory error");
            }

            //Params de la requete
            List<Pair<String,String>> params = new ArrayList<>();
            params.add(new Pair<>("email", utilisateur.getEmail()));
            params.add(new Pair<>("prénom",utilisateur.getNom()));
            params.add(new Pair<>("description",utilisateur.getDescription()));
            params.add(new Pair<>("niveau",utilisateur.getNiveau().toString()));
            params.add(new Pair<>("location",utilisateur.getLocation()));
            params.add(new Pair<>("photo",photoString));

            OutputStream os = connexion.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, StandardCharsets.UTF_8));
            writer.write(String.valueOf(construireQueryPOST(params)));
            writer.flush();
            writer.close();
            os.close();

            connexion.connect();
            if(connexion.getResponseCode()==200){
                //utilisateur = décoderUtilisateur(connexion.getInputStream());
            }
            else{
                throw new SourceProfilApi.SourceProfilApiException(connexion.getResponseCode());
            }
        }
        catch(IOException e) {
            throw new UtilisateursException(e);
        }
    }

    @Override
    public Utilisateur getUtilisateurActuel() throws UtilisateursException {
        return null;
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
        String email="";
        String description="";
        byte[] photoProfil=null;

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
            } else if(key.equals("email")){
                email = jsonReader.nextString();
            }else if(key.equals("description")){
                description = jsonReader.nextString();
            }else if(key.equals("image")){
                if(jsonReader.peek() != JsonToken.NULL){
                    photoProfil = Base64.decode(jsonReader.nextString(),Base64.DEFAULT);
                } else {
                    jsonReader.nextNull();
                }
            }
            else
                {
                jsonReader.skipValue();
            }
        }
        return new Utilisateur(id, nom, niveau, location,email,description,photoProfil);
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

    public String construireQueryPOST(List<Pair<String,String>> pairList)throws UnsupportedEncodingException
    {
        StringBuilder resultat = new StringBuilder();
        boolean first = true;

        for (Pair<String,String> stringPair: pairList)
        {
            if(first)
                first = false;
            else
                resultat.append("&");

            resultat.append(URLEncoder.encode(stringPair.first,"UTF-8"));
            resultat.append("=");
            resultat.append(URLEncoder.encode(stringPair.second,"UTF-8"));
        }
        return resultat.toString();
    }
}
