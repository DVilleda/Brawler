package com.example.brawler.présentation.modèle;

import com.example.brawler.domaine.entité.Message;
import com.example.brawler.domaine.entité.Niveau;
import com.example.brawler.domaine.entité.Partie;
import com.example.brawler.domaine.entité.Utilisateur;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Classe de test pour les getter et setter de la classe Modèle
 */
public class ModèleTest {

    @Test
    public void testGetListUtilisateurs() {
        Modèle modèle = new Modèle();
        Utilisateur mock1 = new Utilisateur(1,"Danny", Niveau.EXPERT,"Toronto");
        Utilisateur mock2 = new Utilisateur(2,"John",Niveau.LÉGENDAIRE,"New York");
        List<Utilisateur> mock = Arrays.asList(mock1,mock2);
        modèle.setListeUtilisateurs(mock);
        List<Utilisateur> attendu = Arrays.asList(new Utilisateur(1,"Danny", Niveau.EXPERT,"Toronto"),new Utilisateur(2,"John",Niveau.LÉGENDAIRE,"New York"));

        for(int i=0;i<attendu.size();i++) {
            assertEquals(attendu.get(i).getId(), modèle.getListUtilisateurs().get(i).getId());
            assertEquals(attendu.get(i).getEmail(), modèle.getListUtilisateurs().get(i).getEmail());
        }
    }

    @Test
    public void testSetListeUtilisateurs() {
        Modèle modèle = new Modèle();
        Utilisateur mock1 = new Utilisateur(1,"Danny", Niveau.EXPERT,"Toronto");
        Utilisateur mock2 = new Utilisateur(2,"John",Niveau.LÉGENDAIRE,"New York");
        List<Utilisateur> mock = Arrays.asList(mock1,mock2);
        modèle.setListeUtilisateurs(mock);
        List<Utilisateur> attendu = Arrays.asList(new Utilisateur(1,"Danny", Niveau.EXPERT,"Toronto"),new Utilisateur(2,"John",Niveau.LÉGENDAIRE,"New York"));

        for(int i=0;i<attendu.size();i++) {
            assertEquals(attendu.get(i).getId(), modèle.getListUtilisateurs().get(i).getId());
            assertEquals(attendu.get(i).getEmail(), modèle.getListUtilisateurs().get(i).getEmail());
        }
    }

    @Test
    public void testSetListeMessage() {
        Modèle modèle = new Modèle();
        Utilisateur mockUser1 = new Utilisateur(1,"Danny", Niveau.EXPERT,"Toronto");
        Utilisateur mockUser2 = new Utilisateur(2,"John",Niveau.LÉGENDAIRE,"New York");
        Message mockMessage1 = new Message(1,"Hello",mockUser1,new Date(2020,12,5,8,0),false);
        Message mockMessage2 = new Message(1,"Hi, friend.",mockUser2,new Date(2020,12,5,8,15),false);
        List<Message> list = Arrays.asList(mockMessage1,mockMessage2);
        modèle.setListeMessage(list);
        List<Message> attendu = Arrays.asList(mockMessage1,mockMessage2);
        List<Message> mock = modèle.getMessages();

        assertEquals(attendu.size(),mock.size());
        for(int i=0;i<mock.size();i++) {
            assertEquals(attendu.get(i).getId(), modèle.getMessages().get(i).getId());
            assertEquals(attendu.get(i).getTexte(), modèle.getMessages().get(i).getTexte());
        }
    }

    @Test
    public void testGetMessages() {
        Modèle modèle = new Modèle();
        Utilisateur mockUser1 = new Utilisateur(1,"Danny", Niveau.EXPERT,"Toronto");
        Utilisateur mockUser2 = new Utilisateur(2,"John",Niveau.LÉGENDAIRE,"New York");
        Message mockMessage1 = new Message(1,"Hello",mockUser1,new Date(2020,12,5,8,0),false);
        Message mockMessage2 = new Message(1,"Hi, friend.",mockUser2,new Date(2020,12,5,8,15),false);
        List<Message> list = Arrays.asList(mockMessage1,mockMessage2);
        modèle.setListeMessage(list);
        List<Message> attendu = Arrays.asList(mockMessage1,mockMessage2);
        List<Message> mock = modèle.getMessages();

        assertEquals(attendu.size(),mock.size());
        for(int i=0;i<mock.size();i++) {
            assertEquals(attendu.get(i).getId(), modèle.getMessages().get(i).getId());
            assertEquals(attendu.get(i).getTexte(), modèle.getMessages().get(i).getTexte());
        }
    }

    @Test
    public void testGetUtilisateurActuel() {
        Modèle modèle = new Modèle();
        Utilisateur mock1 = new Utilisateur(1,"Danny", Niveau.EXPERT,"Toronto");
        Utilisateur mock2 = new Utilisateur(2,"John",Niveau.LÉGENDAIRE,"New York");
        List<Utilisateur> mock = Arrays.asList(mock1,mock2);
        modèle.setListeUtilisateurs(mock);
        List<Integer> attendu = Arrays.asList(1,2);
        List<Integer> utilisateursID = new ArrayList<>();

        for(int i=0;i<modèle.getListUtilisateurs().size();i++) {
            utilisateursID.add(modèle.getListUtilisateurs().get(i).getId());
        }
        modèle.setListUtilisateursId(utilisateursID);
        for(int i=0;i<modèle.getListUtilisateursId().size();i++){
            assertEquals(attendu.get(i),modèle.getListUtilisateursId().get(i));
            modèle.prochainUtilisateur();
        }
    }

    @Test
    public void testGetUtilisateurEnRevue() {
        Modèle modèle = new Modèle();
        Utilisateur mock1 = new Utilisateur(1,"Danny", Niveau.EXPERT,"Toronto");
        Utilisateur mock2 = new Utilisateur(2,"John",Niveau.LÉGENDAIRE,"New York");
        List<Utilisateur> mock = Arrays.asList(mock1,mock2);
        modèle.setListeUtilisateurs(mock);
        int IDattendu = 0;
        for(int i=0;i<modèle.getListUtilisateurs().size();i++) {
            assertEquals(IDattendu,modèle.getUtilisateurEnRevue());
            modèle.prochainUtilisateur();
            IDattendu++;
        }
    }

    @Test
    public void testProchainUtilisateur() {
        Modèle modèle = new Modèle();
        Utilisateur mock1 = new Utilisateur(1,"Danny", Niveau.EXPERT,"Toronto");
        Utilisateur mock2 = new Utilisateur(2,"John",Niveau.LÉGENDAIRE,"New York");
        List<Utilisateur> mock = Arrays.asList(mock1,mock2);
        modèle.setListeUtilisateurs(mock);
        int IDattendu = 1;
        assertEquals(0,modèle.getUtilisateurEnRevue());
        modèle.prochainUtilisateur();
        assertEquals(IDattendu,modèle.getUtilisateurEnRevue());
    }

    @Test
    public void testViderListeUtilisateurs() {
        Modèle modèle = new Modèle();
        Utilisateur mock1 = new Utilisateur(1,"Danny", Niveau.EXPERT,"Toronto");
        Utilisateur mock2 = new Utilisateur(2,"John",Niveau.LÉGENDAIRE,"New York");
        List<Utilisateur> mock = Arrays.asList(mock1,mock2);
        modèle.setListeUtilisateurs(mock);
        List<Integer> attendu = Arrays.asList(1,2);
        List<Integer> utilisateursID = new ArrayList<>();

        for(int i=0;i<modèle.getListUtilisateurs().size();i++) {
            utilisateursID.add(modèle.getListUtilisateurs().get(i).getId());
        }
        modèle.setListUtilisateursId(utilisateursID);

        assertEquals(attendu.size(),modèle.getListUtilisateursId().size());
        modèle.viderListeUtilisateurs();
        assertEquals(0,modèle.getListUtilisateursId().size());

    }

    @Test
    public void testGetUtilisateur() {
        Modèle modèle = new Modèle();
        Utilisateur mock1 = new Utilisateur(1,"Danny", Niveau.EXPERT,"Toronto");
        Utilisateur attendu = new Utilisateur(1,"Danny", Niveau.EXPERT,"Toronto");
        modèle.setUtilisateur(mock1);
        assertEquals(attendu.getId(),modèle.getUtilisateur().getId());
        assertEquals(attendu.getEmail(),modèle.getUtilisateur().getEmail());
    }

    @Test
    public void testSetUtilisateur() {
        Modèle modèle = new Modèle();
        Utilisateur mock1 = new Utilisateur(1,"Danny", Niveau.EXPERT,"Toronto");
        Utilisateur attendu = new Utilisateur(1,"Danny", Niveau.EXPERT,"Toronto");
        modèle.setUtilisateur(mock1);
        assertEquals(attendu.getId(),modèle.getUtilisateur().getId());
        assertEquals(attendu.getEmail(),modèle.getUtilisateur().getEmail());
    }

    @Test
    public void testSetUtilisateurEnRevue() {
        Modèle modèle = new Modèle();
        modèle.setUtilisateurEnRevue(5);
        assertEquals(5,modèle.getUtilisateurEnRevue());
    }

    @Test
    public void testGetTexteRéponse() {
        Modèle modèle = new Modèle();
        modèle.setTexteRéponse("HI");

        assertEquals("HI",modèle.getTexteRéponse());
    }

    @Test
    public void testSetTexteRéponse() {
        Modèle modèle = new Modèle();
        modèle.setTexteRéponse("HI");

        assertEquals("HI",modèle.getTexteRéponse());
    }

    @Test
    public void testGetNombreMessageTotale() {
        Modèle modèle = new Modèle();
        modèle.setNombreMessageTotale(15);

        assertEquals(15,modèle.getNombreMessageTotale());
    }

    @Test
    public void testSetNombreMessageTotale() {
        Modèle modèle = new Modèle();
        modèle.setNombreMessageTotale(15);

        assertEquals(15,modèle.getNombreMessageTotale());
    }

    @Test
    public void testAjouterListeMessage() {
        Modèle modèle = new Modèle();
        Utilisateur mockUser1 = new Utilisateur(1,"Danny", Niveau.EXPERT,"Toronto");
        Utilisateur mockUser2 = new Utilisateur(2,"John",Niveau.LÉGENDAIRE,"New York");
        Message mockMessage1 = new Message(1,"Hello",mockUser1,new Date(2020,12,5,8,0),true);
        Message mockMessage2 = new Message(2,"Hi, friend.",mockUser2,new Date(2020,12,5,8,15),true);
        List<Message> list = new ArrayList<>();
        list.add(mockMessage1);
        list.add(mockMessage2);
        modèle.setListeMessage(list);
        List<Message> messageList = new ArrayList<>();
        messageList.add(new Message(3,"How are you?",mockUser1,new Date(2020,12,5,8,20),true));
        messageList.add(new Message(4,"Fine and you?",mockUser1,new Date(2020,12,5,8,22),false));

        modèle.ajouterListeMessage(messageList);
        assertEquals(4,modèle.getMessages().size());
    }

    @Test
    public void testGetPartie(){
        Modèle modèle = new Modèle();
        Partie mock1 = new Partie(1,true,2,false,null);
        modèle.setPartieChoisi(mock1);

        assertEquals(2,modèle.getPartieChoisi().getIdAdv());
        assertEquals(1,modèle.getPartieChoisi().getIdPartie());
    }

    @Test
    public void testSetPartie(){
        Modèle modèle = new Modèle();
        Partie mock1 = new Partie(1,true,2,false,null);
        modèle.setPartieChoisi(mock1);

        assertEquals(2,modèle.getPartieChoisi().getIdAdv());
        assertEquals(1,modèle.getPartieChoisi().getIdPartie());
    }

    @Test
    public void testSetUtilisateurDeApplication(){
        Modèle modèle = new Modèle();
        Utilisateur mockUser1 = new Utilisateur(1,"Danny", Niveau.EXPERT,"Toronto");

        modèle.setUtilisateurDeApplication(mockUser1);
        assertEquals(1,modèle.getUtilisateurDeApplication().getId());
        assertEquals("Danny",modèle.getUtilisateurDeApplication().getNom());
    }

    @Test
    public void getUtilisateurDeApplication(){
        Modèle modèle = new Modèle();
        Utilisateur mockUser1 = new Utilisateur(1,"Danny", Niveau.EXPERT,"Toronto");
        Utilisateur attendu = new Utilisateur(1,"Danny",Niveau.EXPERT,"Toronto");

        modèle.setUtilisateurDeApplication(mockUser1);
        assertEquals(attendu.getId(),modèle.getUtilisateurDeApplication().getId());
        assertEquals(attendu.getNom(),modèle.getUtilisateurDeApplication().getNom());
    }
}