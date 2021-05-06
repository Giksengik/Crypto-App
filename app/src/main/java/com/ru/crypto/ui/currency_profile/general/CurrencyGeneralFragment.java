package com.ru.crypto.ui.currency_profile.general;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.LineChart;
import com.ru.crypto.R;
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

    private LineChart priceChart;

    TextView time ;
    TextView currencyPriceAtTime;
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

        View root = inflater.inflate(R.layout.fragment_currency_general, container);

        time = root.findViewById(R.id.curencyProfileTimeOfPrice);
        currencyPriceAtTime = root.findViewById(R.id.currencyProfilePrice);
        priceChart = root.findViewById(R.id.currencyPriceChart);

        ILineChartTuner priceChartTuner = new DefaultLineChartTunerFactory().getLineChartTuner();
        priceChartTuner.setChartProperties(priceChart);
        priceChart.setOnChartValueSelectedListener(OnClick.onLineChartValue(currencyPriceAtTime, time));
        CurrencyInfoFragment currencyInfoFragment = CurrencyInfoFragment.newInstance(mCurrency);

        getChildFragmentManager().beginTransaction()
                .add(R.id.companyInfoContainer, currencyInfoFragment)
                .commit();

        TimeRangeFragment bitcoinTimeRangeFragment = TimeRangeFragment.newInstance("currencyGeneral");
        getChildFragmentManager().beginTransaction()
                .add(R.id.currencyTimeRangeContainer, bitcoinTimeRangeFragment)
                .commit();

        mCurrencyGeneralViewModel.getMarketChart().observe(getViewLifecycleOwner(), currencyMarketChart -> {

            priceChartTuner.setLinearChartData(priceChart, currencyMarketChart.getPrices());
            time.setText(priceChartTuner.getLastTime(currencyMarketChart.getPrices()));
            currencyPriceAtTime.setText(priceChartTuner.getLastTimePrice(currencyMarketChart.getPrices()));
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
}
