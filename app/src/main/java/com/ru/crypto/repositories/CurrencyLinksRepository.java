package com.ru.crypto.repositories;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ru.crypto.api.INetworkService;
import com.ru.crypto.di.App;
import com.ru.crypto.models.CurrencyLinks;
import com.ru.crypto.utils.NetworkManager;

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
                        if(NetworkManager.hasConnection(App.getInstance())){
                            loadLinks(currency);
                        } else Toast.makeText(App.getInstance(),"Fail to load data, check internet connection", Toast.LENGTH_LONG ).show();
                    }
                });
    }


}
