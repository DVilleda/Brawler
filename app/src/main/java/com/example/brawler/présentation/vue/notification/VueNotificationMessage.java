package com.example.brawler.présentation.vue.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.Person;
import androidx.core.app.RemoteInput;

import com.example.brawler.R;
import com.example.brawler.domaine.entité.Message;
import com.example.brawler.présentation.présenteur.Notification.PrésenteurNoficationMessage;
import com.example.brawler.ui.activité.Services.Receiver.MessageReceiver;
import com.example.brawler.ui.activité.Services.ServiceNotificationMessage;
import com.example.brawler.ui.activité.Services.jobServices.MessageJobService;

import java.util.Date;
import java.util.List;

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

        //Envoie la notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        //creer le cahnnel de notifcaiton
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notifcationChannel = new NotificationChannel(CHANNEL_ID, resources.getString(R.string.channel_name), NotificationManager.IMPORTANCE_DEFAULT);

            //détail du channel
            notifcationChannel.enableLights(true);
            notifcationChannel.setLightColor(Color.RED);

            notificationManager.createNotificationChannel(notifcationChannel);
        }
        notificationManager.notify( notification.getUtilisateur().getId(), créerNotification(notification.getUtilisateur().getId(), créerStyleAvecListeMessage(notification.getMessage())));
    }

    public void répondreNotification(int idUtilisateur, String texte){
        Notification repliedNotification = null;



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                repliedNotification = getActiveNotification(idUtilisateur);
            }
        }

        if(repliedNotification != null) {
            NotificationCompat.MessagingStyle style = NotificationCompat.MessagingStyle.extractMessagingStyleFromNotification(repliedNotification);
            style.getMessages();
            Date date = new Date();
            style.addMessage(new NotificationCompat.MessagingStyle.Message(texte,
                    date.getTime(),
                    (Person) null));

            repliedNotification = créerNotification(idUtilisateur, style);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(idUtilisateur, repliedNotification);
        }


    }


    private NotificationCompat.MessagingStyle créerStyleAvecListeMessage(List<Message> Listemessages){
        NotificationCompat.MessagingStyle style = new NotificationCompat.MessagingStyle(resources.getString(R.string.reply_name));

        for(Message message : Listemessages){
            NotificationCompat.MessagingStyle.Message nouveauMessage =
                    new NotificationCompat.MessagingStyle.Message(message.getTexte(),
                            message.getTemps().getTime(),
                            message.getUtilisateur().getNom());
            style.addMessage(nouveauMessage);
        }

        return style;
    }

    private Notification créerNotification(int idUtilisateur, NotificationCompat.MessagingStyle messages){


        this.resultIntent = new Intent(context, MessageReceiver.class);
        resultIntent.putExtra(EXTRA_ID_UTILISATEUR_NOTIFICATION, idUtilisateur);

        //instance de remote input builder
        String replyLabel = resources.getString(R.string.reply_name);
        RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
                .setLabel(replyLabel)
                .build();

        //créer le intent pour que l'utilisateur puisse répondre
        PendingIntent replyPendingIntent =
                PendingIntent.getBroadcast(context,
                        idUtilisateur,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        //attacheune action
        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.reply_24px,
                        resources.getString(R.string.reply_name), replyPendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();

        //Build la notificaiton
        Notification uneNotificationAndroid = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.fist_bump)
                .setContentTitle("New mail from " + "test@gmail.com")
                .setContentText("Subject")
                .setStyle(messages)
                .addAction(action)
                .build();

        return uneNotificationAndroid;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private Notification getActiveNotification(int notificationId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        StatusBarNotification[] barNotifications = notificationManager.getActiveNotifications();
        for(StatusBarNotification notification: barNotifications) {
            if (notification.getId() == notificationId) {
                return notification.getNotification();
            }
        }
        return null;
    }

}
