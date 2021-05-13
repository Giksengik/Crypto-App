package com.ru.crypto.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ru.crypto.models.NotificationData;

import java.util.List;

@Dao
public interface NotificationDao {

    @Query("Select * from notificationdata")
    LiveData<List<NotificationData>> getAllNotifications();

    @Insert
    void addNotification(NotificationData notificationData);

    @Delete
    void deleteNotification(NotificationData notificationData);

    @Delete
    void deleteNotifications(List<NotificationData> notificationData);

    @Query("Select * from notificationdata where nextNotificationTime <= :timeInMillis")
    List<NotificationData> getCurrentNotifications(long timeInMillis);

    @Update
    void updateNotification(NotificationData notificationData);

    @Query("Select * from notificationdata where notificationType = :borderType ")
    List<NotificationData> getNotificationsWithType(String borderType);
}
