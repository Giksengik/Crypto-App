package com.ru.crypto.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrencyMarketChart {

    @SerializedName("prices")
    @Expose
    List<List<Double>> prices;

    @SerializedName("market_caps")
    @Expose
    List<List<Double>> marketCaps;

    @SerializedName("total_volumes")
    @Expose
    List<List<Double>> totalVolumes;

    public List<List<Double>> getPrices() {
        return prices;
    }

    public void setPrices(List<List<Double>> prices) {
        this.prices = prices;
    }

    public List<List<Double>> getMarketCaps() {
        return marketCaps;
    }

    public void setMarketCaps(List<List<Double>> marketCaps) {
        this.marketCaps = marketCaps;
    }

    public List<List<Double>> getTotalVolumes() {
        return totalVolumes;
    }

    public void setTotalVolumes(List<List<Double>> totalVolumes) {
        this.totalVolumes = totalVolumes;
    }
}
