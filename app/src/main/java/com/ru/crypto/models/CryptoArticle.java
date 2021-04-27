package com.ru.crypto.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class CryptoArticle {

    @SerializedName("id")
    @Expose
    @PrimaryKey
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
    @Ignore
    ArticleSource sourceInfo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArticleURL() {
        return articleURL;
    }

    public void setArticleURL(String articleURL) {
        this.articleURL = articleURL;
    }

    public String getArticleImageURL() {
        return articleImageURL;
    }

    public void setArticleImageURL(String articleImageURL) {
        this.articleImageURL = articleImageURL;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public ArticleSource getSourceInfo() {
        return sourceInfo;
    }

    public void setSourceInfo(ArticleSource sourceInfo) {
        this.sourceInfo = sourceInfo;
    }
}
