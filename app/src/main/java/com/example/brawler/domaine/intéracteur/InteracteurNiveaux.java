package com.example.brawler.domaine.intéracteur;

import com.example.brawler.DAO.SourceUtilisateursApi;
import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Statistique;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InteracteurNiveaux {

    private static InteracteurNiveaux instance;
    private SourceUtilisateursApi source;

    public static InteracteurNiveaux getInstance() {
        if (instance == null)
            instance =  new InteracteurNiveaux();
        return  instance;
    }

    private InteracteurNiveaux() {}

    /**
     * Obtenir toutes les parties du joueur actuellement connecté qu'elles soient finies ou non
     * @param clé la clé d'authentication
     * @return un objet JSONObjet de toutes les parties du joueur actuellement connecté
     */
    public JSONObject getPartiesEnJson(String clé){
        source = new SourceUtilisateursApi(clé);
        return source.getParties();
    }

    /**
     * Construit des Statistiques a partir des parties du joueur actuellement connetcé
     * @param clé la clé d'authentication
     * @return un objet Statistique
     */
    public Statistique ConstruireStats(String clé){
        Statistique userStats = new Statistique (0,0);

        JSONObject partiesEnJson = getPartiesEnJson(clé);
        try {
            JSONArray toutesLesParties = partiesEnJson.getJSONArray("parties");
            for (int i=0; i < toutesLesParties.length(); i++) {
                JSONObject unePartie = toutesLesParties.getJSONObject(i);
                Boolean gagnée = Boolean.parseBoolean(unePartie.getString("Gagné"));
                if (gagnée){
                    userStats.ajouterVictoire();
                }
                else if (!gagnée){
                    userStats.ajouterPerdu();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userStats;
    }

    /**
     * Pour obtenir le niveu de l'utilisateur connecté, basé sur ses statistiques
     * @param clé
     * @return Objet de tyoe niveau
     */
    public Niveau getNiveu(String clé){
        Statistique stats = ConstruireStats(clé);
        int nbTotal = stats.getPartiesAuTotal();
        int nbGagnées = stats.getNombreVictoire();
        if(nbTotal == 0)
            nbTotal = 1;
        double pourcentageGagnées = (100*nbGagnées)/nbTotal;

        if (pourcentageGagnées <= 20){
            return Niveau.DÉBUTANT;
        }
        else if (pourcentageGagnées > 20 && pourcentageGagnées <= 40 ){
            return Niveau.INTERMÉDIAIRE;
        }
        else if (pourcentageGagnées > 40 && pourcentageGagnées <=70 ){
            return Niveau.EXPERT;
        }
        else if (pourcentageGagnées >= 90){
            return Niveau.LÉGENDAIRE;
        }
        else {
            return Niveau.DÉBUTANT;
        }
    }
}
