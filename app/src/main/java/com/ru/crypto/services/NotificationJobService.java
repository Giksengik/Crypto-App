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

    private void registerRestarterReceiver() {

        // the context can be null if app just installed and this is called from restartsensorservice
        // https://stackoverflow.com/questions/24934260/intentreceiver-components-are-not-allowed-to-register-to-receive-intents-when
        // Final decision: in case it is called from installation of new version (i.e. from manifest, the application is
        // null. So we must use context.registerReceiver. Otherwise this will crash and we try with context.getApplicationContext
        if (restartBroadcastReceiver == null)
            restartBroadcastReceiver = new RestartBroadcastReceiver();
        else try{
            unregisterReceiver(restartBroadcastReceiver);
        } catch (Exception e){
            // not registered
        }
        //give the time to run
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

    public static void stopJob(Context context) {
        if (instance!=null && jobParameters!=null) {
            try{
                instance.unregisterReceiver(restartBroadcastReceiver);
            } catch (Exception e){
            }
            instance.jobFinished(jobParameters, true);
        }
    }
}
