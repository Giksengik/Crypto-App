package com.ru.crypto.ui.currency_profile;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ru.crypto.ui.currency_profile.general.CurrencyGeneralFragment;
import com.ru.crypto.ui.currency_profile.links.CurrencyLinksFragment;
import com.ru.crypto.ui.currency_profile.markets.CurrencyMarketsFragment;
import com.ru.crypto.ui.currency_profile.news.CurrencyNewsFragment;

public class CurrencyProfileAdapter extends FragmentStateAdapter {

    public CurrencyProfileAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch(position + 1) {
            case 1:
                return new CurrencyGeneralFragment();
            case 2:
                return new CurrencyNewsFragment();
            case 3:
                return new CurrencyMarketsFragment();
            case 4:
                return new CurrencyLinksFragment();
        }
        return new CurrencyGeneralFragment();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
