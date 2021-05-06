package com.ru.crypto.ui.market;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DiffUtil;


import com.ru.crypto.di.components.CryptoCurrencyRepoComponent;

import com.ru.crypto.di.components.DaggerCryptoCurrencyRepoComponent;
import com.ru.crypto.models.CryptoCurrency;
import com.ru.crypto.repositories.CryptoCurrencyRepository;

import java.util.List;

import javax.inject.Inject;

public class MarketViewModel extends ViewModel {

    @Inject
    CryptoCurrencyRepository mRepository;

    public MarketViewModel() {
        CryptoCurrencyRepoComponent component = DaggerCryptoCurrencyRepoComponent.create();
        component.inject(this);
    }

    public LiveData<List<CryptoCurrency>> getAllCurrencies() {
        return mRepository.getCurrencies();
    }

    public void updateCurrencies() {
        mRepository.loadCurrenciesInfo( 1);
    }

    public LiveData<DiffUtil.DiffResult> getDiffResult() {
        return mRepository.getCryptoCurrencyDiffResult();
    }

    public void countDiffResult (List<CryptoCurrency> oldCurrencies) {
        mRepository.countCryptoCurrencyDiffResult(oldCurrencies);
    }

    public void refreshData() {
        mRepository.refreshData();
    }

}