package com.ru.crypto.di;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    private static App instance;

    public static App getInstance() {
        return instance;
    }

    public static final String GROUP_1_ID = "General";
    public static final String CHANNEL_1_ID = "Currency notifications";
    public static final String CHANNEL_2_ID = "channel2";
    public static final String RESTART_INTENT = "com.ru.crypto.api";
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannelGroup currencyNotificationGroup = new NotificationChannelGroup(GROUP_1_ID, "General notifications");
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Currency notifications",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel1.setDescription("This is Channel 1");
            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "Channel 2",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel2.setDescription("This is Channel 2");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
    }

}
