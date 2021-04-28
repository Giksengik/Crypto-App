package com.ru.crypto.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ru.crypto.models.CryptoArticle;

import java.util.List;

@Dao
public interface CryptoArticleDao {

    @Query("SELECT * FROM cryptoarticle ")
    LiveData<List<CryptoArticle>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CryptoArticle> articles);

    @Delete
    void deleteAll(List<CryptoArticle> articles);


}
