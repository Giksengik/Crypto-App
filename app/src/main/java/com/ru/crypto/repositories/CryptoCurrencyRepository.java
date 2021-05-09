package com.ru.crypto.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.DiffUtil;

import com.ru.crypto.db.CryptoCurrencyDatabase;
import com.ru.crypto.utils.Converters;
import com.ru.crypto.adapters.CryptoCurrencyDiffUtilCallback;
import com.ru.crypto.api.INetworkService;
import com.ru.crypto.models.CryptoID;
import com.ru.crypto.models.CryptoCurrency;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CryptoCurrencyRepository {

    private static String CURRENT_CURRENCY = "USD";
    private static int PAGE_LIMIT = 4;
    private CryptoCurrencyDatabase mDatabase;
    INetworkService mCryptoCurrencyNetworkService;
    LiveData<List<CryptoCurrency>> mAllCurrencies;
    MutableLiveData<DiffUtil.DiffResult> mCryptoCurrencyDiffResult;

    public CryptoCurrencyRepository(INetworkService cryptoCurrencyNetworkService, CryptoCurrencyDatabase database) {
        this.mCryptoCurrencyNetworkService = cryptoCurrencyNetworkService;
        this.mDatabase = database;
        mAllCurrencies = mDatabase.cryptoCurrencyDao().getAll();
        mCryptoCurrencyDiffResult = new MutableLiveData<>();
    }

    public LiveData<List<CryptoCurrency>> getCurrencies(){
        return mAllCurrencies;
    }

    public LiveData<DiffUtil.DiffResult> getCryptoCurrencyDiffResult() {
        return mCryptoCurrencyDiffResult;
    }
    public void countCryptoCurrencyDiffResult(List<CryptoCurrency> oldData) {
        new Thread(){
            @Override
            public void run() {
                CryptoCurrencyDiffUtilCallback diffUtilCallback =
                        new CryptoCurrencyDiffUtilCallback(oldData, mAllCurrencies.getValue());
                mCryptoCurrencyDiffResult.postValue(DiffUtil.calculateDiff(diffUtilCallback));
            }
        }.start();
    }


    private void loadCurrenciesInfo(String currencies) {
        mCryptoCurrencyNetworkService.getJSONApi()
                .getDefaultInfo(CURRENT_CURRENCY,currencies)
                .enqueue(new Callback<List<CryptoCurrency>>() {
                    @Override
                    public void onResponse(Call<List<CryptoCurrency>> call, Response<List<CryptoCurrency>> response) {
                        List<CryptoCurrency> currenciesList = response.body();
                        if (currenciesList != null) {
                            new Thread(){
                                @Override
                                public void run() {
                                    super.run();
                                    if(mDatabase.cryptoCurrencyDao().getRowCount() < 400)
                                    {
                                        mDatabase.cryptoCurrencyDao().insertAll(currenciesList);
                                    }
                                    else
                                        mDatabase.cryptoCurrencyDao().updateAll(currenciesList);
                                }
                            }.start();
                        }
                    }
                    @Override
                    public void onFailure(Call<List<CryptoCurrency>> call, Throwable t) {

                    }
                });
    }
    public void loadCurrenciesInfo(int page) {
        final int currentPage = page;
        final int nextPage = ++page;
        Single.create((SingleOnSubscribe<List<CryptoID>>) emitter -> mCryptoCurrencyNetworkService.getJSONApi()
                .getCryptoCurrencyList(CURRENT_CURRENCY,currentPage)
                .enqueue(new Callback<List<CryptoID>>() {
                    @Override
                    public void onResponse(Call<List<CryptoID>> call, Response<List<CryptoID>> response) {
                        if(response.body() != null)
                            emitter.onSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<CryptoID>> call, Throwable t) {
                        t.printStackTrace();
                    }
                })).subscribeOn(Schedulers.newThread())
                .subscribe(new DisposableSingleObserver<List<CryptoID>>() {
                    @Override
                    public void onSuccess(@NonNull List<CryptoID> cryptoIDS) {
                        loadCurrenciesInfo(parseCryptoIDsToOneString(cryptoIDS));
                        if(nextPage <= PAGE_LIMIT)
                            loadCurrenciesInfo(nextPage);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }


    private String parseCryptoIDsToOneString(List<CryptoID> ids){
        StringBuilder result = new StringBuilder();
        for(CryptoID item : ids) {
            result.append(item.getId());
            if(item != ids.get(ids.size()-1)){
                result.append(',');
            }
        }
        return result.toString();
    }

    public void updateCurrency(CryptoCurrency currency) {
        mDatabase.cryptoCurrencyDao().updateCurrency(currency);
    }
}
