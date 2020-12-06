package com.example.brawler.DAO;

import android.util.JsonReader;

import com.example.brawler.domaine.entité.Partie;
import com.example.brawler.domaine.entité.Utilisateur;
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
    private String urlDemandeParties = "http://52.3.68.3/demandePartie";
    private String urlRefuserDemandeParties = "http://52.3.68.3/refuserDemandePartie";
    private String urlParties = "http://52.3.68.3/partie";
    private String clé;
    private String cléBearer;

    /**
     * constructeur selon la clé de l'utilisateur vers l'api
     * @param clé
     */
    public SourcePartiesApi (String clé) {
        this.clé = clé;
        this.cléBearer = "Bearer " + clé;
    }

    /**
     * Permet la connexion a l'api et d'obtenir toutes les demandes de partie d'un joueur
     * @return
     * @throws SourcePartieApiException
     */
    @Override
    public List<Partie> getDemandeParties() throws SourcePartieApiException {
        try {
            url = new URL(urlDemandeParties);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return lancerConnexionObtenirDemandeParties();
    }

    /**
     * Permet la connexion a l'api et d'accepter ou de creer une demande de partie
     * @param idAversaire
     * @throws SourcePartieApiException
     */
    @Override
    public void envoyerDemandePartie(int idAversaire) throws SourcePartieApiException {
        try {
            url = new URL(urlDemandeParties + "/" + idAversaire);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        lancerConnexionDemandePartie();
    }

    /**
     * Permet la connexion a l'api et de refuser une demande de partie
     * @param idAversaire
     * @throws SourcePartieApiException
     */
    @Override
    public void refuserDemandePartie(int idAversaire) throws SourcePartieApiException {
        try {
            url = new URL(urlRefuserDemandeParties + "/" + idAversaire);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        lancerConnexionDemandePartie();
    }

    /**
     * retourne toute les partie du joueur actuel qui sont en cour
     * @return
     */
    @Override
    public List<Partie> getPartieEnCour() throws SourcePartieApiException {

        try {
            url = new URL(urlParties);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return lancerConnexionPartieEnCour();
    }

    /**
     * lance la connexion pour obtenir les partien en cour
     * @return
     * @throws SourcePartieApiException
     */
    private List<Partie> lancerConnexionPartieEnCour() throws SourcePartieApiException {
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

    /**
     * lance la connexion pour obtenir toutes les demadne de parties
     * @return
     * @throws SourcePartieApiException
     */
    private List<Partie> lancerConnexionObtenirDemandeParties() throws SourcePartieApiException {
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

    /**
     * lance la connexion pour répondre a une demande de partie
     * @throws SourcePartieApiException
     */
    private void lancerConnexionDemandePartie() throws SourcePartieApiException {

        try{
            HttpURLConnection connexion =
                    (HttpURLConnection)url.openConnection();
            connexion.setRequestProperty("Authorization", cléBearer);
            if(connexion.getResponseCode()==200){
            }
            else{
                throw new SourcePartiesApi.SourcePartieApiException( connexion.getResponseCode() );
            }
        } catch(IOException e){
            throw new SourcePartiesApi.SourcePartieApiException((Exception) e);
        }
    }

    /**
     * lance le décodage d'un array json de partie
     * @param inputStream
     * @return
     * @throws IOException
     * @throws UtilisateursException
     */
    private List<Partie> décoderJSON(InputStream inputStream) throws IOException, UtilisateursException {
        List<Partie> parties = new ArrayList<>();
        InputStreamReader responseBodyReader =
                new InputStreamReader(inputStream, "UTF-8");


        JsonReader jsonReader = new JsonReader(responseBodyReader);
        jsonReader.beginObject();
        while(jsonReader.hasNext()) {
            String key = jsonReader.nextName();
            if(key.equals("demandes")){
                jsonReader.beginArray();
                while (jsonReader.hasNext()){
                    parties.add(décoderDemandePartie(jsonReader));
                }
                jsonReader.endArray();
            } else if (key.equals("parties")) {
                jsonReader.beginArray();
                while (jsonReader.hasNext()){

                    Partie partie = décoderDemandePartie(jsonReader);
                    if(partie.getGagnant() == null)
                        parties.add(partie);
                }
                jsonReader.endArray();
            } else {
                jsonReader.skipValue();
            }

        }
        return parties;
    }

    /**
     * Décode une partie d'un eligne de json
     * @param jsonReader
     * @return
     * @throws IOException
     * @throws UtilisateursException
     */
    private Partie décoderDemandePartie(JsonReader jsonReader) throws IOException, UtilisateursException {
        Partie partie = null;
        int id = -1;
        boolean enCour = true;
        Utilisateur adversaire = null;
        Utilisateur gagnant = null;

        jsonReader.beginObject();
        while (jsonReader.hasNext()){
            String key = jsonReader.nextName();
            if(key.equals("adversaire")){
                SourceUtilisateurApi sourceUtilisateur = new SourceUtilisateurApi(clé);
                adversaire = sourceUtilisateur.getUtilisateurParId(jsonReader.nextInt(), false);
            }else if (key.equals("id")) {
                id = jsonReader.nextInt();
            } else if (key.equals("idPartie")) {
                id = jsonReader.nextInt();
            } else if(key.equals("EnCour")) {
                if(!jsonReader.nextBoolean())
                    enCour = false;
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();

        //Le gangnant est l'adversaire si la partie n'est pas en cour et null si en cour
        if(!enCour){
            partie.setGagnant(gagnant);
        }

        partie = new Partie();
        partie.setAdversaire(adversaire);
        partie.setId(id);
        partie.setGagnant(gagnant);
        return partie;
    }
}
