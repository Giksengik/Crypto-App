package com.ru.crypto.ui.fragments;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ru.crypto.MainActivity;
import com.ru.crypto.R;
import com.ru.crypto.adapters.TimeRangeAdapter;

public class TimeRangeFragment extends Fragment {



        RecyclerView rangesList;
        TimeRangeAdapter timeRangeAdapter;


        private onTimeRangeClickListener onTimeChangedListener = null;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity mainActivity =(MainActivity) context;
        NavHostFragment navHostFragment = (NavHostFragment) mainActivity.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);
        if(fragment instanceof onTimeRangeClickListener) {
            onTimeChangedListener = (onTimeRangeClickListener) fragment;
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
