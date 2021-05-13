package com.ru.crypto.ui.notifications;

import android.app.Notification;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ru.crypto.di.components.DaggerNotificationRepoComponent;
import com.ru.crypto.di.components.NotificationRepoComponent;
import com.ru.crypto.models.CryptoCurrencyName;
import com.ru.crypto.models.NotificationData;
import com.ru.crypto.repositories.NotificationRepository;

import java.util.List;

import javax.inject.Inject;

public class NotificationsViewModel extends ViewModel {
    @Inject
    NotificationRepository notificationRepository;

    public NotificationsViewModel()  {
        NotificationRepoComponent component = DaggerNotificationRepoComponent.create();
        component.inject(this);
    }

    public void loadAllCurrenciesNames() {
        notificationRepository.loadAllCurrencies();
    }

    public LiveData<List<CryptoCurrencyName>> getCurrencyNames() {
        return notificationRepository.getCurrencies();
    }

    public void addNotification(NotificationData notificationData) {
        notificationRepository.addNotification(notificationData);
    }

    public LiveData<List<NotificationData>> getAllNotifications() {
        return notificationRepository.getAllNotifications();
    }
}