package com.ru.crypto.ui.currency_profile.links;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ru.crypto.di.components.CurrencyLinksRepoComponent;
import com.ru.crypto.di.components.DaggerCurrencyLinksRepoComponent;
import com.ru.crypto.models.CurrencyLinks;
import com.ru.crypto.repositories.CurrencyLinksRepository;
import com.ru.crypto.ui.currency_profile.general.CurrencyGeneralViewModel;

import javax.inject.Inject;

public class CurrencyLinksViewModel extends ViewModel {

    @Inject
    CurrencyLinksRepository repository;

    public CurrencyLinksViewModel() {
        CurrencyLinksRepoComponent  component = DaggerCurrencyLinksRepoComponent.create();
        component.inject(this);
    }

    public void loadLinks(String currency) {
        repository.loadLinks(currency);
    }

    public LiveData<CurrencyLinks> getLinks () {
        return repository.getLinks();
    }


}
