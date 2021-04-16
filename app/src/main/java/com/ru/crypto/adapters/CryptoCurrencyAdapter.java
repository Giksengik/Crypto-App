package com.ru.crypto.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ru.crypto.R;
import com.ru.crypto.models.Cryptocurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.inject.Inject;

public class CryptoCurrencyAdapter extends RecyclerView.Adapter<CryptoCurrencyAdapter.ViewHolder> {
    List<Cryptocurrency> currencies = new ArrayList<>();

    @Inject
    public CryptoCurrencyAdapter(){ }

    @NonNull
    @Override
    public CryptoCurrencyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cryptocurrency_in_list,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CryptoCurrencyAdapter.ViewHolder holder, int position) {
        Cryptocurrency currency = currencies.get(position);

    }

    public void setCurrencies(List<Cryptocurrency> currencies) {
        this.currencies = currencies;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return currencies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView currencyName, currencySymbol, priceChange, price;
        ImageView currencyIcon;
        ImageButton isFavouriteButton;

        ViewHolder(View v) {
            super(v);
            currencyName = v.findViewById(R.id.currencyName);
            currencySymbol = v.findViewById(R.id.currencySymbol);
            priceChange = v.findViewById(R.id.curencyPriceChange);
            price = v.findViewById(R.id.curencyPrice);
            currencyIcon = v.findViewById(R.id.currencyIcon);
            isFavouriteButton = v.findViewById(R.id.iconIsFavouriteCurrency);
        }
    }
}
