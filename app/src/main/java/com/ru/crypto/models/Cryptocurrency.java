package com.ru.crypto.models;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cryptocurrency {
    @SerializedName("id")
    @Expose
    String id;

    @SerializedName("symbol")
    @Expose
    String symbol;

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("image")
    @Expose
    String imageUrl;


    @SerializedName("market_cap_rank")
    @Expose
    int marketCapRank;

    @SerializedName("market_cap")
    @Expose
    double marketCap;

    @SerializedName("fully_diluted_valuation")
    @Expose
    double fullyDilutedValuation;

    @SerializedName("total_volume")
    @Expose
    double totalVolume;


    @SerializedName("high_24h")
    @Expose
    double high24H;

    @SerializedName("low_24h")
    @Expose
    double low24H;

    @SerializedName("current_price")
    @Expose
    double currentPrice;

    @SerializedName("price_change_24h")
    @Expose
    double priceChange24H;

    @SerializedName("price_change_percentage_24h")
    @Expose
    double priceChangePercentage24H;

    @SerializedName("price")
    @Expose
    List<Double> priceChange;


    @SerializedName("last_updated")
    @Expose
    String lastUpdated;

    String iconString;

    Bitmap icon;

    public String getIconString() {
        return iconString;
    }

    public void setIconString(String iconString) {
        this.iconString = iconString;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getMarketCapRank() {
        return marketCapRank;
    }

    public void setMarketCapRank(int marketCapRank) {
        this.marketCapRank = marketCapRank;
    }

    public double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(double marketCap) {
        this.marketCap = marketCap;
    }

    public double getFullyDilutedValuation() {
        return fullyDilutedValuation;
    }

    public void setFullyDilutedValuation(double fullyDilutedValuation) {
        this.fullyDilutedValuation = fullyDilutedValuation;
    }

    public double getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(double totalVolume) {
        this.totalVolume = totalVolume;
    }

    public double getHigh24H() {
        return high24H;
    }

    public void setHigh24H(double high24H) {
        this.high24H = high24H;
    }

    public double getLow24H() {
        return low24H;
    }

    public void setLow24H(double low24H) {
        this.low24H = low24H;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getPriceChange24H() {
        return priceChange24H;
    }

    public void setPriceChange24H(double priceChange24H) {
        this.priceChange24H = priceChange24H;
    }

    public double getPriceChangePercentage24H() {
        return priceChangePercentage24H;
    }

    public void setPriceChangePercentage24H(double priceChangePercentage24H) {
        this.priceChangePercentage24H = priceChangePercentage24H;
    }

    public List<Double> getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(List<Double> priceChange) {
        this.priceChange = priceChange;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String curency) {
        this.currency = curency;
    }

    String currency = "USD";
}
