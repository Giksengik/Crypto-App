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

public class CryptoCurrencyNetworkService implements INetworkService {

    Retrofit retrofit;

    public CryptoCurrencyNetworkService(Retrofit retrofit){
        this.retrofit = retrofit;
    }

    public JSONPlaceholderAPI getJSONApi(){
        return retrofit.create(JSONPlaceholderAPI.class);
    }


}
