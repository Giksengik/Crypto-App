package com.ru.crypto.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class HistoricalCurrencyData {

    @SerializedName("prices")
    @Expose
    List<List<Double>> prices;

    public List<List<Double>> getPrices() {
        return prices;
    }
}
