package com.ru.crypto.api;

import com.ru.crypto.models.CryptoID;
import com.ru.crypto.models.CryptoCurrency;
import com.ru.crypto.models.CryptoNews;
import com.ru.crypto.models.GlobalCryptoData;
import com.ru.crypto.models.HistoricalCurrencyData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JSONPlaceholderAPI {

    // CRYPTO NEWS
    String apiKey = "be192f39a76e19ab11d3cfe72e31952110b643da9fb0cad97df93ea312a4b626";


    //COIN GECKO
    String defaultOrder = "market_cap_desc";
    int defaultPerPage = 100;
    String defaultPriceChangePercentage = "24H";

    //COIN GECKO
    @GET("/api/v3/coins/markets?order=" + defaultOrder + "&per_page=" +
            defaultPerPage + "&price_change_percentage=" + defaultPriceChangePercentage + "&sparkline=true")
    Call<List<CryptoCurrency>> getDefaultInfo(@Query("vs_currency") String currency, @Query("ids") String ids);

    @GET("/api/v3/coins/markets?order=" + defaultOrder + "&per_page=" + defaultPerPage  +"&page=1&sparkline=true")
    Call<List<CryptoID>> getDefaultList(@Query("vs_currency") String currency);

    @GET("/api/v3/global")
    Call<GlobalCryptoData> getGlobalData();

    @GET("/api/v3/coins/bitcoin/market_chart?vs_currency=usd&days=max")
    Call<HistoricalCurrencyData> getAllBitcoinData();
    @GET("/api/v3/coins/{currency}/market_chart?vs_currency=usd")
    Call<HistoricalCurrencyData> getCurrencyDataForTimeRange(@Path("currency") String currency, @Query("days") String  days);
    // CRYPTO NEWS

    @GET("/data/v2/news/?lang=EN&api_key=be192f39a76e19ab11d3cfe72e31952110b643da9fb0cad97df93ea312a4b626")
    Call<CryptoNews> getLatestNews();
}
