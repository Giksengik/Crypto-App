package com.ru.crypto.api;

import retrofit2.Retrofit;

public class CryptoNewsNetworkService implements INetworkService{
    Retrofit retrofit;

    public CryptoNewsNetworkService(Retrofit retrofit){
        this.retrofit = retrofit;
    }

    @Override
    public JSONPlaceholderAPI getJSONApi() {
        return retrofit.create(JSONPlaceholderAPI.class);
    }

}
