package com.ru.crypto.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CryptoID {
    @SerializedName("id")
    @Expose
    String id;

    @SerializedName("symbol")
    @Expose
    String symbol;

    @SerializedName("name")
    @Expose
    String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
