package com.ru.crypto.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        private RecyclerView mRangesList;
        private TimeRangeAdapter mTimeRangeAdapter;
        private onTimeRangeClickListener mOnTimeChangedListener = null;

        private String placeholder;

    public static TimeRangeFragment newInstance(String placeholder) {
        return new TimeRangeFragment(placeholder);
    }

    private TimeRangeFragment(String placeholder){
        this.placeholder = placeholder;
    }

    public interface onTimeRangeClickListener{
        void onTimeRangeClick(String timeRange);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity mainActivity =(MainActivity) context;
        Fragment fragment;
        switch(placeholder) {
            case "global":
                NavHostFragment navHostFragment = (NavHostFragment) mainActivity.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);
                if(fragment instanceof onTimeRangeClickListener) {
                    mOnTimeChangedListener = (onTimeRangeClickListener) fragment;
                }
                break;
            case "currencyGeneral":
                Fragment parentFragment = mainActivity.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                fragment = parentFragment.getChildFragmentManager().getFragments().get(0);
                if(fragment instanceof onTimeRangeClickListener) {
                    mOnTimeChangedListener = (onTimeRangeClickListener) fragment;
                }
                break;
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
        mRangesList = view.findViewById(R.id.timeRangeList);
        setListData();
    }

    public void setListData() {
        mTimeRangeAdapter = new TimeRangeAdapter(mOnTimeChangedListener);
        mRangesList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRangesList.setAdapter(mTimeRangeAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnTimeChangedListener = null;
    }
}
