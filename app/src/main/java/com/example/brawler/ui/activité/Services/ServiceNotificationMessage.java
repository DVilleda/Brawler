package com.example.brawler.ui.activité.Services;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;

import com.example.brawler.ui.activité.Services.jobServices.MessageJobService;

public class ServiceNotificationMessage {

    public static void démarerJob(Context context){
        ComponentName serviceComponent = new ComponentName(context, MessageJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(55, serviceComponent);
        builder.setMinimumLatency(1 * 5000);
        builder.setOverrideDeadline(3 * 3000);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);

        JobScheduler jobScheduler = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            jobScheduler = context.getSystemService(JobScheduler.class);
        }
        jobScheduler.schedule(builder.build());
    }

    public static void arrêterJob(Context context) {
        JobScheduler tm = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            tm = (JobScheduler) context.getSystemService(JobScheduler.class);
        }
        tm.cancelAll();
    }
}
