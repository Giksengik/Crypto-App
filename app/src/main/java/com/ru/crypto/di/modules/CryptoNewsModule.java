package com.ru.crypto.di.modules;

import androidx.room.Room;

import com.ru.crypto.adapters.CryptoArticlesAdapter;
import com.ru.crypto.api.CryptoNewsNetworkService;
import com.ru.crypto.api.INetworkService;
import com.ru.crypto.db.CryptoArticleDatabase;
import com.ru.crypto.di.App;
import com.ru.crypto.repositories.CryptoNewsRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CryptoNewsModule {

    @Singleton
    @Provides
    CryptoNewsRepository provideRepo(INetworkService networkService, CryptoArticleDatabase db) {
        return new CryptoNewsRepository(networkService, db);
    }

    @Singleton
    @Provides
    CryptoArticleDatabase provideDB() {
        return Room.databaseBuilder(App.getInstance(),
                CryptoArticleDatabase.class, "crypto article db")
                .fallbackToDestructiveMigration()
                .build();
    }

}
