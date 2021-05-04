package com.ru.crypto.utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

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
}
