package com.example.brawler.ui.activité.Services.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.brawler.ui.activité.Services.ServiceNotificationMessage;

public class DémmarerServiceReceveir extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        ServiceNotificationMessage.démarerJob(context);
    }
}
