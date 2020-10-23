package com.example.brawler.domaine.intéracteur;

public class MessageException extends Throwable {
    public MessageException(Exception e) {super( e );}
    public MessageException(String message) {super( message );}
}
