package com.ru.crypto.ui.global;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ru.crypto.di.components.DaggerRepositoryComponent;
import com.ru.crypto.di.components.RepositoryComponent;
import com.ru.crypto.models.GlobalCryptoData;
import com.ru.crypto.repositories.CryptoCurrencyRepository;
import com.ru.crypto.repositories.GlobalDataRepository;

import javax.inject.Inject;

public class GlobalViewModel extends ViewModel {

    @Inject
    GlobalDataRepository mRepository;

    public GlobalViewModel() {
        RepositoryComponent component = DaggerRepositoryComponent.create();
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
}
