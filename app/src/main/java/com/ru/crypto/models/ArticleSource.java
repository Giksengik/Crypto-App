package com.ru.crypto.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArticleSource {

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("lang")
    @Expose
    String lang;

    @SerializedName("img")
    @Expose
    String imgURL;
}
