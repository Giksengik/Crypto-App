package com.ru.crypto.services;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.ru.crypto.R;
import com.ru.crypto.api.INetworkService;
import com.ru.crypto.di.App;


import com.ru.crypto.di.components.CryptoCurrencyNetworkServiceComponent;
import com.ru.crypto.di.components.DaggerCryptoCurrencyNetworkServiceComponent;
import com.ru.crypto.models.CryptoCurrency;
import com.ru.crypto.models.NotificationData;
import com.ru.crypto.utils.NetworkManager;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationBroadcast extends BroadcastReceiver {

    @Inject
    INetworkService networkService;
    public static int count = 1;
    @Override
    public synchronized void onReceive(Context context, Intent intent) {

        CryptoCurrencyNetworkServiceComponent component = DaggerCryptoCurrencyNetworkServiceComponent.create();
        component.inject(this);
        Gson gson =  new Gson();
        NotificationData notificationData = gson.fromJson((String)intent.getSerializableExtra(NotificationData.KEY_TO_SERIALIZE)
                , NotificationData.class);
        if( notificationData != null){
            switch (notificationData.getNotificationType()) {
                case NotificationData.TYPE_BORDER_EVENT:
                    comparePriceAndBorders(notificationData, context);
                    break;
                case NotificationData.TYPE_SINGLE:
                case NotificationData.TYPE_CYCLICAL_PRICE:
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
                            showCurrencyPriceNotification(context, response.body().get(0), notificationData);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CryptoCurrency>> call, Throwable t) {
                        if(NetworkManager.hasConnection(App.getInstance())){
                            getCurrencyData(notificationData, context);
                        }
                    }
                });
    }

    public void comparePriceAndBorders(NotificationData notificationData, Context context) {
        networkService.getJSONApi()
                .getDefaultInfo("usd", notificationData.getCurrencyID())
                .enqueue(new Callback<List<CryptoCurrency>>() {
                    @Override
                    public void onResponse(Call<List<CryptoCurrency>> call, Response<List<CryptoCurrency>> response) {
                        if(response.body() != null && response.body().size() > 0) {
                            CryptoCurrency cryptoCurrency = response.body().get(0);
                            if(cryptoCurrency.getCurrentPrice() > notificationData.getTopBorder()){
                                showTopBorderNotification(context, cryptoCurrency,notificationData);

                                Intent broadcastIntent = new Intent(NotificationService.ACTION_DELETE);
                                Gson gson = new Gson();
                                broadcastIntent.putExtra(NotificationData.KEY_TO_SERIALIZE, gson.toJson(notificationData));
                                LocalBroadcastManager bm = LocalBroadcastManager.getInstance(context);
                                bm.sendBroadcast(broadcastIntent);
                            }

                            else if (cryptoCurrency.getCurrentPrice() < notificationData.getBottomBorder()) {
                                showBottomBorderNotification(context, cryptoCurrency,notificationData);

                                Intent broadcastIntent = new Intent(NotificationService.ACTION_DELETE);
                                Gson gson = new Gson();
                                broadcastIntent.putExtra(NotificationData.KEY_TO_SERIALIZE, gson.toJson(notificationData));
                                LocalBroadcastManager bm = LocalBroadcastManager.getInstance(context);
                                bm.sendBroadcast(broadcastIntent);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CryptoCurrency>> call, Throwable t) {
                        if(NetworkManager.hasConnection(App.getInstance())){
                            comparePriceAndBorders(notificationData, context);
                        }
                    }
                });
    }


    public void showBottomBorderNotification(Context context, CryptoCurrency currency, NotificationData notificationData) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat.Builder(context, App.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.icon_bottom_border_notification)
                .setContentTitle(currency.getName() + " price fell below the border")
                .setContentText("Cryptocurrency price is now "
                        + currency.getCurrentPrice() + "$")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(notificationData.getNotificationID(), notification);
    }

    public void showTopBorderNotification(Context context, CryptoCurrency currency, NotificationData notificationData) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat.Builder(context, App.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.icon_top_border_notification)
                .setContentTitle(currency.getName() + " price has risen above the border")
                .setContentText("Cryptocurrency price is now " + currency.getCurrentPrice() + "$")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(notificationData.getNotificationID(), notification);
    }

    public void showCurrencyPriceNotification(Context context, CryptoCurrency currency, NotificationData notificationData) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat.Builder(context, App.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.icon_info_notification)
                .setContentTitle("Current price")
                .setContentText(currency.getName() + " price is " + currency.getCurrentPrice() + "$")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(notificationData.getNotificationID(), notification);
    }
}
