package com.ru.crypto.ui.global;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.ru.crypto.MainActivity;
import com.ru.crypto.R;


import java.util.ArrayList;
import java.util.TreeMap;

public class GlobalFragment extends Fragment {
     private GlobalViewModel mGlobalViewModel;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeToolbar();
        mGlobalViewModel = new ViewModelProvider(this).get(GlobalViewModel.class);
    }

    public void initializeToolbar() {
        Toolbar toolbar  = getActivity().findViewById(R.id.toolbar);
        if(toolbar != null) {
            toolbar.setTitle("Global");
            toolbar.getMenu().getItem(0).setVisible(false);
            toolbar.getMenu().getItem(1).setVisible(false);
        }
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_global, container, false);
        PieChart chart = root.findViewById(R.id.globalPercentageChart);
        getViewLifecycleOwner();

        GlobalStatsFragment globalStatsFragment = new GlobalStatsFragment();
        getChildFragmentManager().beginTransaction()
                .add(R.id.marketStatsContainer,globalStatsFragment)
                .commit();

        mGlobalViewModel.getGlobalData().observe(getViewLifecycleOwner(), globalCryptoData -> {
            setPieChartData(globalCryptoData.getData().getMarketCapPercentage(), chart);
            globalStatsFragment.setData(globalCryptoData);
        });
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        mGlobalViewModel.loadGlobalData();

    }

    public void setPieChartData(TreeMap<String,Double> pieData, PieChart chart) {
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.animate();
        if (pieData != null) {
            ArrayList<PieEntry> entries = new ArrayList<>();
            for (Double percentage : pieData.values()) {
                double temp_val = percentage;
                float val = (float) temp_val;
                entries.add(new PieEntry(val));
            }
            PieDataSet dataSet = new PieDataSet(entries, "Election Results");
            dataSet.setDrawIcons(false);
            dataSet.setSliceSpace(3f);
            dataSet.setIconsOffset(new MPPointF(0, 40));
            dataSet.setSelectionShift(5f);
            ArrayList<Integer> colors = new ArrayList<>();

            for (int c : ColorTemplate.VORDIPLOM_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.JOYFUL_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.COLORFUL_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.LIBERTY_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.PASTEL_COLORS)
                colors.add(c);
            colors.add(ColorTemplate.getHoloBlue());

            dataSet.setColors(colors);
            //dataSet.setSelectionShift(0f);

            PieData dataPie = new PieData(dataSet);
            dataPie.setValueFormatter(new PercentFormatter());
            dataPie.setValueTextSize(11f);
            dataPie.setValueTextColor(Color.WHITE);
            dataPie.setValueTypeface(Typeface.DEFAULT);
            chart.setData(dataPie);

            chart.invalidate();
            }

        }
}
