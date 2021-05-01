package com.ru.crypto.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ru.crypto.R;
import com.ru.crypto.ui.fragments.TimeRangeFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimeRangeAdapter extends RecyclerView.Adapter<TimeRangeAdapter.ViewHolder> {
    List<String> ranges = new ArrayList(Arrays.asList("1D","7D","1M", "3M", "6M", "1Y", "3Y", "ALL"));
    String pressedTimeRange;
    TimeRangeFragment.onTimeRangeClickListener onTimeChangedListener;
    public TimeRangeAdapter (TimeRangeFragment.onTimeRangeClickListener onTimeChangedListener) {
        this.onTimeChangedListener = onTimeChangedListener;
    }
    @NonNull
    @Override
    public TimeRangeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TimeRangeAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.time_range_in_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String range = ranges.get(position);
        holder.timeRange.setText(range);
        holder.itemView.setOnClickListener(v -> {
            pressedTimeRange = range;
            onTimeChangedListener.onTimeRangeClick(range);
        });
    }

    @Override
    public int getItemCount() {
        return ranges.size();
    }

    public String getPressedTimeRange() {
        return pressedTimeRange;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView timeRange;
        ViewHolder(View v) {
            super(v);
            timeRange = v.findViewById(R.id.timeRange);
        }

    }
}
