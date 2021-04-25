package com.ru.crypto.di.components;

import com.ru.crypto.api.CryptoNewsNetworkService;
import com.ru.crypto.di.modules.CryptoNewsModule;
import com.ru.crypto.di.modules.CryptoNewsNetworkServiceModule;
import com.ru.crypto.di.modules.GsonModule;
import com.ru.crypto.repositories.CryptoNewsRepository;
import com.ru.crypto.ui.news.NewsViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {GsonModule.class, CryptoNewsNetworkServiceModule.class, CryptoNewsModule.class})
@Singleton
public interface CryptoNewsRepoComponent {

    CryptoNewsRepository getRepo();

    void inject(NewsViewModel viewModel);
}
