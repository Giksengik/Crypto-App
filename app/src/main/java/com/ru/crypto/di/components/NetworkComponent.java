package com.ru.crypto.di.components;

import com.google.gson.Gson;

import com.ru.crypto.di.modules.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Component (modules = {NetworkModule.class})
@Singleton
public interface NetworkComponent {
    Gson getGson();
    Retrofit getRetrofit();
}
