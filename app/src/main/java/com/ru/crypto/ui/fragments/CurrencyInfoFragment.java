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
import com.ru.crypto.models.CryptoCurrency;

import com.ru.crypto.utils.factories.DefaultCurrencyCharacteristicsMakerFactory;
import com.ru.crypto.utils.factories.ICurrencyCharacteristicsMaker;


public class CurrencyInfoFragment extends Fragment {
    private ImageButton buttonIsFav;
    private ImageView currencyIcon;
    private TextView currencyName;
    private RecyclerView characteristicsBlockList;

    private ICurrencyCharacteristicsMaker characteristicsMaker;

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
        View root = inflater.inflate(R.layout.fragment_currency_info, container, false);

        currencyIcon = root.findViewById(R.id.currencyInfoIcon);
        currencyName = root.findViewById(R.id.currencyInfoName);
        characteristicsBlockList = root.findViewById(R.id.characteristicsBlockList);

        setData();

        return root;
    }

    public void setData() {

        characteristicsMaker.setIcon(currencyIcon);
        currencyName.setText(characteristicsMaker.getCurrencyName());
        characteristicsBlockList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        CharacteristicsBlockAdapter adapter = new CharacteristicsBlockAdapter();
        characteristicsBlockList.setNestedScrollingEnabled(false);
        characteristicsBlockList.setAdapter(adapter);
        adapter.setCurrencyCharacteristics(characteristicsMaker.getCharacteristics());

    }
}
