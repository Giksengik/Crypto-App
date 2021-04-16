package com.ru.crypto.api;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    Retrofit retrofit;

    public NetworkService(Retrofit retrofit){
        this.retrofit = retrofit;
    }

    public JSONPlaceholderAPI getJSONApi(){
        return retrofit.create(JSONPlaceholderAPI.class);
    }
//
//
//    public static Date GetUTCdatetimeAsDate()
//    {
//        //note: doesn't check for null
//        return StringDateToDate(GetUTCdatetimeAsString());
//    }
//
//    public static String GetUTCdatetimeAsString()
//    {
//        final SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
//        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
//        final String utcTime = sdf.format(new Date());
//
//        return utcTime;
//    }
//
//    public static Date StringDateToDate(String StrDate)
//    {
//        Date dateToReturn = null;
//        SimpleDateFormat dateFormat = new SimpleDateFormat(DATEFORMAT);
//
//        try
//        {
//            dateToReturn = (Date)dateFormat.parse(StrDate);
//        }
//        catch (ParseException e)
//        {
//            e.printStackTrace();
//        }
//
//        return dateToReturn;
//    }
}
