package com.ru.crypto.ui.currency_profile.general;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ru.crypto.di.components.CurrencyMarketChartRepositoryComponent;

import com.ru.crypto.di.components.DaggerCurrencyMarketChartRepositoryComponent;
import com.ru.crypto.models.CurrencyMarketChart;
import com.ru.crypto.repositories.CurrencyMarketChartRepository;

import javax.inject.Inject;

public class CurrencyGeneralViewModel extends ViewModel {

    @Inject
    CurrencyMarketChartRepository repository;

    public CurrencyGeneralViewModel() {
        CurrencyMarketChartRepositoryComponent component = DaggerCurrencyMarketChartRepositoryComponent.create();
        component.inject(this);
    }
    public LiveData<CurrencyMarketChart> getMarketChart (){
        return repository.getCurrencyMarketChart();
    }

    public void loadCurrencyMarketChartByTimeRange(String currency, String timeRange) {
        repository.loadCurrencyMarketsChartByTimeRange(currency, timeRange);
    }
}
