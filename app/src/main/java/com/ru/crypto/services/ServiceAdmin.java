package com.ru.crypto.services;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.content.ContextCompat;

import com.ru.crypto.services.NotificationService;

public class ServiceAdmin {

    private static final String TAG = "ServiceAdmin";

    private static Intent serviceIntent = null;

    public ServiceAdmin() {
    }

    private void setServiceIntent(Context context) {
        if (serviceIntent == null) {
            serviceIntent = new Intent(context, NotificationService.class);
        }
    }

    public void launchService(Context context) {
        if (context == null) {
            return;
        }

        setServiceIntent(context);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(context,serviceIntent);
        } else {
            context.startService(serviceIntent);
        }
    }
}
