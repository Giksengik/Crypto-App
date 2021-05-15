package com.ru.crypto.repositories;

import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.ru.crypto.utils.Converters;
import com.ru.crypto.api.INetworkService;
import com.ru.crypto.db.CryptoArticleDatabase;
import com.ru.crypto.di.App;
import com.ru.crypto.models.CryptoArticle;
import com.ru.crypto.models.CryptoNews;
import com.ru.crypto.utils.NetworkManager;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CryptoNewsRepository {
    private INetworkService mNewsNetworkService;
    private CryptoArticleDatabase mDatabase;
    private LiveData<List<CryptoArticle>> mArticles;
    public CryptoNewsRepository(INetworkService networkService, CryptoArticleDatabase mDatabase) {
        this.mNewsNetworkService = networkService;
        this.mDatabase = mDatabase;
        mArticles = mDatabase.cryptoArticleDao()
                .getAll();
        loadNews();
    }
    public  LiveData<List<CryptoArticle>> getArticles() {
        return mArticles;
    }

    public void loadNews() {
        mNewsNetworkService.getJSONApi()
                .getAllLatestNews()
                .enqueue(new Callback<CryptoNews>() {
                    @Override
                    public void onResponse(Call<CryptoNews> call, Response<CryptoNews> response) {
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                mDatabase.cryptoArticleDao()
                                        .insertAll(response.body().getArticles());
                                }
                        }.start();
                    }

                    @Override
                    public void onFailure(Call<CryptoNews> call, Throwable t) {
                        if(NetworkManager.hasConnection(App.getInstance())){
                            loadNews();
                        } else Toast.makeText(App.getInstance(),"Fail to load data, check internet connection", Toast.LENGTH_LONG ).show();
                    }
                });
    }
    public void deleteAllArticles() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                mDatabase.cryptoArticleDao().deleteAll(mArticles.getValue());
            }
        }.start();

    }

}
