package com.ru.crypto.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ru.crypto.api.INetworkService;
import com.ru.crypto.models.CurrencyLinks;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrencyLinksRepository {

    private INetworkService networkService;
    private MutableLiveData<CurrencyLinks> currencyLinks;

    public CurrencyLinksRepository(INetworkService networkService) {
        this.networkService = networkService;
        currencyLinks = new MutableLiveData<>();
    }

    public LiveData<CurrencyLinks> getLinks() {
        return currencyLinks;
    }

    public void loadLinks(String currency) {
        networkService.getJSONApi()
                .getCurrencyLinks(currency)
                .enqueue(new Callback<CurrencyLinks>() {
                    @Override
                    public void onResponse(Call<CurrencyLinks> call, Response<CurrencyLinks> response) {
                        if(response.body() != null){
                            currencyLinks.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<CurrencyLinks> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }


}
