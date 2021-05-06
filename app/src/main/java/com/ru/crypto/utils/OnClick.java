package com.ru.crypto.utils;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;


public class OnClick {

     public static OnChartValueSelectedListener onLineChartValue(TextView priceValue) {
        return new OnChartValueSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                priceValue.setText(Converters.getFormattedWithHourDataStringByUnixTimestamp(e.getX()) + " : " + e.getY() + "$");
            }

            @Override
            public void onNothingSelected() {

            }
        };
     }
    public static OnChartValueSelectedListener onLineChartValue(TextView priceValue, TextView time) {
        return new OnChartValueSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                priceValue.setText(e.getY() + "$");
                time.setText(Converters.getFormattedWithHourDataStringByUnixTimestamp(e.getX()));
            }

            @Override
            public void onNothingSelected() {

            }
        };
    }
}
