package com.ru.crypto.ui.notifications;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ru.crypto.R;
import com.ru.crypto.adapters.NotificationAdapter;
import com.ru.crypto.api.RestartBroadcastReceiver;
import com.ru.crypto.api.ServiceAdmin;
import com.ru.crypto.models.CryptoCurrencyName;
import com.ru.crypto.models.NotificationData;

import java.util.List;

public class NotificationsFragment extends Fragment implements CreatingNotificationDialogFragment.onCreationNotificationClickListener {

    private NotificationsViewModel notificationsViewModel;
    private FloatingActionButton floatingActionButton;
    private RecyclerView notificationList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        CreatingNotificationDialogFragment creatingFragment = new CreatingNotificationDialogFragment();

        floatingActionButton = root.findViewById(R.id.buttonAddNotification);
        notificationList = root.findViewById(R.id.notificationList);

        notificationList.setLayoutManager(new LinearLayoutManager(getContext()));
        NotificationAdapter notificationAdapter = new NotificationAdapter();
        notificationList.setAdapter(notificationAdapter);

        notificationsViewModel.getAllNotifications().observe(getViewLifecycleOwner(),
                notificationAdapter::setNotifications);

        floatingActionButton.setOnClickListener(v -> {
            creatingFragment.show(
                    getChildFragmentManager(), CreatingNotificationDialogFragment.TAG);
        });

        notificationsViewModel.getCurrencyNames().observe(getViewLifecycleOwner(), creatingFragment::setCurrenciesList);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            RestartBroadcastReceiver.scheduleJob(getContext());
        } else {
            ServiceAdmin bck = new ServiceAdmin();
            bck.launchService(getContext());
        }
    }

    @Override
    public void onCreationNotificationClick(NotificationData notificationData) {
        notificationsViewModel.addNotification(notificationData);
    }
}