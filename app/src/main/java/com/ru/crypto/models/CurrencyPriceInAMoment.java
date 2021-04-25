package com.ru.crypto.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrencyPriceInAMoment {
    @SerializedName("data")
    @Expose
    double price;
    int time;
}
