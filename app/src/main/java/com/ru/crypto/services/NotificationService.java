package com.ru.crypto.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleService;

import com.google.gson.Gson;
import com.ru.crypto.di.components.DaggerNotificationRepoComponent;
import com.ru.crypto.di.components.NotificationRepoComponent;
import com.ru.crypto.models.NotificationData;
import com.ru.crypto.repositories.NotificationRepository;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

public class NotificationService extends LifecycleService {

    private static final String TAG = "NotificationService";

    public static final String ACTION_NOTIFICATION = "com.ru.crypto.NOTIFICATION";
    public static final String ACTION_DELETE = "com.ru.crypto.DELETE";
    @Inject
    NotificationRepository notificationRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationRepoComponent component = DaggerNotificationRepoComponent.create();
        component.inject(this);
        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "my_channel_01";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Crypto currency notification channel",
                    NotificationManager.IMPORTANCE_LOW);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setVisibility(NotificationCompat.VISIBILITY_SECRET)
                    .setContentText("").build();

            startForeground(1, notification);
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        notificationRepository.getCurrentNotifications().observe(this, notificationData -> {
            if(notificationData.size() != 0) {
                for(NotificationData item : notificationData) {
                    if(item.getNotificationType().equals(NotificationData.TYPE_SINGLE)) {
                        sendSingleNotification(item);
                        notificationRepository.deleteNotification(item);
                    }
                    else if (item.getNotificationType().equals(NotificationData.TYPE_CYCLICAL_PRICE)) {
                        sendCyclicalNotification(item);
                        item.setNextNotificationTime(System.currentTimeMillis() + item.getIntervalValueInMillis());
                        notificationRepository.updateNotification(item);
                    }
                }
            }
        });


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                    notificationRepository.updateCurrentNotifications(System.currentTimeMillis());
                }
            }
            ,0,10000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                notificationRepository.sendToCheckBorderNotifications(getApplicationContext());
            }
        }, 0, 90000);
        return START_STICKY;
    }

    public void sendSingleNotification(NotificationData notificationData) {
        Intent broadcastIntent = new Intent(this, NotificationBroadcast.class);
        Gson gson = new Gson();
        broadcastIntent.putExtra(NotificationData.KEY_TO_SERIALIZE, gson.toJson(notificationData));
        sendBroadcast(broadcastIntent);
    }

    public void sendCyclicalNotification(NotificationData notificationData) {
        Intent broadcastIntent = new Intent(this, NotificationBroadcast.class);
        Gson gson = new Gson();
        broadcastIntent.putExtra(NotificationData.KEY_TO_SERIALIZE, gson.toJson(notificationData));
        sendBroadcast(broadcastIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent broadcastIntent = new Intent(this, RestartBroadcastReceiver.class);
        sendBroadcast(broadcastIntent);
    }

}
