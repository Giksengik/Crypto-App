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
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.ru.crypto.Converters;
import com.ru.crypto.R;
import com.ru.crypto.models.CryptoCurrency;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CryptoCurrencyAdapter extends RecyclerView.Adapter<CryptoCurrencyAdapter.ViewHolder> {
    List<CryptoCurrency> currencies = new ArrayList<>();

    @Inject
    public CryptoCurrencyAdapter(){ }

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
        initializeChart(holder);
        setData(holder.chart,currency);
        holder.currencyName.setText(currency.getName());
        holder.currencySymbol.setText(currency.getSymbol().toUpperCase());
        holder.price.setText(Converters.getCurrencySymbol(currency.getCurrency()) + decimalFormat.format(currency.getCurrentPrice()));
        holder.priceChange.setText(decimalFormat.format(currency.getPriceChangePercentage24H()) + "%");
        if(currency.getPriceChange24H() < 0)
            holder.priceChange.setTextColor(Color.RED);
        else
            holder.priceChange.setTextColor(Color.GREEN);
        if (currency.getIconString() != null)
                holder.currencyIcon.setImageBitmap(Converters.decodeBase64(currency.getIconString()));
    }

    public void initializeChart(CryptoCurrencyAdapter.ViewHolder holder) {
        LineChart chart = holder.chart;
        chart.setBackgroundColor(Color.WHITE);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getDescription().setEnabled(false);
        chart.getAxisLeft().setDrawLabels(false);
        chart.getAxisRight().setDrawLabels(false);
        chart.getXAxis().setDrawLabels(false);
        chart.getLegend().setEnabled(false);
        chart.setTouchEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);
        chart.setPinchZoom(false);
        chart.setDrawBorders(false);
        Legend l = chart.getLegend();
        l.setEnabled(false);
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);
        chart.setPinchZoom(false);
        chart.setTouchEnabled(false);
    }
    public void setCurrencies(List<CryptoCurrency> currencies) {
        this.currencies = currencies;
    }
    private void setData(LineChart chart, CryptoCurrency currency) {
        if (currency.getSparkline() != null) {
            ArrayList<Entry> values = new ArrayList<>();
            for (int i = currency.getSparkline().getPriceChange().size() - 72;
                 i < currency.getSparkline().getPriceChange().size(); i++) {
                double temp_val = currency.getSparkline().getPriceChange().get(i);
                float val = (float) temp_val;
                values.add(new Entry(i, val));
            }
            LineDataSet set1;
            if (chart.getData() != null &&
                    chart.getData().getDataSetCount() > 0) {
                set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
                set1.setValues(values);
                set1.notifyDataSetChanged();
                chart.getData().notifyDataChanged();
                chart.notifyDataSetChanged();
            } else {
                set1 = new LineDataSet(values, "DataSet 1");
                set1.setDrawIcons(false);
                if(currency.getPriceChange24H() < 0) {
                    set1.setColor(Color.RED);
                }else
                    set1.setColor(Color.GREEN);
                set1.setLineWidth(1f);
            }
            set1.setDrawValues(false);
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            set1.setDrawCircles(false);
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            chart.setData(data);
        }
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
        ImageButton isFavouriteButton;
        LineChart chart ;
        ViewHolder(View v) {
            super(v);
            currencyName = v.findViewById(R.id.currencyName);
            currencySymbol = v.findViewById(R.id.currencySymbol);
            priceChange = v.findViewById(R.id.currencyPriceChange);
            price = v.findViewById(R.id.currencyPrice);
            currencyIcon = v.findViewById(R.id.currencyIcon);
            isFavouriteButton = v.findViewById(R.id.iconIsFavouriteCurrency);
            chart = v.findViewById(R.id.currencyChart);
        }
    }
}
