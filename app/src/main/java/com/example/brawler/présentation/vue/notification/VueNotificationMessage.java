package com.example.brawler.présentation.vue.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;

import com.example.brawler.R;
import com.example.brawler.domaine.entité.Message;
import com.example.brawler.présentation.présenteur.Notification.PrésenteurNoficationMessage;
import com.example.brawler.ui.activité.Services.Receiver.MessageReceiver;
import com.example.brawler.ui.activité.Services.ServiceNotificationMessage;
import com.example.brawler.ui.activité.Services.jobServices.MessageJobService;

public class VueNotificationMessage {
    private  final static String CHANNEL_ID = "com.brawler.channnelId";
    private static final String KEY_TEXT_REPLY = "com.brawler.keyTextReply";
    private static final String EXTRA_ID_UTILISATEUR_NOTIFICATION = "com.brawler.idUtilisateurNotification";

    private Context context;
    private Resources resources;
    private PrésenteurNoficationMessage présenteur;
    private Intent resultIntent;
    public VueNotificationMessage(Context applicationContext, Resources resources) {
        this.context = applicationContext;
        this.resources = resources;

        //créer l'intent pour le bouton reply de la notifiation

    }

    public void setPrésenteur(PrésenteurNoficationMessage présenteur) {
        this.présenteur = présenteur;
    }

    public void afficherNotification(com.example.brawler.domaine.entité.Notification notification) {
        NotificationCompat.MessagingStyle messages = new NotificationCompat.MessagingStyle(resources.getString(R.string.reply_name));

        this.resultIntent = new Intent(context, MessageReceiver.class);
        resultIntent.putExtra(EXTRA_ID_UTILISATEUR_NOTIFICATION, notification.getUtilisateur().getId());

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

        //créer le intent pour que l'utilisateur puisse répondre
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
        notificationManager.notify( notification.getUtilisateur().getId(), uneNotificationAndroid);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void répondreNotification(int idUtilisateur, String texteRéponse){
        Notification repliedNotification = new Notification.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_aller_profil)
                .setContentText(texteRéponse)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(idUtilisateur, repliedNotification);
    }
}
