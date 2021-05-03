package com.ru.crypto.ui.global;

import android.annotation.SuppressLint;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;



import com.ru.crypto.ui.fragments.TimeRangeFragment;
import com.ru.crypto.utils.Converters;
import com.ru.crypto.R;
import com.ru.crypto.utils.OnClick;
import com.ru.crypto.utils.factories.DefaultLineChartTuner;
import com.ru.crypto.utils.factories.DefaultPieChartTuner;


import java.util.ArrayList;

public class GlobalFragment extends Fragment implements TimeRangeFragment.onTimeRangeClickListener{

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

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_global, container, false);
        PieChart globalPercentageChart = root.findViewById(R.id.globalPercentageChart);
        LineChart bitcoinPriceChart = root.findViewById(R.id.bitcoinPriceLineChart);
        TextView bitcoinValue = root.findViewById(R.id.bitcoinValue);
        ProgressBar globalDataProgressBar = root.findViewById(R.id.progressBarGlobalData);
        ProgressBar bitcoinDataProgressBar = root.findViewById(R.id.progressBarBitcoinData);

        TimeRangeFragment bitcoinTimeRangeFragment = new TimeRangeFragment();
        getChildFragmentManager().beginTransaction()
                .add(R.id.bitcoinTimeRanges, bitcoinTimeRangeFragment)
                .commit();

        GlobalStatsFragment globalStatsFragment = new GlobalStatsFragment();
        getChildFragmentManager().beginTransaction()
                .add(R.id.marketStatsContainer,globalStatsFragment)
                .commit();
        DefaultPieChartTuner pieTuner = (DefaultPieChartTuner) mGlobalViewModel.getDefaultPieChartTuner();
        DefaultLineChartTuner lineTuner = (DefaultLineChartTuner) mGlobalViewModel.getDefaultLineChartTuner();

        lineTuner.setChartProperties(bitcoinPriceChart);
        pieTuner.setChartProperties(globalPercentageChart);

        bitcoinPriceChart.setOnChartValueSelectedListener(OnClick.onLineChartValue(bitcoinValue));

        mGlobalViewModel.getGlobalData().observe(getViewLifecycleOwner(), globalCryptoData -> {
            pieTuner.setData(globalPercentageChart, globalCryptoData.getData().getMarketCapPercentage());
            globalStatsFragment.setData(globalCryptoData);
            globalDataProgressBar.setVisibility(View.INVISIBLE);
        });



        mGlobalViewModel.getBitcoinData().observe(getViewLifecycleOwner(), historicalCurrencyData -> {
            lineTuner.setLinearChartData(bitcoinPriceChart, historicalCurrencyData.getPrices());
            ArrayList<Double> lastPrice = historicalCurrencyData.getPrices().get(historicalCurrencyData.getPrices().size()-1);
            bitcoinValue.setText(Converters.getFormattedWithHourDataStringByUnixTimestamp(lastPrice.get(0)) + " : " + lastPrice.get(1) + "$");
            bitcoinDataProgressBar.setVisibility(View.INVISIBLE);
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mGlobalViewModel.loadGlobalData();

    }


    @Override
    public void onTimeRangeClick(String timeRange) {
        mGlobalViewModel.loadBitcoinInformationInTimeRange(timeRange);
    }
}
