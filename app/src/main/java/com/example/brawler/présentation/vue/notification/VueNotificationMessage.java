package com.example.brawler.présentation.vue.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;
import android.util.TimeFormatException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.example.brawler.R;
import com.example.brawler.domaine.entité.Message;

import java.sql.Time;
import java.util.Date;

public class VueNotificationMessage {

    private  final static String CHANNEL_ID = "MessageBrawlerA";
    private Context context;
    private Resources resources;
    private boolean test;

    public VueNotificationMessage(Context applicationContext, Resources resources) {
        this.context = applicationContext;
        this.resources = resources;
        test = true;
    }

    public void afficherNotification(com.example.brawler.domaine.entité.Notification notification) {
        if(test) {
            NotificationCompat.MessagingStyle messages = new NotificationCompat.MessagingStyle(resources.getString(R.string.reply_name));
            for(Message message : notification.getMessage()){
                NotificationCompat.MessagingStyle.Message nouveauMessage =
                        new NotificationCompat.MessagingStyle.Message(message.getTexte(),
                                message.getTemps().getTime(),
                                message.getUtilisateur().getNom());
                messages.addMessage(nouveauMessage);
            }
            Notification uneNotificationAndroid = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_aller_profil)
                    .setStyle(messages).build();

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

            notificationManager.notify( 0, uneNotificationAndroid);
        }
//
    }

    private void createNotificationChannel() {

    }
}
