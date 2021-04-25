package com.ru.crypto.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CryptoArticle {

    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("guid")
    @Expose
    String articleURL;

    @SerializedName("imageurl")
    @Expose
    String articleImageURL;

    @SerializedName("title")
    @Expose
    String articleTitle;

    @SerializedName("source")
    @Expose
    String source;

    @SerializedName("tags")
    @Expose
    String tags;

    @SerializedName("categories")
    @Expose
    String categories;

    @SerializedName("source_info")
    @Expose
    ArticleSource sourceInfo;

}
