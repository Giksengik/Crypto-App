package com.ru.crypto.utils;

import androidx.room.TypeConverter;


import com.google.gson.Gson;
import com.ru.crypto.models.Sparkline;


public class SparklineConverter {

    Gson gson;

    public SparklineConverter() {
        gson = new Gson();
    }

    @TypeConverter
    public String toJSON(Sparkline sparkline) {
        String json = gson.toJson(sparkline);
        return json;
    }

    @TypeConverter
    public Sparkline toSparkline(String data) {
        Sparkline sparkline = gson.fromJson(data,Sparkline.class);
        return sparkline;
    }
}
