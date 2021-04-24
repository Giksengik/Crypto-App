package com.ru.crypto.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.TreeMap;

public class CryptoData {

    @SerializedName("market_cap_change_percentage_24h_usd")
    @Expose
    double marketCapChangePercentage24H;

    @SerializedName("active_cryptocurrencies")
    @Expose
    int activeCryptoCurrenciesNum;

    @SerializedName("markets")
    @Expose
    int marketsNum;

    @SerializedName("total_market_cap")
    @Expose
    TreeMap<String , Double> totalMarketCap;

    @SerializedName("total_volume")
    @Expose
    TreeMap<String , Double> totalVolume;

    @SerializedName("market_cap_percentage")
    @Expose
    TreeMap<String , Double> marketCapPercentage;

    public double getMarketCapChangePercentage24H() {
        return marketCapChangePercentage24H;
    }

    public int getActiveCryptoCurrenciesNum() {
        return activeCryptoCurrenciesNum;
    }

    public int getMarketsNum() {
        return marketsNum;
    }

    public TreeMap<String, Double> getTotalMarketCap() {
        return totalMarketCap;
    }

    public TreeMap<String, Double> getTotalVolume() {
        return totalVolume;
    }

    public TreeMap<String, Double> getMarketCapPercentage() {
        return marketCapPercentage;
    }
}
