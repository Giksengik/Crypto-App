package com.ru.crypto.di.modules;

import com.ru.crypto.api.INetworkService;
import com.ru.crypto.repositories.CurrencyMarketChartRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CurrencyMarketChartRepositoryModule {

    @Singleton
    @Provides
    CurrencyMarketChartRepository provideRepo(INetworkService service) {
        return new CurrencyMarketChartRepository(service);
    }
}
