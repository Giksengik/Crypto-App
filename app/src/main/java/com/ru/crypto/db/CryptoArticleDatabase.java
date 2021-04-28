package com.ru.crypto.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.ru.crypto.models.CryptoArticle;

@Database(entities = {CryptoArticle.class}, version = 1 , exportSchema = false)
public abstract class CryptoArticleDatabase extends RoomDatabase {
    public abstract CryptoArticleDao cryptoArticleDao();
}
