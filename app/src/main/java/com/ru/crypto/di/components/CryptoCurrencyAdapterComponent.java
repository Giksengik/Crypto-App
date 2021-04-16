package com.ru.crypto.di.components;

import com.ru.crypto.adapters.CryptoCurrencyAdapter;
import com.ru.crypto.models.Cryptocurrency;
import com.ru.crypto.ui.market.MarketFragment;
import com.ru.crypto.ui.market.MarketViewModel;

import dagger.Component;

@Component
public interface CryptoCurrencyAdapterComponent {
    CryptoCurrencyAdapter getAdapter();
    void inject(MarketFragment marketFragment);
}
