package com.ru.crypto.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Sparkline {
    @SerializedName("price")
    @Expose
    List<Double> priceChange;

    public List<Double> getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(List<Double> priceChange) {
        this.priceChange = priceChange;
    }
}
