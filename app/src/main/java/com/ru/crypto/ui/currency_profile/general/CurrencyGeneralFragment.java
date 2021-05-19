package com.ru.crypto.ui.currency_profile.general;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.LineChart;
import com.ru.crypto.R;
import com.ru.crypto.databinding.FragmentCurrencyGeneralBinding;
import com.ru.crypto.models.CryptoCurrency;
import com.ru.crypto.ui.currency_profile.CurrencyProfileFragment;
import com.ru.crypto.ui.fragments.CurrencyInfoFragment;
import com.ru.crypto.ui.fragments.TimeRangeFragment;
import com.ru.crypto.utils.OnClick;
import com.ru.crypto.utils.factories.DefaultLineChartTunerFactory;
import com.ru.crypto.utils.factories.ILineChartTuner;

public class CurrencyGeneralFragment extends Fragment implements TimeRangeFragment.onTimeRangeClickListener {

    private CurrencyGeneralViewModel mCurrencyGeneralViewModel;
    private CryptoCurrency mCurrency;
    private FragmentCurrencyGeneralBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrencyGeneralViewModel = new ViewModelProvider(this).get(CurrencyGeneralViewModel.class);
        setCurrency();
    }

    public void setCurrency () {
        CurrencyProfileFragment fragment = (CurrencyProfileFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        mCurrency = fragment.getCurrency();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentCurrencyGeneralBinding.inflate(inflater);
        View root = binding.getRoot();

        ILineChartTuner priceChartTuner = new DefaultLineChartTunerFactory().getLineChartTuner();
        priceChartTuner.setChartProperties(binding.currencyPriceChart);
        binding.currencyPriceChart
                .setOnChartValueSelectedListener(OnClick.onLineChartValue(binding.currencyProfilePrice, binding.curencyProfileTimeOfPrice));

        CurrencyInfoFragment currencyInfoFragment = CurrencyInfoFragment.newInstance(mCurrency);
        getChildFragmentManager().beginTransaction()
                .add(R.id.companyInfoContainer, currencyInfoFragment)
                .commit();

        TimeRangeFragment bitcoinTimeRangeFragment = TimeRangeFragment.newInstance("currencyGeneral");
        getChildFragmentManager().beginTransaction()
                .add(R.id.currencyTimeRangeContainer, bitcoinTimeRangeFragment)
                .commit();

        mCurrencyGeneralViewModel.getMarketChart().observe(getViewLifecycleOwner(), currencyMarketChart -> {
            binding.progressCurrencyMarketChart.setVisibility(View.GONE);
            priceChartTuner.setLinearChartData(binding.currencyPriceChart, currencyMarketChart.getPrices());
            binding.curencyProfileTimeOfPrice.setText(priceChartTuner.getLastTime(currencyMarketChart.getPrices()));
            binding.currencyProfilePrice.setText(priceChartTuner.getLastTimePrice(currencyMarketChart.getPrices()));
        });

        return root;

    }

    @Override
    public void onStart() {
        super.onStart();
        mCurrencyGeneralViewModel.loadCurrencyMarketChartByTimeRange(mCurrency.getId(), "ALL");
    }

    @Override
    public void onTimeRangeClick(String timeRange) {
        mCurrencyGeneralViewModel.loadCurrencyMarketChartByTimeRange(mCurrency.getId(), timeRange);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
