package com.example.brawler.DAO;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Pair;

import com.example.brawler.domaine.entité.Mouvement;
import com.example.brawler.domaine.entité.Partie;
import com.example.brawler.domaine.intéracteur.PartieException;
import com.example.brawler.domaine.intéracteur.SourceDeroulementPartie;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SourcePartieJoueurApi implements SourceDeroulementPartie {

    public class SourcePartieApiException extends PartieException {
        public SourcePartieApiException (int numErreur){super("Erreur num: "+numErreur);}
    }

    private URL url;
    private final String urlPartie="http://52.3.68.3/partie/";
    private final String urlEnvoyerMouvement="http://52.3.68.3/envoyerResultat";
    private String token;

    public SourcePartieJoueurApi(String leToken){
        this.token = leToken;
    }

    @Override
    public Partie getPartieParID(int idPartie) throws PartieException{
        try{
            url = new URL(urlPartie+idPartie);
        }catch (MalformedURLException e){}

        Partie partie;
        try{
            HttpURLConnection connection =
                    (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("Authorization","Bearer "+token);
            if(connection.getResponseCode()==200){
                partie = decoderPartie(connection.getInputStream());
                return partie;
            }else {
                throw new SourcePartieJoueurApi.SourcePartieApiException(connection.getResponseCode());
            }
        } catch (IOException e) {
            throw new PartieException(e);
        }
    }

    @Override
    public void EnvoyerMouvement(int idPartie, int idAdversaire, String mouvement) throws PartieException {
        try {
            url = new URL(urlEnvoyerMouvement);
        } catch (MalformedURLException e) {
            //try/catch obligatoire pour satisfaire le compilateur.
        }

        try {
            HttpURLConnection connexion =
                    (HttpURLConnection) url.openConnection();
            connexion.setRequestProperty("Authorization", "Bearer " + token);
            connexion.setRequestMethod("POST");
            connexion.setDoOutput(true);
            connexion.setDoInput(true);

            //Params de la requete
            List<Pair<String,String>> params = new ArrayList<>();
            params.add(new Pair<String, String>("idAdversaire",Integer.toString(idAdversaire)));
            params.add(new Pair<String, String>("idPartie",Integer.toString(idPartie)));
            params.add(new Pair<String, String>("mouvement",mouvement));

            OutputStream os = connexion.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, StandardCharsets.UTF_8));
            writer.write(String.valueOf(construireQueryPOST(params)));
            writer.flush();
            writer.close();
            os.close();

            connexion.connect();
            if(connexion.getResponseCode()==200){
                //partie = decoderPartie(connection.getInputStream());
            }
            else{
                throw new SourcePartieJoueurApi.SourcePartieApiException(connexion.getResponseCode());
            }
        } catch (IOException e) {
            throw new PartieException(e);
        }
    }

    @Override
    public ArrayList RecevoirMouvements(int idPartie) {
        return null;
    }

    private Partie decoderPartie (InputStream partieEncoder) throws IOException{
        InputStreamReader responeBodyReader =
                new InputStreamReader(partieEncoder,"UTF-8");
        Partie partie = new Partie(-1,false,-1,false,null);

        JsonReader jsonReader = new JsonReader(responeBodyReader);
        jsonReader.beginObject();

        while(jsonReader.hasNext()){
            String key = jsonReader.nextName();
            if(key.equals("partie")){
                partie = lirePartie(jsonReader);
                return partie;
            } else {
                jsonReader.skipValue();
            }
        }
        return partie;
    }

    private Partie lirePartie (JsonReader jsonReader) throws IOException{
        int idPartie = -1;
        int idAdv = -1;
        boolean enCours = false;
        boolean victoire = false;
        List<Mouvement> mouvementList=null;

        jsonReader.beginObject();

        while(jsonReader.hasNext()){
            String key = jsonReader.nextName();

            if(key.equals("idPartie")){
                idPartie = jsonReader.nextInt();
            } else if(key.equals("EnCour")){
                enCours = jsonReader.nextBoolean();
            }else if(key.equals("adversaire")){
                idAdv = jsonReader.nextInt();
            }else if(key.equals("mouvements")){
                if(jsonReader.peek() != JsonToken.NULL){
                    mouvementList = lireMouvements(jsonReader);
                }else{
                    jsonReader.nextNull();
                }
            }else if (key.equals("Gagné")){
                victoire = jsonReader.nextBoolean();
            }else{
                jsonReader.skipValue();
            }
        }
        return new Partie(idPartie,enCours,idAdv,victoire,mouvementList);
    }

    private List<Mouvement> lireMouvements(JsonReader jsonReader) throws  IOException{
        List<Mouvement> mouvementList = new ArrayList<>();

        jsonReader.beginArray();
        while(jsonReader.hasNext()){
            mouvementList.add(lireMouvement(jsonReader));
        }
        jsonReader.endArray();

        return mouvementList;
    }

    private Mouvement lireMouvement(JsonReader reader) throws  IOException{
        String mouvementAdv = "";
        String mouvementJoueur = "";
        int tour = -1;

        reader.beginObject();
        while(reader.hasNext()){
            String key = reader.nextName();
            if(key.equals("mouvementAdversaire")){
                mouvementAdv = reader.nextString();
            }else if(key.equals("mouvementJoueur")){
                mouvementJoueur = reader.nextString();
            }else if(key.equals("tour")){
                tour = reader.nextInt();
            }else{
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Mouvement(tour,mouvementAdv,mouvementJoueur);
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
