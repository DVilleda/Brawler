package com.example.brawler.ui.activité.Services.jobServices;

import android.app.IntentService;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.brawler.DAO.SourceMessageApi;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.présenteur.Notification.PrésenteurNoficationMessage;
import com.example.brawler.présentation.vue.notification.VueNotificationMessage;
import com.example.brawler.ui.activité.Services.ServiceNotificationMessage;


public class MessageJobService extends JobService {
    private static final String TAG = "SyncService";
    private String clé;

    private  VueNotificationMessage vue;
    private PrésenteurNoficationMessage présenteur;

    @Override
    public boolean onStartJob(JobParameters params) {


        Log.i(TAG, "on start job: " + params.getJobId());
        vue = new VueNotificationMessage(getApplicationContext(), getResources());
        présenteur = new PrésenteurNoficationMessage(new Modèle(), vue);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        clé = sharedPref.getString("token", "");

        présenteur.setSource(new SourceMessageApi(clé));
        présenteur.getMessagesÀNotifier();
        présenteur.notifierMessage();


        ServiceNotificationMessage.démarerJob(getApplicationContext());
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "on Stop job: " + params.getJobId());

        return true;
    }

}
