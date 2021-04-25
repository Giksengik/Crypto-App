package com.ru.crypto.di.components;

import com.ru.crypto.adapters.CryptoCurrencyAdapter;
import com.ru.crypto.api.CryptoCurrencyNetworkService;
import com.ru.crypto.di.modules.CryptoCurrencyModule;
import com.ru.crypto.di.modules.GsonModule;
import com.ru.crypto.ui.market.MarketFragment;

import javax.inject.Singleton;

import dagger.Component;

@Component
@Singleton
public interface CryptoCurrencyAdapterComponent {
    CryptoCurrencyAdapter getAdapter();
    void inject(MarketFragment marketFragment);
}
