package com.ru.crypto.di.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ru.crypto.api.CryptoCurrencyNetworkService;
import com.ru.crypto.api.INetworkService;
import com.ru.crypto.repositories.CryptoCurrencyRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class CryptoCurrencyModule {
    @Provides
    @Singleton
    CryptoCurrencyRepository provideCryptoCurrencyRepository(INetworkService networkService) {
        return new CryptoCurrencyRepository(networkService);
    }

}
