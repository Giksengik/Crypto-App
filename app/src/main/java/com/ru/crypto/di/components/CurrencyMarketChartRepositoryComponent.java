package com.ru.crypto.di.components;

import com.ru.crypto.di.modules.CryptoCurrencyNetworkServiceModule;
import com.ru.crypto.di.modules.CurrencyMarketChartRepositoryModule;
import com.ru.crypto.di.modules.GlobalDataModule;
import com.ru.crypto.di.modules.GsonModule;
import com.ru.crypto.repositories.CurrencyMarketChartRepository;
import com.ru.crypto.ui.currency_profile.general.CurrencyGeneralFragment;
import com.ru.crypto.ui.currency_profile.general.CurrencyGeneralViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {GsonModule.class, CryptoCurrencyNetworkServiceModule.class, CurrencyMarketChartRepositoryModule.class})
@Singleton
public interface CurrencyMarketChartRepositoryComponent {

    CurrencyMarketChartRepository getRepo();

    void inject(CurrencyGeneralViewModel viewModel);
}
