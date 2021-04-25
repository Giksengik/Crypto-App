package com.ru.crypto.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.TreeMap;

public class HistoricalCurrencyData {

    @SerializedName("prices")
    @Expose
    ArrayList<ArrayList<Double>> prices;
}
