package com.ru.crypto.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ru.crypto.api.CryptoCurrencyNetworkService;
import com.ru.crypto.api.INetworkService;
import com.ru.crypto.models.GlobalCryptoData;
import com.ru.crypto.models.HistoricalCurrencyData;
import com.ru.crypto.utils.factories.DefaultLineChartTuner;
import com.ru.crypto.utils.factories.DefaultLineChartTunerFactory;
import com.ru.crypto.utils.factories.DefaultPieChartTunerFactory;
import com.ru.crypto.utils.factories.ILineChartTuner;
import com.ru.crypto.utils.factories.IPieChartTuner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GlobalDataRepository {

    private INetworkService mNetworkService;
    private MutableLiveData<GlobalCryptoData> mGlobalCryptoData;
    private MutableLiveData<HistoricalCurrencyData> bitcoinData;

    public GlobalDataRepository (INetworkService networkService) {
        this.mNetworkService = networkService;
        mGlobalCryptoData = new MutableLiveData<>();
        bitcoinData = new MutableLiveData<>();
        loadAllBitcoinData();
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
    public void loadAllBitcoinData() {
        mNetworkService.getJSONApi()
                .getAllBitcoinData()
                .enqueue(new Callback<HistoricalCurrencyData>() {
                    @Override
                    public void onResponse(Call<HistoricalCurrencyData> call, Response<HistoricalCurrencyData> response) {
                        if(response.body() != null) {
                            bitcoinData.setValue(response.body());
                        }

                    }

                    @Override
                    public void onFailure(Call<HistoricalCurrencyData> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public LiveData<HistoricalCurrencyData> getBitcoinGlobalData() {
        return bitcoinData;
    }

    public ILineChartTuner getDefaultLineChartTuner() {
          DefaultLineChartTunerFactory factory = new DefaultLineChartTunerFactory();
      return factory.getLineChartTuner();
    }

    public IPieChartTuner getDefaultPieChartTuner() {
        DefaultPieChartTunerFactory factory = new DefaultPieChartTunerFactory();
        return factory.getTuner();
    }

}
