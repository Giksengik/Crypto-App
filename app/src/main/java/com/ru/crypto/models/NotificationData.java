package com.ru.crypto.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class NotificationData {

    @Ignore public static final String TYPE_SINGLE = "single";
    @Ignore public static final String TYPE_CYCLICAL_PRICE = "cyclical_price";
    @Ignore public static final String TYPE_BORDER_EVENT = "border_event";

    @Ignore public static final String KEY_TO_SERIALIZE = "notification_data";

    @PrimaryKey(autoGenerate = true)
    int notificationID;

    String currencyID;

    double oldPrice;

    long intervalValueInMillis;

    double topBorder;

    double bottomBorder;

    String notificationType;

    long nextNotificationTime;


    public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    public String getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyID(String currencyID) {
        this.currencyID = currencyID;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }



    public double getTopBorder() {
        return topBorder;
    }

    public void setTopBorder(double topBorder) {
        this.topBorder = topBorder;
    }

    public double getBottomBorder() {
        return bottomBorder;
    }

    public void setBottomBorder(double bottomBorder) {
        this.bottomBorder = bottomBorder;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public long getNextNotificationTime() {
        return nextNotificationTime;
    }

    public void setNextNotificationTime(long nextNotificationTime) {
        this.nextNotificationTime = nextNotificationTime;
    }

    public long getIntervalValueInMillis() {
        return intervalValueInMillis;
    }

    public void setIntervalValueInMillis(long intervalValueInMillis) {
        this.intervalValueInMillis = intervalValueInMillis;
    }
}
