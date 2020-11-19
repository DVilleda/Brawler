package com.example.brawler.ui.activité.BackgroundProcess;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.brawler.domaine.intéracteur.InteracteurMessage;
import com.example.brawler.domaine.intéracteur.MessageException;
import com.example.brawler.domaine.intéracteur.SourceMessage;
import com.example.brawler.domaine.intéracteur.UtilisateursException;
import com.example.brawler.présentation.modèle.Modèle;
import com.example.brawler.présentation.présenteur.Notification.PrésenteurNoficationMessage;
import com.example.brawler.présentation.vue.notification.VueNotificationMessage;


public class RecevoirMessageWorker extends Worker {

    private  VueNotificationMessage vue;
    private PrésenteurNoficationMessage présenteur;
    private Modèle modèle;

    public RecevoirMessageWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        vue = new VueNotificationMessage();
        présenteur = new PrésenteurNoficationMessage(modèle, vue);
    }

    @NonNull
    @Override
    public Result doWork() {
        présenteur.getMessagesÀNotifier();
        présenteur.notifierMessage();

        return  Result.success();
    }

}
