package com.example.brawler.domaine.intéracteur;

public class PartieException extends Throwable{
    public PartieException(Exception e) {super( e );}
    public PartieException(String message) {super( message );}
}
