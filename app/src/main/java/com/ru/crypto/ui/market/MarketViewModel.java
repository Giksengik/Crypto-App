package com.ru.crypto.ui.market;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ru.crypto.di.components.CryptoCurrencyRepositoryComponent;
import com.ru.crypto.di.components.DaggerCryptoCurrencyRepositoryComponent;
import com.ru.crypto.di.modules.CryptoCurrencyRepoModule;
import com.ru.crypto.models.Cryptocurrency;
import com.ru.crypto.repositories.CryptoCurrencyRepository;

import java.util.Currency;
import java.util.List;

import javax.inject.Inject;

public class MarketViewModel extends ViewModel {
    @Inject
    CryptoCurrencyRepository mRepository;

    public MarketViewModel() {
        CryptoCurrencyRepositoryComponent component = DaggerCryptoCurrencyRepositoryComponent.create();
        component.inject(this);
    }

    public LiveData<List<Cryptocurrency>> getAllCurrencies() {
        return mRepository.getCurrencies();
    }
    public void updateCurrencies() {
        mRepository.updateCurrencies();
    }

}