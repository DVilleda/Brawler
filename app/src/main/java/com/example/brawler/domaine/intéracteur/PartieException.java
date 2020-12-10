package com.example.brawler.domaine.intéracteur;

public class PartieException extends Throwable{
    public PartieException(Exception e) { super(e); }
    public PartieException(String msg) { super(msg); }
}
