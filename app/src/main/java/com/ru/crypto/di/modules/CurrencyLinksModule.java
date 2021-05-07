package com.ru.crypto.di.modules;

import com.ru.crypto.api.INetworkService;
import com.ru.crypto.repositories.CurrencyLinksRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CurrencyLinksModule {

    @Provides
    @Singleton
    CurrencyLinksRepository provideRepo(INetworkService networkService) {
        return new CurrencyLinksRepository(networkService);
    }
}
