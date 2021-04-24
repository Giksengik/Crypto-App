package com.ru.crypto.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GlobalCryptoData {

    @SerializedName("data")
    @Expose
    CryptoData data;

    public CryptoData getData() {
        return data;
    }
}
