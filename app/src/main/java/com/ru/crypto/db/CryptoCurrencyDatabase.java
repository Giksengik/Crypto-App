package com.ru.crypto.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.ru.crypto.models.CryptoCurrency;
import com.ru.crypto.models.Sparkline;
import com.ru.crypto.utils.SparklineConverter;

@Database(entities = {CryptoCurrency.class}, version = 2, exportSchema = false)
public abstract class CryptoCurrencyDatabase extends RoomDatabase {

    public abstract CryptoCurrencyDao cryptoCurrencyDao();

}
