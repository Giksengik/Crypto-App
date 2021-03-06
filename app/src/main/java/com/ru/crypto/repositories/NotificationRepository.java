package com.ru.crypto.repositories;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.ru.crypto.api.INetworkService;
import com.ru.crypto.services.NotificationBroadcast;
import com.ru.crypto.services.NotificationService;
import com.ru.crypto.db.NotificationDatabase;
import com.ru.crypto.di.App;
import com.ru.crypto.models.CryptoCurrency;
import com.ru.crypto.models.CryptoCurrencyName;
import com.ru.crypto.models.NotificationData;
import com.ru.crypto.utils.NetworkManager;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationRepository {


    private MutableLiveData<List<CryptoCurrencyName>> currencyNames;
    private LiveData<List<NotificationData>> mAllNotificationData;
    private MutableLiveData<List<NotificationData>> mCurrentNotifications;
    private MutableLiveData<CryptoCurrency> mCurrentCryptoCurrency;

    private INetworkService mNetworkService;
    private NotificationDatabase database;


    public NotificationRepository(INetworkService networkService, NotificationDatabase db) {

        this.mNetworkService = networkService;
        database = db;
        mAllNotificationData = database.notificationDao().getAllNotifications();
        currencyNames = new MutableLiveData<>();
        mCurrentNotifications = new MutableLiveData<>();
        mCurrentCryptoCurrency = new MutableLiveData<>();

        loadAllCurrencies();

        IntentFilter filter = new IntentFilter();
        filter.addAction(NotificationService.ACTION_DELETE);
        LocalBroadcastManager bm = LocalBroadcastManager.getInstance(App.getInstance());
        BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(NotificationService.ACTION_DELETE)) {
                    Gson gson = new Gson();
                    String jsonStr = intent.getStringExtra(NotificationData.KEY_TO_SERIALIZE);
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            database.notificationDao()
                                    .deleteNotification(gson.fromJson(jsonStr, NotificationData.class));
                        }
                    }.start();
                }
            }
        };
        bm.registerReceiver(mBroadcastReceiver, filter);
    }

    public LiveData<List<NotificationData>> getAllNotifications() {
        return mAllNotificationData;
    }

    public LiveData<List<CryptoCurrencyName>> getCurrencies() {
        return currencyNames;
    }

    public void loadAllCurrencies() {
        mNetworkService.getJSONApi()
                .getAllCurrencies()
                .enqueue(new Callback<List<CryptoCurrencyName>>() {
                    @Override
                    public void onResponse(Call<List<CryptoCurrencyName>> call, Response<List<CryptoCurrencyName>> response) {
                        if(response.body() != null) {
                            currencyNames.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CryptoCurrencyName>> call, Throwable t) {
                        if(NetworkManager.hasConnection(App.getInstance())){
                            loadAllCurrencies();
                        } else Toast.makeText(App.getInstance(),"Fail to load data, check internet connection", Toast.LENGTH_LONG ).show();
                    }
                });
    }


    public void addNotification(NotificationData notificationData) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                database.notificationDao()
                        .addNotification(notificationData);
                mAllNotificationData = database.notificationDao().getAllNotifications();
            }
        }.start();
    }

    public void updateCurrentNotifications(long timeMillis) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                List<NotificationData> notificationList = database.notificationDao().getCurrentNotifications(timeMillis);
                mCurrentNotifications.postValue(notificationList);
            }
        }.start();
    }

    public LiveData<List<NotificationData>> getCurrentNotifications() {
        return mCurrentNotifications;
    }

    public void deleteNotification(NotificationData notificationData) {
        new Thread() {
            @Override
            public void run() {
                database.notificationDao().deleteNotification(notificationData);
                mAllNotificationData = database.notificationDao().getAllNotifications();
            }
        }.start();
    }

    public void updateNotification ( NotificationData notificationData) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                database.notificationDao()
                        .updateNotification(notificationData);
            }
        }.start();
    }

    public void sendToCheckBorderNotifications(Context context) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                List<NotificationData> borderNotifications = new ArrayList<>();
                borderNotifications = database.notificationDao()
                        .getNotificationsWithType(NotificationData.TYPE_BORDER_EVENT);

                for(NotificationData notificationData : borderNotifications) {
                    Intent broadcastIntent = new Intent(context, NotificationBroadcast.class);
                    Gson gson = new Gson();
                    broadcastIntent.putExtra(NotificationData.KEY_TO_SERIALIZE, gson.toJson(notificationData));
                    context.sendBroadcast(broadcastIntent);
                }
            }
        }.start();
    }

    public void loadCurrentCryptoCurrency(String id) {
        mNetworkService.getJSONApi()
                .getDefaultInfo("usd", id)
                .enqueue(new Callback<List<CryptoCurrency>>() {
                    @Override
                    public void onResponse(Call<List<CryptoCurrency>> call, Response<List<CryptoCurrency>> response) {
                        if(response.body() != null && response.body().size() > 0) {
                          mCurrentCryptoCurrency.setValue(response.body().get(0));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CryptoCurrency>> call, Throwable t) {
                        if(NetworkManager.hasConnection(App.getInstance())){
                            loadCurrentCryptoCurrency(id);
                        } else Toast.makeText(App.getInstance(),"Fail to load data, check internet connection", Toast.LENGTH_LONG ).show();
                    }
                });
    }

    public LiveData<CryptoCurrency> getCurrentCryptoCurrency() {
        return mCurrentCryptoCurrency;
    }
}
