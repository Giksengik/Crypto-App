package com.ru.crypto.ui.market;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.ru.crypto.MainActivity;
import com.ru.crypto.R;
import com.ru.crypto.adapters.CryptoCurrencyAdapter;
import com.ru.crypto.ui.currency_profile.CurrencyProfileFragment;



public class MarketFragment extends Fragment implements LifecycleOwner {

    private MarketViewModel mMarketViewModel;
    private Toolbar mToolbar;
    CryptoCurrencyAdapter currencyAdapter;

    private final CryptoCurrencyAdapter.OnCurrencyClickListener mCurrencyClickListener = (currency, position) -> {
        CurrencyProfileFragment profileFragment = new CurrencyProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("currency", currency);
        profileFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, profileFragment)
                .addToBackStack("cryptocurrency")
                .commit();
        setNavVisibility(false);
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMarketViewModel = new ViewModelProvider(this).get(MarketViewModel.class);
        currencyAdapter = new CryptoCurrencyAdapter(mCurrencyClickListener);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_market, container, false);
        RecyclerView currenciesList = root.findViewById(R.id.currenciesList);
        currenciesList.setItemAnimator(null);

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
        setNavVisibility(true);
        initializeToolbar();
        mMarketViewModel.updateCurrencies(getActivity().getApplication());
        setSearchActions();
    }

    public void initializeToolbar() {
        mToolbar = getActivity().findViewById(R.id.toolbar);
        if(mToolbar != null) {
            mToolbar.setTitle("");
            mToolbar.getMenu().getItem(0).setVisible(true);
            mToolbar.getMenu().getItem(1).setVisible(true);

        }
    }

    public void setSearchActions() {
        mToolbar = getActivity().findViewById(R.id.toolbar);
        if (mToolbar != null) {
            SearchView searchView = (SearchView) mToolbar.getMenu().findItem(R.id.action_search).getActionView();

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
        if(mToolbar != null) {
            mToolbar.collapseActionView();
        }
    }

    public void setNavVisibility(boolean isVisible) {
        BottomNavigationView nav = ((MainActivity)getActivity()).getNav();
        if(isVisible && nav.getVisibility() == View.GONE){
            nav.setVisibility(View.VISIBLE);
        }else if(!isVisible && nav.getVisibility() == View.VISIBLE)
            nav.setVisibility(View.GONE);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        closeSearchView();
    }

}