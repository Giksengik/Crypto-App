package com.ru.crypto.ui.market;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.ru.crypto.databinding.FragmentMarketBinding;
import com.ru.crypto.ui.currency_profile.CurrencyProfileFragment;



public class MarketFragment extends Fragment implements LifecycleOwner {

    private MarketViewModel mMarketViewModel;
    private CryptoCurrencyAdapter currencyAdapter;
    private FragmentMarketBinding binding;
    private Toolbar mToolbar;

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
        currencyAdapter = new CryptoCurrencyAdapter(mCurrencyClickListener, mMarketViewModel);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMarketBinding.inflate(inflater);
        View root = binding.getRoot();

        binding.currenciesList.setItemAnimator(null);
        binding.currenciesList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.currenciesList.setAdapter(currencyAdapter);


        binding.currenciesList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && binding.refreshMarketButton.getVisibility() == View.VISIBLE) {
                    binding.refreshMarketButton.hide();
                } else if (dy < 0 && binding.refreshMarketButton.getVisibility() != View.VISIBLE) {
                    binding.refreshMarketButton.show();
                }
            }
        });

        binding.refreshMarketButton.setOnClickListener(v -> mMarketViewModel.updateCurrencies());

        mMarketViewModel.getAllCurrencies().observe(getViewLifecycleOwner(), cryptocurrencies -> {
            if( currencyAdapter.getData().size() == 0) {
                currencyAdapter.setCurrencies(cryptocurrencies);
                currencyAdapter.notifyDataSetChanged();
            }else {
                currencyAdapter.setCurrencies(cryptocurrencies);
                mMarketViewModel.countDiffResult(currencyAdapter.getData());
            }
            binding.cryptoCurrencyListProgressBar.setVisibility(View.INVISIBLE);
        });

        mMarketViewModel.getDiffResult().observe(getViewLifecycleOwner(), diffResult -> {
            diffResult.dispatchUpdatesTo(currencyAdapter);
            binding.cryptoCurrencyListProgressBar.setVisibility(View.INVISIBLE);
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        setNavVisibility(true);
        initializeToolbar();
        mMarketViewModel.updateCurrencies();
        setSearchActions();
    }

    public void initializeToolbar() {
        mToolbar = getActivity().findViewById(R.id.toolbar);
        String toolbarName = "";
        TextView toolbarTitle  = getActivity().findViewById(R.id.toolbarTitle);
        if(mToolbar != null) {
            mToolbar.getMenu().getItem(0).setVisible(true);
            toolbarTitle.setText(toolbarName);
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