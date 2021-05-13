package com.ru.crypto.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ru.crypto.api.INetworkService;
import com.ru.crypto.db.NotificationDatabase;
import com.ru.crypto.models.CryptoCurrencyName;
import com.ru.crypto.models.NotificationData;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationRepository {


    private MutableLiveData<List<CryptoCurrencyName>> currencyNames;
    private LiveData<List<NotificationData>> mAllNotificationData;
    private MutableLiveData<List<NotificationData>> mCurrentNotifications;

    private INetworkService mNetworkService;
    private NotificationDatabase database;


    public NotificationRepository(INetworkService networkService, NotificationDatabase db) {

        this.mNetworkService = networkService;
        database = db;
        mAllNotificationData = database.notificationDao().getAllNotifications();
        currencyNames = new MutableLiveData<>();
        mCurrentNotifications = new MutableLiveData<>();
        loadAllCurrencies();

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
                        t.printStackTrace();
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

}
