package com.ru.crypto.di.components;


import com.ru.crypto.api.INetworkService;
import com.ru.crypto.api.NotificationBroadcast;
import com.ru.crypto.api.NotificationJobService;
import com.ru.crypto.di.modules.CryptoCurrencyNetworkServiceModule;
import com.ru.crypto.di.modules.GsonModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {GsonModule.class, CryptoCurrencyNetworkServiceModule.class})
@Singleton
public interface CryptoCurrencyNetworkServiceComponent {

    INetworkService getService();

    void inject(NotificationJobService service);
    void inject(NotificationBroadcast broadcast);
}
