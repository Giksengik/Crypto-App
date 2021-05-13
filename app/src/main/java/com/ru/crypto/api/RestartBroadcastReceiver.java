package com.ru.crypto.api;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.RequiresApi;

import com.ru.crypto.di.App;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

public class RestartBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "RestartBroadcastReceive";
    private static JobScheduler jobScheduler;
    private RestartBroadcastReceiver restartBroadcastReceiver;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            scheduleJob(context);
        } else {
            registerRestarterReceiver(context);
            ServiceAdmin bck = new ServiceAdmin();
            bck.launchService(context);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void scheduleJob(Context context) {
        if (jobScheduler == null) {
            jobScheduler = (JobScheduler) context
                    .getSystemService(JOB_SCHEDULER_SERVICE);
        }
        ComponentName componentName = new ComponentName(context,
                NotificationJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(1, componentName)
                .setOverrideDeadline(0)
                .setPersisted(true).build();
        jobScheduler.schedule(jobInfo);
    }

    public static void reStartTracker(Context context) {
        Intent broadcastIntent = new Intent(App.RESTART_INTENT);
        context.sendBroadcast(broadcastIntent);
    }

    private void registerRestarterReceiver(final Context context) {

        if (restartBroadcastReceiver == null)
            restartBroadcastReceiver = new RestartBroadcastReceiver();
        else try{
            context.unregisterReceiver(restartBroadcastReceiver);
        } catch (Exception e){
            // not registered
        }
        new Handler().postDelayed(() -> {
            IntentFilter filter = new IntentFilter();
            filter.addAction(App.RESTART_INTENT);
            try {
                context.registerReceiver(restartBroadcastReceiver, filter);
            } catch (Exception e) {
                try {
                    context.getApplicationContext().registerReceiver(restartBroadcastReceiver, filter);
                } catch (Exception ex) {

                }
            }
        }, 1000);

    }
}