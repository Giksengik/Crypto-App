package com.ru.crypto.services;


import android.app.job.JobParameters;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.RequiresApi;

import com.ru.crypto.di.App;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NotificationJobService extends android.app.job.JobService {

    private static String TAG = "JobService";
    private static RestartBroadcastReceiver restartBroadcastReceiver;
    private static NotificationJobService instance;
    private static JobParameters jobParameters;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        ServiceAdmin serviceAdmin = new ServiceAdmin();
        serviceAdmin.launchService(this);
        instance = this;
        NotificationJobService.jobParameters = jobParameters;

        return false;
    }

    private void registerRestarterReceiver() {
        if (restartBroadcastReceiver == null)
            restartBroadcastReceiver = new RestartBroadcastReceiver();
        else try{
            unregisterReceiver(restartBroadcastReceiver);
        } catch (Exception e){
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                IntentFilter filter = new IntentFilter();
                filter.addAction(App.RESTART_INTENT);
                try {
                    registerReceiver(restartBroadcastReceiver, filter);
                } catch (Exception e) {
                    try {
                        getApplicationContext().registerReceiver(restartBroadcastReceiver, filter);
                    } catch (Exception ex) {

                    }
                }
            }
        }, 1000);
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Intent broadcastIntent = new Intent(App.RESTART_INTENT);
        sendBroadcast(broadcastIntent);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                unregisterReceiver(restartBroadcastReceiver);
            }
        }, 1000);

        return false;
    }

    public static void stopJob(Context context) {
        if (instance!=null && jobParameters!=null) {
            try{
                instance.unregisterReceiver(restartBroadcastReceiver);
            } catch (Exception e){
                // not registered
            }
            instance.jobFinished(jobParameters, true);
        }
    }
}
