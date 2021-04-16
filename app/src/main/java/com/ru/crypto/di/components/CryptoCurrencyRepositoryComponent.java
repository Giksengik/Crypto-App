package com.ru.crypto.di.components;

import com.ru.crypto.di.modules.CryptoCurrencyRepoModule;
import com.ru.crypto.di.modules.NetworkModule;
import com.ru.crypto.repositories.CryptoCurrencyRepository;
import com.ru.crypto.ui.market.MarketViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {CryptoCurrencyRepoModule.class, NetworkModule.class})
@Singleton
public interface CryptoCurrencyRepositoryComponent {
    CryptoCurrencyRepository getRepo();
    void inject(MarketViewModel viewModel);
}
