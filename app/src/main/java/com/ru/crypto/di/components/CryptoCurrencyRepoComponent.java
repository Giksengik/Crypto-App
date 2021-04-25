package com.ru.crypto.di.components;

import com.ru.crypto.api.CryptoCurrencyNetworkService;
import com.ru.crypto.di.modules.CryptoCurrencyModule;
import com.ru.crypto.di.modules.CryptoCurrencyNetworkServiceModule;
import com.ru.crypto.di.modules.GsonModule;
import com.ru.crypto.repositories.CryptoCurrencyRepository;
import com.ru.crypto.ui.market.MarketViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {GsonModule.class, CryptoCurrencyNetworkServiceModule.class, CryptoCurrencyModule.class})
@Singleton
public interface CryptoCurrencyRepoComponent {

    CryptoCurrencyRepository getRepo();
    void inject(MarketViewModel viewModel);
}
