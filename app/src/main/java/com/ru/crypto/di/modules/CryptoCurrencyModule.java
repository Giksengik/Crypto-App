package com.ru.crypto.di.modules;

import androidx.room.Room;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ru.crypto.api.CryptoCurrencyNetworkService;
import com.ru.crypto.api.INetworkService;
import com.ru.crypto.db.CryptoArticleDatabase;
import com.ru.crypto.db.CryptoCurrencyDatabase;
import com.ru.crypto.di.App;
import com.ru.crypto.models.CryptoCurrency;
import com.ru.crypto.repositories.CryptoCurrencyRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class CryptoCurrencyModule {

    @Provides
    CryptoCurrencyRepository provideCryptoCurrencyRepository(INetworkService networkService, CryptoCurrencyDatabase database) {
        return new CryptoCurrencyRepository(networkService, database);
    }

    @Singleton
    @Provides
    CryptoCurrencyDatabase provideDB() {
        return Room.databaseBuilder(App.getInstance(),
                CryptoCurrencyDatabase.class, "crypto currency db")
                .fallbackToDestructiveMigration()
                .build();
    }

}
