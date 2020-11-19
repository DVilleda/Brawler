package com.example.brawler.ui.activité.Services;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.brawler.ui.activité.Services.jobServices.MessageJobService;

import static android.content.ContentValues.TAG;

public class ServiceNotificationMessage {

    public static void démarerJob(Context context){
        ComponentName serviceComponent = new ComponentName(context, MessageJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(55, serviceComponent);
        builder.setMinimumLatency(1 * 1000);
        builder.setOverrideDeadline(3 * 1000);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);

        Log.i(TAG, "Job starting: " + 55);

        JobScheduler jobScheduler = null;
        jobScheduler = context.getSystemService(JobScheduler.class);
        jobScheduler.schedule(builder.build());
    }
}
