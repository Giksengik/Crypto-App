package com.ru.crypto.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        holder.currencyName.setText(notificationData.getCurrencyID());
        if (notificationData.getNotificationType().equals(NotificationData.TYPE_BORDER_EVENT)) {
            holder.bottomBorder.setText(notificationData.getTopBorder() + "" + "$");
            holder.topBorder.setText(notificationData.getBottomBorder() + "" + "$");
            holder.timeInterval.setText("");
        }
        else  {
            holder.bottomBorder.setText("");
            holder.topBorder.setText("");
        }
        switch(notificationData.getNotificationType()) {
            case NotificationData.TYPE_BORDER_EVENT:
                holder.type.setText("Border");
                holder.notificationImage.setImageResource(R.drawable.icon_border_notification);
                break;
            case NotificationData.TYPE_CYCLICAL_PRICE:
                holder.notificationImage.setImageResource(R.drawable.icon_cycling_notification);
                holder.type.setText("Cyclical");
                double toConvert =(double) notificationData.getIntervalValueInMillis();
                double timeIntervalInMilliHours =  toConvert / 60 / 60;
                double timeIntervalInHours = ((double)Math.round(timeIntervalInMilliHours))/1000;
                holder.timeInterval.setText( timeIntervalInHours + " hours");
                break;
            case NotificationData.TYPE_SINGLE:
                holder.notificationImage.setImageResource(R.drawable.icon_one_time_notification);
                holder.type.setText("Single");
                holder.timeInterval.setText(Converters.getFormattedWithHourDataStringByUnixTimestamp(notificationData.getNextNotificationTime()));
                break;
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView currencyName, timeInterval, bottomBorder, topBorder, type;
        ImageView notificationImage;
        ViewHolder(View v) {
            super(v);
            currencyName = v.findViewById(R.id.currencyNameInNotification);
            timeInterval = v.findViewById(R.id.timeIntervalInNotification);
            bottomBorder = v.findViewById(R.id.bottomBorder);
            topBorder = v.findViewById(R.id.topBorder);
            type = v.findViewById(R.id.notificationType);
            notificationImage = v.findViewById(R.id.notificationImage);
        }
    }

    public List<NotificationData> getData(){
        return notifications;
    }
}
