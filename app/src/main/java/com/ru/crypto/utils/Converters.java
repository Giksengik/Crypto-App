package com.ru.crypto.utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.ru.crypto.models.Links;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

public class Converters {
    public static String encodeToBase64(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 90, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
    public static String getCurrencySymbol(String currency) {
        switch(currency){
            case "USD":
                return "$";
            default:
                return currency;
        }
    }
    public static String getFormattedDataStringByUnixTimestamp(double timeStamp) {
        Date timeMilliseconds = convertUnixTimestamp(timeStamp);
        DateFormat dateTimeFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
        return dateTimeFormat.format(timeMilliseconds);
    }
    public static String getFormattedWithHourDataStringByUnixTimestamp(double timeStamp) {
        Date timeMilliseconds = convertUnixTimestamp(timeStamp);
        DateFormat dateTimeFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT, Locale.getDefault());
        return getFormattedDataStringByUnixTimestamp(timeStamp) + " " + dateTimeFormat.format(timeMilliseconds);

    }
    private static Date convertUnixTimestamp(double timeStamp) {
       return new Date((long)timeStamp);
    }

    public static TreeMap<String, List<String>> getLinksMap(Links links) {
        TreeMap<String, List<String>> linksMap = new TreeMap<>();

        if(links != null){
            if(links.getAnnouncement() != null && links.getAnnouncement().size() != 0)
                linksMap.put("Announcement", links.getAnnouncement());
            if(links.getBlockchain() != null && links.getBlockchain().size() != 0)
                linksMap.put("Blockchain", links.getBlockchain());
            if(links.getChat() != null && links.getChat().size() != 0)
                linksMap.put("Chat", links.getChat());
            if(links.getFacebookName() != null) {
                List<String> facebookName = new ArrayList<>();
                facebookName.add(links.getFacebookName());
                linksMap.put("Facebook", facebookName);
            }
            if(links.getTelegramID() != null) {
                List<String> telegramID = new ArrayList<>();
                telegramID.add(links.getTelegramID());
                linksMap.put("Facebook", telegramID);
            }
            if(links.getHomepage() != null && links.getHomepage().size() != 0)
                linksMap.put("Chat", links.getHomepage());
            if(links.getOfficialForum() != null && links.getOfficialForum().size() != 0)
                linksMap.put("Chat", links.getOfficialForum());
        }
        return linksMap;
    }
}
