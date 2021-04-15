package com.ru.crypto.api;

import com.ru.crypto.models.Cryptocurrency;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JSONPlaceholderAPI {
    String defaultOrder = "market_cap_desc";
    int defaultPerPage = 100;
    String defaultPriceChangePercentage = "24H";
    @GET("/api/v3/coins/markets?order=" + defaultOrder + "&per_page=" +
            defaultPerPage + "&price_change_percentage=" + defaultPriceChangePercentage + "&sparkline=true")
    Call<List<Cryptocurrency>> getDefaultInfo(@Query("vs_currency") String currency, @Query("ids") String ids);

}
