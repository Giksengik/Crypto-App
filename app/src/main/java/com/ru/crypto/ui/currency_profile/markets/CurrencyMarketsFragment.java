package com.ru.crypto.ui.currency_profile.markets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ru.crypto.R;

public class CurrencyMarketsFragment extends Fragment {

    private CurrencyMarketsViewModel mCurrencyMarketsViesModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrencyMarketsViesModel = new ViewModelProvider(this).get(CurrencyMarketsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_currency_markets, container);
        return root;
    }
}
