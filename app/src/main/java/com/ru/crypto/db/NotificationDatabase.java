package com.ru.crypto.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.ru.crypto.models.NotificationData;

@Database(entities = {NotificationData.class}, version = 1, exportSchema = false)
public abstract class NotificationDatabase extends RoomDatabase {

    public abstract NotificationDao notificationDao();

}
