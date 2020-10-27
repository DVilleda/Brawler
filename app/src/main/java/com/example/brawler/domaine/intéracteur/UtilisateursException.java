package com.example.brawler.domaine.intéracteur;

public class UtilisateursException extends  Throwable {
    public UtilisateursException(Exception e) {super( e );}
    public UtilisateursException(String message) {super( message );}
}
