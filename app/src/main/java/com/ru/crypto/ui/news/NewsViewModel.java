package com.ru.crypto.ui.news;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ru.crypto.di.components.CryptoNewsRepoComponent;


import com.ru.crypto.di.components.DaggerCryptoNewsRepoComponent;
import com.ru.crypto.models.CryptoArticle;
import com.ru.crypto.repositories.CryptoNewsRepository;

import java.util.List;

import javax.inject.Inject;

public class NewsViewModel extends ViewModel {
    @Inject CryptoNewsRepository mRepository;

    public NewsViewModel() {
        CryptoNewsRepoComponent component = DaggerCryptoNewsRepoComponent.create();
        component.inject(this);
    }
    public LiveData<List<CryptoArticle>> getArticles () {
        return mRepository.getArticles();
    }

}