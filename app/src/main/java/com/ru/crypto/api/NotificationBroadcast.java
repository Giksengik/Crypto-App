package com.ru.crypto.api;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.gson.Gson;
import com.ru.crypto.R;
import com.ru.crypto.di.App;


import com.ru.crypto.di.components.CryptoCurrencyNetworkServiceComponent;
import com.ru.crypto.di.components.DaggerCryptoCurrencyNetworkServiceComponent;
import com.ru.crypto.models.CryptoCurrency;
import com.ru.crypto.models.NotificationData;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationBroadcast extends BroadcastReceiver {

    @Inject
    INetworkService networkService;

    @Override
    public synchronized void onReceive(Context context, Intent intent) {

        CryptoCurrencyNetworkServiceComponent component = DaggerCryptoCurrencyNetworkServiceComponent.create();
        component.inject(this);
        Gson gson =  new Gson();
        NotificationData notificationData = gson.fromJson((String)intent.getSerializableExtra(NotificationData.KEY_TO_SERIALIZE)
                , NotificationData.class);
        if( notificationData != null){
            switch (notificationData.getNotificationType()) {
                case NotificationData.TYPE_SINGLE:
                    getCurrencyData(notificationData, context);
                    break;
            }
        }

    }

    public void getCurrencyData(NotificationData notificationData, Context context) {
        networkService.getJSONApi()
                .getDefaultInfo("usd", notificationData.getCurrencyID())
                .enqueue(new Callback<List<CryptoCurrency>>() {
                    @Override
                    public void onResponse(Call<List<CryptoCurrency>> call, Response<List<CryptoCurrency>> response) {
                        if(response.body() != null) {
                            showCurrencyPriceNotification(context, response.body().get(0));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CryptoCurrency>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }






    public void showTestNotification(Context context, CryptoCurrency currency) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat.Builder(context, App.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.icon_info_notification)
                .setContentTitle("test")
                .setContentText("test price is " + currency.getCurrentPrice())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1, notification);
    }

    public void showCurrencyPriceNotification(Context context, CryptoCurrency currency) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat.Builder(context, App.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.icon_info_notification)
                .setContentTitle("Current price")
                .setContentText(currency.getName() + " price is " + currency.getCurrentPrice() + "$")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1, notification);
    }
}
