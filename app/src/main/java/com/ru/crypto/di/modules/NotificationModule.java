package com.ru.crypto.di.modules;


import androidx.room.Room;

import com.ru.crypto.api.INetworkService;
import com.ru.crypto.db.CryptoCurrencyDatabase;
import com.ru.crypto.db.NotificationDatabase;
import com.ru.crypto.di.App;
import com.ru.crypto.repositories.NotificationRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NotificationModule {

    @Singleton
    @Provides
    NotificationDatabase provideNotificationDB() {
        return Room.databaseBuilder(App.getInstance(),
                NotificationDatabase.class, "notification db")
                .fallbackToDestructiveMigration()
                .build();
    }
    @Singleton
    @Provides
    NotificationRepository provideNotificationRepo(INetworkService networkService, NotificationDatabase db) {
        return new NotificationRepository(networkService, db);
    }
}
