package com.ru.crypto.ui.currency_profile.links;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ru.crypto.R;
import com.ru.crypto.models.CryptoCurrency;
import com.ru.crypto.models.CurrencyLinks;
import com.ru.crypto.ui.currency_profile.CurrencyProfileFragment;
import com.ru.crypto.ui.fragments.LinksFragment;

import java.util.LinkedHashSet;

public class CurrencyLinksFragment extends Fragment {

    private CurrencyLinksViewModel mCurrencyLinksViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrencyLinksViewModel = new ViewModelProvider(this).get(CurrencyLinksViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_currency_links, container);

        LinksFragment linksFragment = new LinksFragment();

        getChildFragmentManager().beginTransaction()
                .add(R.id.linksFragmentContainer, linksFragment)
                .commit();

        mCurrencyLinksViewModel.getLinks().observe(getViewLifecycleOwner(),
                linksFragment::setLinksData);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        mCurrencyLinksViewModel.loadLinks(getCurrency().getId());
    }

    public CryptoCurrency getCurrency () {
        CurrencyProfileFragment fragment = (CurrencyProfileFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        return fragment.getCurrency();
    }
}
