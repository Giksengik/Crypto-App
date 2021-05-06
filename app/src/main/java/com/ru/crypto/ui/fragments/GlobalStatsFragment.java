package com.ru.crypto.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ru.crypto.R;
import com.ru.crypto.models.GlobalCryptoData;


public class GlobalStatsFragment extends Fragment {

    private TextView numOfCurrencies;
    private TextView marketCapChange;
    private TextView numOfMarkets;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_global_stats,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        numOfCurrencies = view.findViewById(R.id.numOfActiveCurrencies);
        marketCapChange = view.findViewById(R.id.totalMarketCapChange);
        numOfMarkets = view.findViewById(R.id.numOfMarkets);
    }

    @SuppressLint("SetTextI18n")
    public void setData(GlobalCryptoData data) {
        numOfCurrencies.setText(data.getData().getActiveCryptoCurrenciesNum()+ "");
        marketCapChange.setText(data.getData().getMarketCapChangePercentage24H().substring(0,6)+"%");
        numOfMarkets.setText(data.getData().getMarketsNum()+ "");

    }
}
