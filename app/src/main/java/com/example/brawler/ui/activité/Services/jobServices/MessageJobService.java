package com.example.brawler.ui.activité.Services.jobServices;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.brawler.DAO.SourceMessageApi;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.présenteur.Notification.PrésenteurNoficationMessage;
import com.example.brawler.présentation.vue.notification.VueNotificationMessage;
import com.example.brawler.ui.activité.Services.ServiceNotificationMessage;


public class MessageJobService extends JobService {

    private String clé;
    private  VueNotificationMessage vue;
    private PrésenteurNoficationMessage présenteur;

    @Override
    public boolean onStartJob(JobParameters params) {

        vue = new VueNotificationMessage(getApplicationContext(), getResources());
        présenteur = new PrésenteurNoficationMessage(new Modèle(), vue);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        clé = sharedPref.getString("token", "");

        présenteur.setSource(new SourceMessageApi(clé));
        présenteur.startNotifier();


        ServiceNotificationMessage.démarerJob(getApplicationContext());
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {

        return true;
    }

}
