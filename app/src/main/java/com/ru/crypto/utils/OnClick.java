package com.ru.crypto.utils;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.List;

public class OnClick {

     public static OnChartValueSelectedListener onLineChartValue(TextView priceValue) {
        return new OnChartValueSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                priceValue.setText(Converters.getFormattedDataStringByUnixTimestamp(e.getX()) + " " + e.getY() + "$");
            }

            @Override
            public void onNothingSelected() {

            }
        };
     }
}
