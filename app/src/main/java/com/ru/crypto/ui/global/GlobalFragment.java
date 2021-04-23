package com.ru.crypto.ui.global;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ru.crypto.R;
import com.ru.crypto.ui.market.MarketViewModel;

public class GlobalFragment extends Fragment {
     private GlobalViewModel mGlobalViewModel;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_global, container, false);
        mGlobalViewModel = new ViewModelProvider(this).get(GlobalViewModel.class);
        return root;
    }
}
