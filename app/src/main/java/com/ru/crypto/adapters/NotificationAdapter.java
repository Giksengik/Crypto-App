package com.ru.crypto.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ru.crypto.R;
import com.ru.crypto.models.CryptoArticle;
import com.ru.crypto.models.NotificationData;
import com.ru.crypto.utils.Converters;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    List<NotificationData> notifications = new ArrayList<>();

    public NotificationAdapter() {

    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_in_list, parent, false));
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public void setNotifications(List<NotificationData> notificationData) {
        this.notifications = notificationData;
        notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationData notificationData = notifications.get(position);

        holder.timeInterval.setText(notificationData.getCurrencyID());
        holder.currencyName.setText(notificationData.getNextNotificationTime() + " milli");
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView currencyName, timeInterval;

        ViewHolder(View v) {
            super(v);
            currencyName = v.findViewById(R.id.currencyNameInNotification);
            timeInterval = v.findViewById(R.id.timeIntervalInNotification);
        }

    }
}
