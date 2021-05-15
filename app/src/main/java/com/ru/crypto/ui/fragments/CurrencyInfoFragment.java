package com.ru.crypto.ui.fragments;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ru.crypto.R;
import com.ru.crypto.adapters.CharacteristicsBlockAdapter;
import com.ru.crypto.databinding.FragmentCurrencyInfoBinding;
import com.ru.crypto.models.CryptoCurrency;

import com.ru.crypto.utils.factories.DefaultCurrencyCharacteristicsMakerFactory;
import com.ru.crypto.utils.factories.ICurrencyCharacteristicsMaker;


public class CurrencyInfoFragment extends Fragment {

    private ICurrencyCharacteristicsMaker characteristicsMaker;
    private FragmentCurrencyInfoBinding binding;

    public static CurrencyInfoFragment newInstance(CryptoCurrency currency) {
        return new CurrencyInfoFragment(currency);
    }
    private CurrencyInfoFragment(CryptoCurrency currency) {
        characteristicsMaker = new DefaultCurrencyCharacteristicsMakerFactory().getMaker();
        characteristicsMaker.setCryptoCurrency(currency);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentCurrencyInfoBinding.inflate(inflater);
        View root = binding.getRoot();
        setData();
        return root;
    }

    public void setData() {
        characteristicsMaker.setIcon(binding.currencyInfoIcon);
        binding.currencyInfoName.setText(characteristicsMaker.getCurrencyName());
        binding.characteristicsBlockList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        CharacteristicsBlockAdapter adapter = new CharacteristicsBlockAdapter();
        binding.characteristicsBlockList.setNestedScrollingEnabled(false);
        binding.characteristicsBlockList.setAdapter(adapter);
        adapter.setCurrencyCharacteristics(characteristicsMaker.getCharacteristics());

    }
}
