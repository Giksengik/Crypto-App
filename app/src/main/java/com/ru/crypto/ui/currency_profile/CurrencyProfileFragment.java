package com.ru.crypto.ui.currency_profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;


import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ru.crypto.R;

public class CurrencyProfileFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private final String [] tabNames = new String[] {"General", "News", "Markets", "Links"};
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_currency_profile,container,false);

        ViewPager2 viewPager = root.findViewById(R.id.viewPager);
        TabLayout tabs = root.findViewById(R.id.currencyProfileTabs);

        CurrencyProfileAdapter adapter = new CurrencyProfileAdapter(this);
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabs, viewPager, (tab, position) -> { tab.setText(tabNames[position]);});

        initializeToolbar();

        return root;
    }

    public void initializeToolbar() {
        Toolbar toolbar  = getActivity().findViewById(R.id.toolbar);
        if(toolbar != null) {
            toolbar.getMenu().getItem(0).setVisible(false);
            toolbar.getMenu().getItem(1).setVisible(false);
        }
    }
}
