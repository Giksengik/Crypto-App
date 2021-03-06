package com.ru.crypto.ui.currency_profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

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
import com.ru.crypto.databinding.FragmentCurrencyProfileBinding;
import com.ru.crypto.models.CryptoCurrency;

public class CurrencyProfileFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private final String [] tabNames = new String[] {"General", "Links"};
    private FragmentCurrencyProfileBinding binding;
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

        binding = FragmentCurrencyProfileBinding.inflate(inflater);
        View root = binding.getRoot();

        CurrencyProfileAdapter adapter = new CurrencyProfileAdapter(this);
        binding.viewPager.setAdapter(adapter);
        new TabLayoutMediator(binding.currencyProfileTabs, binding.viewPager, (tab, position) -> { tab.setText(tabNames[position]);}).attach();

        initializeToolbar();

        return root;
    }

    public void initializeToolbar() {
        Toolbar toolbar  = getActivity().findViewById(R.id.toolbar);
        TextView toolbarTitle  = getActivity().findViewById(R.id.toolbarTitle);
        if(toolbar != null) {
            toolbar.getMenu().getItem(0).setVisible(false);
            toolbarTitle.setText(mCryptoCurrency.getName());
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
        binding = null;
    }

}
