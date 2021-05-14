package com.ru.crypto.di.components;

import com.ru.crypto.services.NotificationService;
import com.ru.crypto.di.modules.CryptoCurrencyNetworkServiceModule;
import com.ru.crypto.di.modules.GsonModule;
import com.ru.crypto.di.modules.NotificationModule;
import com.ru.crypto.repositories.NotificationRepository;
import com.ru.crypto.ui.notifications.NotificationsViewModel;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {GsonModule.class, CryptoCurrencyNetworkServiceModule.class, NotificationModule.class})
public interface NotificationRepoComponent {

    NotificationRepository getRepo();

    void inject(NotificationsViewModel viewModel);
    void inject(NotificationService service);
}
