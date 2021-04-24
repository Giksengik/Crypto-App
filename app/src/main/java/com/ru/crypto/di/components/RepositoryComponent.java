package com.ru.crypto.di.components;

import com.ru.crypto.di.modules.RepoModule;
import com.ru.crypto.di.modules.NetworkModule;
import com.ru.crypto.repositories.CryptoCurrencyRepository;
import com.ru.crypto.repositories.GlobalDataRepository;
import com.ru.crypto.ui.global.GlobalViewModel;
import com.ru.crypto.ui.market.MarketViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {RepoModule.class, NetworkModule.class})
@Singleton
public interface RepositoryComponent {
    CryptoCurrencyRepository getCryptoCurrencyRepo();
    GlobalDataRepository getGlobalDataRepo();
    void inject(MarketViewModel viewModel);
    void inject(GlobalViewModel viewModel);
}
