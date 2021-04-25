package com.ru.crypto.repositories;

import android.widget.ImageButton;

import com.ru.crypto.api.CryptoNewsNetworkService;
import com.ru.crypto.api.INetworkService;
import com.ru.crypto.models.CryptoNews;

public class CryptoNewsRepository {
    INetworkService newsNetworkService;
    public CryptoNewsRepository(INetworkService networkService) {
        this.newsNetworkService = networkService;
    }

}
