package com.ru.crypto.ui.currency_profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class CurrencyProfilePlaceholder extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static  CurrencyProfilePlaceholder newInstance(int index) {

        CurrencyProfilePlaceholder fragment = new CurrencyProfilePlaceholder();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;

    }
}
