package com.example.brawler.ui.activité.Services.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.brawler.ui.activité.Services.ServiceNotificationMessage;

import static android.content.ContentValues.TAG;

public class DémmarerServiceReceveir extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "boot signal received");
        ServiceNotificationMessage.démarerJob(context);
    }
}
