package com.ru.crypto.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.TreeMap;

public class Links {

    @SerializedName("homepage")
    @Expose
    List<String> homepage;

    @SerializedName("blockchain_site")
    @Expose
    List<String> blockchain;

    @SerializedName("official_forum_url")
    @Expose
    List<String> officialForum;

    @SerializedName("chat_url")
    @Expose
    List<String> chat;

    @SerializedName("announcement_url")
    @Expose
    List<String> announcement;

    @SerializedName("twitter_screen_name")
    @Expose
    String twitterName;

    @SerializedName("facebook_username")
    @Expose
    String facebookName;

    @SerializedName("telegram_channel_identifier")
    @Expose
    String telegramID;



    public List<String> getHomepage() {
        return homepage;
    }

    public List<String> getOfficialForum() {
        return officialForum;
    }

    public List<String> getBlockchain() {
        return blockchain;
    }

    public List<String> getChat() {
        return chat;
    }

    public List<String> getAnnouncement() {
        return announcement;
    }

    public String getTwitterName() {
        return twitterName;
    }

    public String getFacebookName() {
        return facebookName;
    }

    public String getTelegramID() {
        return telegramID;
    }
}
