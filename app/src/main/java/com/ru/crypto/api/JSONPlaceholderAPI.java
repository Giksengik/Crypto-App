package com.ru.crypto.api;

import com.ru.crypto.models.CryptoCurrencyName;
import com.ru.crypto.models.CryptoID;
import com.ru.crypto.models.CryptoCurrency;
import com.ru.crypto.models.CryptoNews;
import com.ru.crypto.models.CurrencyLinks;
import com.ru.crypto.models.CurrencyMarketChart;
import com.ru.crypto.models.GlobalCryptoData;
import com.ru.crypto.models.HistoricalCurrencyData;

import java.util.Currency;
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
    int defaultPerPage = 50;
    String defaultPriceChangePercentage = "24H";

    //COIN GECKO


    @GET("/api/v3/coins/markets?order=" + defaultOrder + "&per_page=" +
            defaultPerPage + "&price_change_percentage=" + defaultPriceChangePercentage + "&sparkline=true")
    Call<List<CryptoCurrency>> getDefaultInfo(@Query("vs_currency") String currency, @Query("ids") String ids);

    @GET("/api/v3/coins/markets?order=" + defaultOrder + "&per_page=" + defaultPerPage  +"&sparkline=true")
    Call<List<CryptoID>> getCryptoCurrencyList(@Query("vs_currency") String currency, @Query("page") int page);

    @GET("/api/v3/global")
    Call<GlobalCryptoData> getGlobalData();

    @GET("/api/v3/coins/bitcoin/market_chart?vs_currency=usd&days=max")
    Call<HistoricalCurrencyData> getAllBitcoinData();

    @GET("/api/v3/coins/{currency}/market_chart?vs_currency=usd")
    Call<HistoricalCurrencyData> getCurrencyDataForTimeRange(@Path("currency") String currency, @Query("days") String  days);

    @GET("/api/v3/coins/{currency}/market_chart?vs_currency=usd")
    Call<CurrencyMarketChart> getCurrencyMarketChart(@Path("currency") String currencyID, @Query("days") String days);

    @GET("/api/v3/coins/{currency}?tickers=false&market_data=false&community_data=false&developer_data=false")
    Call<CurrencyLinks> getCurrencyLinks(@Path("currency") String currency);

    @GET("/api/v3/coins/list")
    Call<List<CryptoCurrencyName>> getAllCurrencies();

    // CRYPTO NEWS

    @GET("/data/v2/news/?lang=EN&api_key=be192f39a76e19ab11d3cfe72e31952110b643da9fb0cad97df93ea312a4b626")
    Call<CryptoNews> getAllLatestNews();
}
