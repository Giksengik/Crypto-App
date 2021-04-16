package com.ru.crypto.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ru.crypto.api.NetworkService;
import com.ru.crypto.models.Cryptocurrency;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CryptoCurrencyRepository {

    NetworkService mNetworkService;
    MutableLiveData<List<Cryptocurrency>> mAllCurrencies;
    public CryptoCurrencyRepository(NetworkService networkService){
        this.mNetworkService = networkService;
        mAllCurrencies = new MutableLiveData<>();
    }

    public LiveData<List<Cryptocurrency>> getCurrencies(){
        return mAllCurrencies;
    }

    public void updateCurrencies () {
        mNetworkService.getJSONApi()
                .getDefaultInfo("USD","bitcoin")
                .enqueue(new Callback<List<Cryptocurrency>>() {
                    @Override
                    public void onResponse(Call<List<Cryptocurrency>> call, Response<List<Cryptocurrency>> response) {
                        mAllCurrencies.postValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Cryptocurrency>> call, Throwable t) {

                    }
                });
    }
}
