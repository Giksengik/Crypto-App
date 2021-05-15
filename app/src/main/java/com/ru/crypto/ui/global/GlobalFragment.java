package com.ru.crypto.ui.global;

import android.annotation.SuppressLint;

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



import com.ru.crypto.databinding.FragmentGlobalBinding;
import com.ru.crypto.ui.fragments.GlobalStatsFragment;
import com.ru.crypto.ui.fragments.TimeRangeFragment;
import com.ru.crypto.R;
import com.ru.crypto.utils.OnClick;
import com.ru.crypto.utils.factories.DefaultLineChartTuner;
import com.ru.crypto.utils.factories.DefaultPieChartTuner;



public class GlobalFragment extends Fragment implements TimeRangeFragment.onTimeRangeClickListener {

     private GlobalViewModel mGlobalViewModel;
     private FragmentGlobalBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGlobalViewModel = new ViewModelProvider(this).get(GlobalViewModel.class);
    }

    public void initializeToolbar() {
        Toolbar toolbar  = getActivity().findViewById(R.id.toolbar);
        String toolbarName = "GlobalData";
        TextView toolbarTitle  = getActivity().findViewById(R.id.toolbarTitle);
        if(toolbar != null) {
            toolbar.getMenu().getItem(0).setVisible(false);
            toolbarTitle.setText(toolbarName);
        }
    }

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGlobalBinding.inflate(inflater);
        View root = binding.getRoot();

        TimeRangeFragment bitcoinTimeRangeFragment = TimeRangeFragment.newInstance("global");
        getChildFragmentManager().beginTransaction()
                .add(R.id.bitcoinTimeRanges, bitcoinTimeRangeFragment)
                .commit();

        GlobalStatsFragment globalStatsFragment = new GlobalStatsFragment();
        getChildFragmentManager().beginTransaction()
                .add(R.id.marketStatsContainer,globalStatsFragment)
                .commit();
        DefaultPieChartTuner pieTuner = (DefaultPieChartTuner) mGlobalViewModel.getDefaultPieChartTuner();
        DefaultLineChartTuner lineTuner = (DefaultLineChartTuner) mGlobalViewModel.getDefaultLineChartTuner();

        lineTuner.setChartProperties(binding.bitcoinPriceLineChart);
        pieTuner.setChartProperties(binding.globalPercentageChart);

        binding.bitcoinPriceLineChart.setOnChartValueSelectedListener(OnClick.onLineChartValue(binding.bitcoinValue));

        mGlobalViewModel.getGlobalData().observe(getViewLifecycleOwner(), globalCryptoData -> {
            pieTuner.setData(binding.globalPercentageChart, globalCryptoData.getData().getMarketCapPercentage());
            globalStatsFragment.setData(globalCryptoData);
            binding.progressBarGlobalData.setVisibility(View.INVISIBLE);
        });



        mGlobalViewModel.getBitcoinData().observe(getViewLifecycleOwner(), historicalCurrencyData -> {
            lineTuner.setLinearChartData(binding.bitcoinPriceLineChart, historicalCurrencyData.getPrices());
            binding.bitcoinValue.setText(lineTuner.getLastTime(historicalCurrencyData.getPrices()) + " : "
                    + lineTuner.getLastTimePrice(historicalCurrencyData.getPrices()));
            binding.progressBarBitcoinData.setVisibility(View.INVISIBLE);
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
        initializeToolbar();
    }


    @Override
    public void onTimeRangeClick(String timeRange) {
        mGlobalViewModel.loadBitcoinInformationInTimeRange(timeRange);
    }
}
