package com.ru.crypto.di.modules;



import com.ru.crypto.api.NetworkService;
import com.ru.crypto.repositories.CryptoCurrencyRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class CryptoCurrencyRepoModule {
    @Provides
    CryptoCurrencyRepository provideCryptoCurrencyRepository(NetworkService networkService) {
        return new CryptoCurrencyRepository(networkService);
    }

}