package com.ru.crypto.ui.global;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


import com.ru.crypto.di.components.DaggerGlobalDataRepoComponent;
import com.ru.crypto.di.components.GlobalDataRepoComponent;
import com.ru.crypto.models.GlobalCryptoData;
import com.ru.crypto.models.HistoricalCurrencyData;
import com.ru.crypto.repositories.GlobalDataRepository;

import javax.inject.Inject;

public class GlobalViewModel extends ViewModel {

    @Inject
    GlobalDataRepository mRepository;

    public GlobalViewModel() {
        GlobalDataRepoComponent component = DaggerGlobalDataRepoComponent.create();
        component.inject(this);
    }

    public LiveData<GlobalCryptoData> getGlobalData () {
        return mRepository.getGlobalData();
    }

    public void loadGlobalData () {
        mRepository.loadGlobalData();
    }

    public void loadBitcoinInfo () {
        mRepository.loadAllBitcoinData();
    }

    public LiveData<HistoricalCurrencyData> getBitcoinData () {
        return mRepository.getBitcoinGlobalData();
    }
}
