package com.ru.crypto.ui.currency_profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;


import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ru.crypto.MainActivity;
import com.ru.crypto.R;
import com.ru.crypto.models.CryptoCurrency;

public class CurrencyProfileFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private final String [] tabNames = new String[] {"General", "News", "Markets", "Links"};
    private CryptoCurrency mCryptoCurrency;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            mCryptoCurrency = (CryptoCurrency) getArguments().get("currency");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_currency_profile,container,false);

        ViewPager2 viewPager = root.findViewById(R.id.viewPager);
        TabLayout tabs = root.findViewById(R.id.currencyProfileTabs);

        CurrencyProfileAdapter adapter = new CurrencyProfileAdapter(this);
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabs, viewPager, (tab, position) -> { tab.setText(tabNames[position]);}).attach();

        initializeToolbar();

        return root;
    }

    public void initializeToolbar() {
        Toolbar toolbar  = getActivity().findViewById(R.id.toolbar);
        if(toolbar != null) {
            toolbar.getMenu().getItem(0).setVisible(false);
            toolbar.getMenu().getItem(1).setVisible(false);
        }
        ImageButton buttonBack = getActivity().findViewById(R.id.button_back);
        buttonBack.setVisibility(View.VISIBLE);
        buttonBack.setOnClickListener(
                v -> getFragmentManager().popBackStack());
    }

    public CryptoCurrency getCurrency () {
        return mCryptoCurrency;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ImageButton buttonBack = getActivity().findViewById(R.id.button_back);
        buttonBack.setVisibility(View.GONE);
    }
}
