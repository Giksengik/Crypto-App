package com.ru.crypto.di.modules;

import com.google.gson.Gson;
import com.ru.crypto.api.CryptoCurrencyNetworkService;
import com.ru.crypto.api.CryptoNewsNetworkService;
import com.ru.crypto.api.INetworkService;
import com.ru.crypto.repositories.CryptoCurrencyRepository;
import com.ru.crypto.repositories.GlobalDataRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class GlobalDataModule {
    @Provides
    @Singleton
    GlobalDataRepository provideGlobalDataRepository(INetworkService networkService) {
        return new GlobalDataRepository(networkService);
    }

}
