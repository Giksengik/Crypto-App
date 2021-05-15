package com.ru.crypto.repositories;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ru.crypto.adapters.TimeRange;
import com.ru.crypto.api.INetworkService;
import com.ru.crypto.di.App;
import com.ru.crypto.models.CurrencyMarketChart;
import com.ru.crypto.utils.NetworkManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrencyMarketChartRepository {

    private MutableLiveData<CurrencyMarketChart> mCurrencyMarketChart;
    private INetworkService networkService;

    public CurrencyMarketChartRepository (INetworkService service) {
        this.networkService = service;
        mCurrencyMarketChart = new MutableLiveData<>();
    }

    public LiveData<CurrencyMarketChart> getCurrencyMarketChart() {
        return mCurrencyMarketChart;
    }

    public void loadCurrencyMarketsChartByTimeRange(String currency, String timeRange) {
            if(timeRange.equals(TimeRange.ALL_TIME_RANGE.toString()))
                loadMarketChartDataInRange(currency,"max");
            else if (timeRange.equals(TimeRange.THREE_YEAR_TIME_RANGE.toString()))
                loadMarketChartDataInRange(currency, "1100");
            else if (timeRange.equals(TimeRange.ONE_YEAR_TIME_RANGE.toString()))
                loadMarketChartDataInRange(currency, "365");
            else if (timeRange.equals(TimeRange.SIX_MONTH_TIME_RANGE.toString()))
                loadMarketChartDataInRange(currency, "190");
            else if (timeRange.equals(TimeRange.THREE_MONTH_TIME_RANGE.toString()))
                loadMarketChartDataInRange(currency, "100");
            else if (timeRange.equals(TimeRange.ONE_MONTH_TIME_RANGE.toString()))
                loadMarketChartDataInRange(currency, "31");
            else if (timeRange.equals(TimeRange.ONE_WEEK_TIME_RANGE.toString()))
                loadMarketChartDataInRange(currency, "7");
            else if (timeRange.equals(TimeRange.ONE_DAY_TIME_RANGE.toString()))
                loadMarketChartDataInRange(currency, "1");

        }
        private void loadMarketChartDataInRange(String currency, String range) {
            networkService.getJSONApi()
                    .getCurrencyMarketChart(currency, range)
                    .enqueue(new Callback<CurrencyMarketChart>() {
                        @Override
                        public void onResponse(Call<CurrencyMarketChart> call, Response<CurrencyMarketChart> response) {
                            if(response.body() != null) {
                                mCurrencyMarketChart.setValue(response.body());
                            }
                        }

                        @Override
                        public void onFailure(Call<CurrencyMarketChart> call, Throwable t) {
                            if(NetworkManager.hasConnection(App.getInstance())){
                                loadMarketChartDataInRange(currency, range);
                            } else Toast.makeText(App.getInstance(),"Fail to load data, check internet connection", Toast.LENGTH_LONG ).show();
                        }
                    });
        }



}
