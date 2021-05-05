package com.ru.crypto.repositories;

import androidx.lifecycle.LiveData;

import com.ru.crypto.utils.Converters;
import com.ru.crypto.api.INetworkService;
import com.ru.crypto.db.CryptoArticleDatabase;
import com.ru.crypto.di.App;
import com.ru.crypto.models.CryptoArticle;
import com.ru.crypto.models.CryptoNews;
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
                                if(response.body() != null) {
                                    List<CryptoArticle> articles = response.body().getArticles();
                                    for (int i = 0; i < articles.size(); i++) {
                                            try {
                                                articles.get(i).setIconString(
                                                        Converters.encodeToBase64(Picasso.with(App.getInstance())
                                                                .load(articles.get(i).getArticleImageURL()).get()));
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                    mDatabase.cryptoArticleDao()
                                            .insertAll(response.body().getArticles());
                                }
                        }.start();
                    }

                    @Override
                    public void onFailure(Call<CryptoNews> call, Throwable t) {
                        t.printStackTrace();
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
