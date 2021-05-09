package com.ru.crypto.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CryptoNews {

    @SerializedName("Data")
    @Expose
    ArrayList<CryptoArticle> articles;


    public ArrayList<CryptoArticle> getArticles() {
        return articles;
    }
}
