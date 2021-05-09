package com.ru.crypto.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.TreeMap;

public class CurrencyLinks {

    @SerializedName("id")
    @Expose
    String id;

    @SerializedName("symbol")
    @Expose
    String symbol;

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("block_time_in_minutes")
    @Expose
    String blockTimeMinutes;


    @SerializedName("hashing_algorithm")
    @Expose
    String hashingAlgorithm;

    @SerializedName("description")
    @Expose
    TreeMap<String, String> description;

    @SerializedName("links")
    @Expose
    Links links;

    public String getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getBlockTimeMinutes() {
        return blockTimeMinutes;
    }

    public String getHashingAlgorithm() {
        return hashingAlgorithm;
    }

    public Links getLinks() {
        return links;
    }

    public TreeMap<String, String> getDescription() {
        return description;
    }
}
