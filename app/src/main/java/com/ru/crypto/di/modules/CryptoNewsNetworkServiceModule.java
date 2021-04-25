package com.ru.crypto.di.modules;

import com.google.gson.Gson;
import com.ru.crypto.api.CryptoNewsNetworkService;
import com.ru.crypto.api.INetworkService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class CryptoNewsNetworkServiceModule {
    static final String BASE_URL = "https://min-api.cryptocompare.com/";

    @Provides
    @Singleton
    static Retrofit provideRetrofit(Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    @Singleton
    INetworkService provideNetworkService(Retrofit retrofit) {
        return new CryptoNewsNetworkService(retrofit);
    }

}
