package com.ru.crypto.di.components;

import com.ru.crypto.api.INetworkService;
import com.ru.crypto.di.modules.CryptoCurrencyNetworkServiceModule;
import com.ru.crypto.di.modules.CryptoNewsModule;
import com.ru.crypto.di.modules.CryptoNewsNetworkServiceModule;
import com.ru.crypto.di.modules.CurrencyLinksModule;
import com.ru.crypto.di.modules.GsonModule;
import com.ru.crypto.models.CryptoCurrency;
import com.ru.crypto.models.CurrencyLinks;
import com.ru.crypto.repositories.CurrencyLinksRepository;
import com.ru.crypto.ui.currency_profile.links.CurrencyLinksViewModel;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Provides;

@Component(modules = {GsonModule.class, CryptoCurrencyNetworkServiceModule.class, CurrencyLinksModule.class})
@Singleton
public interface CurrencyLinksRepoComponent {

    CurrencyLinksRepository getRepo();

    void inject(CurrencyLinksViewModel viewModel);
}
