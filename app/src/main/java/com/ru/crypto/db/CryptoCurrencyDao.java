package com.ru.crypto.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ru.crypto.models.CryptoArticle;
import com.ru.crypto.models.CryptoCurrency;

import java.util.List;

@Dao
public interface CryptoCurrencyDao {

    @Query("SELECT * FROM cryptocurrency ")
    LiveData<List<CryptoCurrency>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CryptoCurrency> cryptoCurrencies);

    @Update
    void updateAll(List<CryptoCurrency> cryptoCurrencies);
}
