package com.ru.crypto.ui.notifications;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.ru.crypto.R;
import com.ru.crypto.adapters.NotificationAdapter;
import com.ru.crypto.adapters.NotificationSwipeCallback;
import com.ru.crypto.databinding.FragmentNotificationsBinding;
import com.ru.crypto.models.NotificationData;

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

        initializeToolbar();

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

    public void initializeToolbar() {
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        String toolbarName = "Notifications";
        TextView toolbarTitle  = getActivity().findViewById(R.id.toolbarTitle);
        if(toolbar != null) {
            toolbar.getMenu().getItem(0).setVisible(false);
            toolbarTitle.setText(toolbarName);
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
