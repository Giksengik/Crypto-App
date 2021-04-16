package com.ru.crypto.di.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ru.crypto.api.NetworkService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    static final String BASE_URL = "https://api.coingecko.com/";
    @Provides
    static Gson provideGson() {
        return new GsonBuilder()
                .setLenient()
                .create();
    }
    @Provides
    Retrofit provideRetrofit(Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
    @Provides
    NetworkService provideNetworkService(Retrofit retrofit) {
        return new NetworkService(retrofit);
    }

}
