package com.ru.crypto.di.modules;

import com.ru.crypto.api.CryptoNewsNetworkService;
import com.ru.crypto.api.INetworkService;
import com.ru.crypto.repositories.CryptoNewsRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CryptoNewsModule {

    @Singleton
    @Provides
    CryptoNewsRepository provideRepo(INetworkService networkService) {
        return new CryptoNewsRepository(networkService);
    }

}
