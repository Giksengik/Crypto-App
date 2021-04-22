package com.ru.crypto.ui.market;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ru.crypto.R;
import com.ru.crypto.adapters.CryptoCurrencyAdapter;
import com.ru.crypto.adapters.CryptoCurrencyDiffUtilCallback;
import com.ru.crypto.di.components.CryptoCurrencyAdapterComponent;
import com.ru.crypto.di.components.DaggerCryptoCurrencyAdapterComponent;
import com.ru.crypto.models.CryptoCurrency;

import javax.inject.Inject;

public class MarketFragment extends Fragment {

    private MarketViewModel mMarketViewModel;
    @Inject CryptoCurrencyAdapter currencyAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        CryptoCurrencyAdapterComponent component = DaggerCryptoCurrencyAdapterComponent.create();
        component.inject(this);

        mMarketViewModel = new ViewModelProvider(this).get(MarketViewModel.class);

        View root = inflater.inflate(R.layout.fragment_market, container, false);
        RecyclerView currenciesList = root.findViewById(R.id.currenciesList);
        currenciesList.setLayoutManager(new LinearLayoutManager(getContext()));
        currenciesList.setAdapter(currencyAdapter);

        mMarketViewModel.getAllCurrencies().observe(getViewLifecycleOwner(), cryptocurrencies -> {
            mMarketViewModel.countDiffResult(currencyAdapter.getData());
            currencyAdapter.setCurrencies(cryptocurrencies);
        });

        mMarketViewModel.getDiffResult().observe(getViewLifecycleOwner(), diffResult -> {
            diffResult.dispatchUpdatesTo(currencyAdapter);
        });


        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        mMarketViewModel.updateCurrencies(getActivity().getApplication());
    }
}