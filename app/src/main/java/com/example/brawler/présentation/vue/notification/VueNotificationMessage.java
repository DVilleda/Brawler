package com.example.brawler.présentation.vue.notification;

import android.app.Notification;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.example.brawler.R;
import com.example.brawler.domaine.entité.Message;
public class VueNotificationMessage extends Fragment {

    private  final static String CHANNEL_ID = "MessageBrawlerA";

    public void afficherNotification(com.example.brawler.domaine.entité.Notification notification) {

        NotificationCompat.MessagingStyle messages = new NotificationCompat.MessagingStyle(getResources().getString(R.string.reply_name));

        for(Message message : notification.getMessage()){
            NotificationCompat.MessagingStyle.Message nouveauMessage =
                    new NotificationCompat.MessagingStyle.Message(message.getTexte(),
                            message.getTemps().getTime(),
                            message.getUtilisateur().getNom());
            messages.addMessage(nouveauMessage);
        }
        Notification uneNotificationAndroid = new NotificationCompat.Builder(getContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_aller_profil)
                .setStyle(messages)
                .build();
    }
}
