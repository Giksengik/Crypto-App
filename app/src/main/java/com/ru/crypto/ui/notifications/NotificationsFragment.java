package com.ru.crypto.ui.notifications;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ru.crypto.R;
import com.ru.crypto.adapters.NotificationAdapter;
import com.ru.crypto.adapters.NotificationSwipeCallback;
import com.ru.crypto.api.RestartBroadcastReceiver;
import com.ru.crypto.api.ServiceAdmin;
import com.ru.crypto.databinding.FragmentDialogCreationBinding;
import com.ru.crypto.databinding.FragmentNotificationsBinding;
import com.ru.crypto.models.CryptoCurrency;
import com.ru.crypto.models.CryptoCurrencyName;
import com.ru.crypto.models.NotificationData;

import java.util.List;

public class NotificationsFragment extends Fragment implements CreatingNotificationDialogFragment.onCreationNotificationClickListener
        , CreatingNotificationDialogFragment.onCryptoCurrencyChooseListener{

    private NotificationsViewModel notificationsViewModel;

    private NotificationAdapter notificationAdapter;
    private FragmentNotificationsBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        CreatingNotificationDialogFragment creatingFragment = new CreatingNotificationDialogFragment();


        binding.notificationList.setLayoutManager(new LinearLayoutManager(getContext()));
        notificationAdapter = new NotificationAdapter();
        binding.notificationList.setAdapter(notificationAdapter);

        notificationsViewModel.getAllNotifications().observe(getViewLifecycleOwner(),
                notificationAdapter::setNotifications);

        binding.buttonAddNotification.setOnClickListener(v -> {
            creatingFragment.show(
                    getChildFragmentManager(), CreatingNotificationDialogFragment.TAG);
        });


        notificationsViewModel.getCurrencyNames().observe(getViewLifecycleOwner(), creatingFragment::setCurrenciesList);
        notificationsViewModel.getCurrentCurrency().observe(getViewLifecycleOwner(), creatingFragment::setBorderHints);

        enableSwipeToDeleteAndUndo();

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

    @Override
    public void onCryptoCurrencyChoose(String id) {
        notificationsViewModel.loadCurrentCryptoCurrencyData(id);
    }

    private void enableSwipeToDeleteAndUndo() {
        NotificationSwipeCallback notificationSwipeCallback = new NotificationSwipeCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                final int position = viewHolder.getAdapterPosition();
                final NotificationData item = notificationAdapter.getData().get(position);
                notificationsViewModel.deleteNotification(item);
                Snackbar snackbar = Snackbar
                        .make(binding.notificationsCoordinatorLayout, "Notification was removed.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        notificationsViewModel.addNotification(item);
                        binding.notificationList.scrollToPosition(position);
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(notificationSwipeCallback);
        itemTouchhelper.attachToRecyclerView(binding.notificationList);
    }


}
