package com.ru.crypto.di.components;

import com.ru.crypto.api.CryptoCurrencyNetworkService;
import com.ru.crypto.di.modules.CryptoCurrencyNetworkServiceModule;
import com.ru.crypto.di.modules.GlobalDataModule;
import com.ru.crypto.di.modules.GsonModule;
import com.ru.crypto.repositories.GlobalDataRepository;
import com.ru.crypto.ui.global.GlobalViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {GsonModule.class, CryptoCurrencyNetworkServiceModule.class, GlobalDataModule.class})
@Singleton
public interface GlobalDataRepoComponent {

    GlobalDataRepository getRepo();

    void inject(GlobalViewModel viewModel);
}
