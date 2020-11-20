package com.example.brawler.présentation.vue.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;

import com.example.brawler.R;
import com.example.brawler.domaine.entité.Message;
import com.example.brawler.présentation.présenteur.Notification.PrésenteurNoficationMessage;
import com.example.brawler.ui.activité.Services.ServiceNotificationMessage;
import com.example.brawler.ui.activité.Services.jobServices.MessageJobService;

public class VueNotificationMessage {
    private  final static String CHANNEL_ID = "MessageBrawlerA";
    private static final String KEY_TEXT_REPLY = "ReplyBrawlerA";

    private Context context;
    private Resources resources;
    private PrésenteurNoficationMessage présenteur;
    private Intent resultIntent;
    public VueNotificationMessage(Context applicationContext, Resources resources) {
        this.context = applicationContext;
        this.resources = resources;
        this.resultIntent = new Intent(context, MessageJobService.class);
    }

    public void setPrésenteur(PrésenteurNoficationMessage présenteur) {
        this.présenteur = présenteur;
    }

    public void afficherNotification(com.example.brawler.domaine.entité.Notification notification) {
        NotificationCompat.MessagingStyle messages = new NotificationCompat.MessagingStyle(resources.getString(R.string.reply_name));

        //insère les message recu dans la notification
        for(Message message : notification.getMessage()){
            NotificationCompat.MessagingStyle.Message nouveauMessage =
                    new NotificationCompat.MessagingStyle.Message(message.getTexte(),
                            message.getTemps().getTime(),
                            message.getUtilisateur().getNom());
            messages.addMessage(nouveauMessage);
        }

        //instance de remote input builder
        String replyLabel = resources.getString(R.string.reply_name);
        RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
                .setLabel(replyLabel)
                .build();

        //créer le intent poru que l'utilisateur puisse répondre
        PendingIntent replyPendingIntent =
                PendingIntent.getBroadcast(context,
                        notification.getUtilisateur().getId(),
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        //attacheune action
        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.reply_24px,
                        resources.getString(R.string.age), replyPendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();

        //Build la notificaiton
        Notification uneNotificationAndroid = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_aller_profil)
                .setStyle(messages)
                .addAction(action)
                .build();

        //Envoie la notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify( 0, uneNotificationAndroid);
    }
}
