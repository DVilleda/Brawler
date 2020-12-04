package com.example.brawler.domaine.entité;

import java.util.Date;

public class Message {

    int id;
    String texte;
    Utilisateur utilisateur;
    Date temps;
    Boolean lue;


    /**
     *
     * @param id
     * @param texte
     * @param utilisateur
     * @param temps
     * @param lue
     */
    public Message(int id, String texte, Utilisateur utilisateur, Date temps, Boolean lue) {
        this.texte = texte;
        this.utilisateur = utilisateur;
        this.temps = temps;
        this.lue = lue;
        this.id = id;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getTexte() {
        return texte;
    }

    /**
     *
     * @param texte
     */
    public void setTexte(String texte) {
        this.texte = texte;
    }

    /**
     *
     * @return
     */
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    /**
     *
     * @param utilisateur
     */
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    /**
     *
     * @return
     */
    public Date getTemps() {
        return temps;
    }

    /**
     *
     * @param temps
     */
    public void setTemps(Date temps) {
        this.temps = temps;
    }

    /**
     *
     * @return
     */
    public Boolean getLue() {
        return lue;
    }

    /**
     * 
     * @param lue
     */
    public void setLue(Boolean lue) {
        this.lue = lue;
    }
}
