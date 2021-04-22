package com.ru.crypto.adapters;

import androidx.recyclerview.widget.DiffUtil;

import com.ru.crypto.models.CryptoCurrency;

import java.util.List;

public class CryptoCurrencyDiffUtilCallback extends DiffUtil.Callback {
    private final List<CryptoCurrency> oldList;
    private final List<CryptoCurrency> newList;

    public CryptoCurrencyDiffUtilCallback(List<CryptoCurrency> oldList, List<CryptoCurrency> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }
    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        CryptoCurrency oldCurrency = oldList.get(oldItemPosition);
        CryptoCurrency newCurrency = newList.get(newItemPosition);
        return oldCurrency.getCurrency().equals(newCurrency.getCurrency());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        CryptoCurrency oldCurrency = oldList.get(oldItemPosition);
        CryptoCurrency newCurrency = newList.get(newItemPosition);
        return oldCurrency.getCurrentPrice() == newCurrency.getCurrentPrice()
                && oldCurrency.getPriceChange24H() == newCurrency.getPriceChange24H();
    }
}
