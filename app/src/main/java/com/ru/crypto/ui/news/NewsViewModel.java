package com.ru.crypto.ui.news;


import androidx.lifecycle.ViewModel;

import com.ru.crypto.di.components.CryptoNewsRepoComponent;
import com.ru.crypto.di.components.DaggerCryptoNewsRepoComponent;
import com.ru.crypto.repositories.CryptoNewsRepository;

import javax.inject.Inject;

public class NewsViewModel extends ViewModel {
    @Inject CryptoNewsRepository mRepository;

    public NewsViewModel() {
        CryptoNewsRepoComponent component = DaggerCryptoNewsRepoComponent.create();
        component.inject(this);
    }

}