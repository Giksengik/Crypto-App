package com.ru.crypto.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ru.crypto.R;
import com.ru.crypto.adapters.TimeRangeAdapter;

public class TimeRangeFragment extends Fragment {

        RecyclerView rangesList;
        TimeRangeAdapter timeRangeAdapter;
        private onTimeRangeClickListener onTimeChangedListener = null;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof onTimeRangeClickListener) {
            onTimeChangedListener = (onTimeRangeClickListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_timestamp_range,container,false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rangesList = view.findViewById(R.id.timeRangeList);
        setListData();
    }

    public void setListData() {
        timeRangeAdapter = new TimeRangeAdapter(onTimeChangedListener);
        rangesList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rangesList.setAdapter(timeRangeAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onTimeChangedListener = null;
    }

    public interface onTimeRangeClickListener{
        void onTimeRangeClick(String timeRange);
    }
}
