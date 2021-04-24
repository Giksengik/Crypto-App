package com.ru.crypto.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ru.crypto.api.CryptoCurrencyNetworkService;
import com.ru.crypto.api.INetworkService;
import com.ru.crypto.models.GlobalCryptoData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GlobalDataRepository {

    private INetworkService mNetworkService;
    private MutableLiveData<GlobalCryptoData> mGlobalCryptoData;

    public GlobalDataRepository (INetworkService networkService) {
        this.mNetworkService = networkService;
        mGlobalCryptoData = new MutableLiveData<>();
    }

    public LiveData<GlobalCryptoData> getGlobalData() {
        return mGlobalCryptoData;
    }

    public void loadGlobalData() {
        mNetworkService.getJSONApi()
                .getGlobalData()
                .enqueue(new Callback<GlobalCryptoData>() {
                    @Override
                    public void onResponse(Call<GlobalCryptoData> call, Response<GlobalCryptoData> response) {
                        if(response.body() != null) {
                            mGlobalCryptoData.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<GlobalCryptoData> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

}
