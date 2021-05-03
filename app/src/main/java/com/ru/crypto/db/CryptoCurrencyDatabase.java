package com.ru.crypto.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.ru.crypto.models.CryptoCurrency;

@Database(entities = {CryptoCurrency.class}, version = 2, exportSchema = false)
public abstract class CryptoCurrencyDatabase extends RoomDatabase {

    public abstract CryptoCurrencyDao cryptoCurrencyDao();

}
