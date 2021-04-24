package com.ru.crypto.di.modules;



import com.ru.crypto.api.CryptoCurrencyNetworkService;
import com.ru.crypto.repositories.CryptoCurrencyRepository;
import com.ru.crypto.repositories.GlobalDataRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepoModule {
    @Provides
    @Singleton
    CryptoCurrencyRepository provideCryptoCurrencyRepository(CryptoCurrencyNetworkService cryptoCurrencyNetworkService) {
        return new CryptoCurrencyRepository(cryptoCurrencyNetworkService);
    }
    @Provides
    @Singleton
    GlobalDataRepository providesGlobalDataRepository(CryptoCurrencyNetworkService cryptoCurrencyNetworkService) {
        return new GlobalDataRepository(cryptoCurrencyNetworkService);
    }

}