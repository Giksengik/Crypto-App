package com.ru.crypto.ui.global;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.ru.crypto.MainActivity;
import com.ru.crypto.R;
import com.ru.crypto.adapters.LineChartXAxisValueFormatter;
import com.ru.crypto.models.HistoricalCurrencyData;


import java.util.ArrayList;
import java.util.TreeMap;

public class GlobalFragment extends Fragment {

     private GlobalViewModel mGlobalViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeToolbar();
        mGlobalViewModel = new ViewModelProvider(this).get(GlobalViewModel.class);
    }

    public void initializeToolbar() {
        Toolbar toolbar  = getActivity().findViewById(R.id.toolbar);
        if(toolbar != null) {
            toolbar.setTitle("Global");
            toolbar.getMenu().getItem(0).setVisible(false);
            toolbar.getMenu().getItem(1).setVisible(false);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_global, container, false);
        PieChart chart = root.findViewById(R.id.globalPercentageChart);
        getViewLifecycleOwner();

        GlobalStatsFragment globalStatsFragment = new GlobalStatsFragment();
        getChildFragmentManager().beginTransaction()
                .add(R.id.marketStatsContainer,globalStatsFragment)
                .commit();

        mGlobalViewModel.getGlobalData().observe(getViewLifecycleOwner(), globalCryptoData -> {
            setPieChartData(globalCryptoData.getData().getMarketCapPercentage(), chart);
            globalStatsFragment.setData(globalCryptoData);
        });

        mGlobalViewModel.getBitcoinData().observe(getViewLifecycleOwner(), historicalCurrencyData -> {
            setBitcoinChartData(root.findViewById(R.id.bitcoinPriceLineChart), historicalCurrencyData.getPrices());
        });
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        mGlobalViewModel.loadGlobalData();

    }
    public void setBitcoinChartData(LineChart chart, ArrayList<ArrayList<Double>> prices) {

        chart.setBackgroundColor(Color.WHITE);
        chart.setDrawGridBackground(false);
        chart.getDescription().setEnabled(false);

        chart.setTouchEnabled(true);

        chart.getLegend().setEnabled(false);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setValueFormatter(new LineChartXAxisValueFormatter());
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisLeft().setDrawLabels(false);
        chart.getAxisLeft().setDrawAxisLine(false);
        chart.getLegend().setDrawInside(false);
        chart.getXAxis().setLabelCount(5);
        ArrayList<Entry> values1 = new ArrayList<>();
        for (int i = 0; i < prices.size(); i++) {
            double tempVal1 =  prices.get(i).get(1);
            double tempVal2 = prices.get(i).get(0);
            float nextVal1 = (float)tempVal1;
            float nextVal2 = (float)tempVal2;
            values1.add(new Entry(nextVal2, nextVal1));
        }

        LineDataSet set1;
        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values1);
            set1.notifyDataSetChanged();
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values1, "DataSet 1");
            set1.setLineWidth(3f);


            set1.setDrawCircleHole(false);
            set1.setDrawCircles(false);

            set1.setValueTextSize(15f);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);

            chart.setData(data);
        }
    }
    public void setPieChartData(TreeMap<String,Double> pieData, PieChart chart) {
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.animate();
        if (pieData != null) {
            ArrayList<PieEntry> entries = new ArrayList<>();
            for (Double percentage : pieData.values()) {
                double temp_val = percentage;
                float val = (float) temp_val;
                entries.add(new PieEntry(val));
            }
            PieDataSet dataSet = new PieDataSet(entries, "Election Results");
            dataSet.setDrawIcons(false);
            dataSet.setSliceSpace(3f);
            dataSet.setIconsOffset(new MPPointF(0, 40));
            dataSet.setSelectionShift(5f);
            ArrayList<Integer> colors = new ArrayList<>();

            for (int c : ColorTemplate.VORDIPLOM_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.JOYFUL_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.COLORFUL_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.LIBERTY_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.PASTEL_COLORS)
                colors.add(c);
            colors.add(ColorTemplate.getHoloBlue());

            dataSet.setColors(colors);
            PieData dataPie = new PieData(dataSet);
            dataPie.setValueFormatter(new PercentFormatter());
            dataPie.setValueTextSize(11f);
            dataPie.setValueTextColor(Color.WHITE);
            dataPie.setValueTypeface(Typeface.DEFAULT);
            chart.setData(dataPie);

            chart.invalidate();
            }

        }
}
