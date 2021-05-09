package com.ru.crypto.adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.mikephil.charting.charts.LineChart;

import com.ru.crypto.repositories.CryptoCurrencyRepository;
import com.ru.crypto.ui.market.MarketViewModel;
import com.ru.crypto.utils.Converters;
import com.ru.crypto.R;
import com.ru.crypto.models.CryptoCurrency;
import com.ru.crypto.utils.factories.MinimalisticLineChartTuner;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class CryptoCurrencyAdapter extends RecyclerView.Adapter<CryptoCurrencyAdapter.ViewHolder> {

    private List<CryptoCurrency> currencies = new ArrayList<>();
    private List<CryptoCurrency> unfilteredCurrencies = new ArrayList<>();
    private List<CryptoCurrency> filteredCurrencies = new ArrayList<>();

    private MarketViewModel viewModel;

    private OnCurrencyClickListener onCurrencyClickListener;
    public interface OnCurrencyClickListener{
        void onCurrencyClick(CryptoCurrency currency, int position);
    }

    @Inject
    public CryptoCurrencyAdapter(OnCurrencyClickListener currencyClickListener, MarketViewModel viewModel){
        this.onCurrencyClickListener = currencyClickListener;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public CryptoCurrencyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cryptocurrency_in_list,parent,false);
        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CryptoCurrencyAdapter.ViewHolder holder, int position) {
        CryptoCurrency currency = currencies.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        holder.currencyName.setText(currency.getName());
        holder.currencySymbol.setText(currency.getSymbol().toUpperCase());
        holder.price.setText(Converters.getCurrencySymbol(currency.getCurrency()) + decimalFormat.format(currency.getCurrentPrice()));
        holder.priceChange.setText(decimalFormat.format(currency.getPriceChangePercentage24H()) + "%");

        MinimalisticLineChartTuner chartTuner = new MinimalisticLineChartTuner();
        chartTuner.setChartProperties(holder.chart);
        if(currency.getSparkline() != null)
        chartTuner.setLinearChartData(holder.chart, currency);


        Glide.with(holder.currencyIcon.getContext())
                .load(currency.getImageUrl()).apply(new RequestOptions().circleCrop())
                .into(holder.currencyIcon);

        holder.itemView.setOnClickListener(v -> {
            onCurrencyClickListener.onCurrencyClick(currency, position);
        });

    }

    public void setCurrencies(List<CryptoCurrency> currencies) {
        this.currencies = currencies;
        this.unfilteredCurrencies = new ArrayList<>(currencies);
    }

    public void filterCurrencies(String query) {
        if (!query.replaceAll("\\s+","").equals("")) {
            filteredCurrencies.clear();
            for (CryptoCurrency item : unfilteredCurrencies) {
                if (item.getCurrency().startsWith(query) || item.getId().startsWith(query))
                    filteredCurrencies.add(item);
            }
            currencies.clear();
            currencies = new ArrayList<>(filteredCurrencies);
        }else {
            currencies.clear();
            currencies = new ArrayList<>(unfilteredCurrencies);
        }
        notifyDataSetChanged();
    }

    public List<CryptoCurrency> getData() {
        return currencies;
    }

    @Override
    public int getItemCount() {
        return currencies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView currencyName, currencySymbol, priceChange, price;
        ImageView currencyIcon;
        LineChart chart ;
        ViewHolder(View v) {
            super(v);
            currencyName = v.findViewById(R.id.currencyName);
            currencySymbol = v.findViewById(R.id.currencySymbol);
            priceChange = v.findViewById(R.id.currencyPriceChange);
            price = v.findViewById(R.id.currencyPrice);
            currencyIcon = v.findViewById(R.id.currencyIcon);
            chart = v.findViewById(R.id.currencyChart);
        }
    }
}
