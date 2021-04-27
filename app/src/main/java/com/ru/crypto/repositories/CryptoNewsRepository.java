package com.ru.crypto.repositories;

import android.widget.ImageButton;

import com.ru.crypto.api.CryptoNewsNetworkService;
import com.ru.crypto.api.INetworkService;
import com.ru.crypto.db.CryptoArticleDatabase;
import com.ru.crypto.models.CryptoNews;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CryptoNewsRepository {
    INetworkService newsNetworkService;
    CryptoArticleDatabase database;
    public CryptoNewsRepository(INetworkService networkService, CryptoArticleDatabase database) {
        this.newsNetworkService = networkService;
        this.database = database;
        loadNews();
    }
    public void loadNews() {
        newsNetworkService.getJSONApi()
                .getLatestNews()
                .enqueue(new Callback<CryptoNews>() {
                    @Override
                    public void onResponse(Call<CryptoNews> call, Response<CryptoNews> response) {
                        CryptoNews news = response.body();
                        int a  = 5;
                    }

                    @Override
                    public void onFailure(Call<CryptoNews> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

}
