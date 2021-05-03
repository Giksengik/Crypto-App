package com.ru.crypto.ui.market;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ru.crypto.MainActivity;
import com.ru.crypto.R;
import com.ru.crypto.adapters.CryptoCurrencyAdapter;
import com.ru.crypto.di.components.CryptoCurrencyAdapterComponent;
import com.ru.crypto.di.components.DaggerCryptoCurrencyAdapterComponent;


import javax.inject.Inject;

public class MarketFragment extends Fragment implements LifecycleOwner {

    private MarketViewModel mMarketViewModel;
    Toolbar toolbar;

    @Inject
    CryptoCurrencyAdapter currencyAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeToolbar();
    }

    public void initializeToolbar() {
        toolbar  = getActivity().findViewById(R.id.toolbar);
        if(toolbar != null) {
            toolbar.setTitle("");
            toolbar.getMenu().getItem(0).setVisible(true);
            toolbar.getMenu().getItem(1).setVisible(true);

        }
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CryptoCurrencyAdapterComponent component = DaggerCryptoCurrencyAdapterComponent.create();
        component.inject(this);
        currencyAdapter = new CryptoCurrencyAdapter();
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
        setSearchActions();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        closeSearchView();
    }


    public void setSearchActions() {
        toolbar = getActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {
            SearchView searchView = (SearchView) toolbar.getMenu().findItem(R.id.action_search).getActionView();

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    currencyAdapter.filterCurrencies(newText);
                    return false;
                }
            });
        }
    }
    public void closeSearchView() {
        
        toolbar.collapseActionView();
        searchView.clearFocus();
    }

}