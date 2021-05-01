package com.ru.crypto.utils.factories;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class DefaultPieChartTuner implements IPieChartTuner {
    @Override
    public void setChartProperties(PieChart chart) {
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.animate();
        chart.getLegend().setEnabled(false);
        SpannableString s = new SpannableString("Market cap distribution");
        s.setSpan(new RelativeSizeSpan(1.7f),0, s.length(),0);
        s.setSpan(new StyleSpan(Typeface.NORMAL),0, s.length(),0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY),0, s.length(),0);
        chart.setCenterText(s);
    }

    @Override
    public void setData(PieChart chart, TreeMap<String, Double> pieData) {
        if (pieData != null) {
            ArrayList<PieEntry> entries = new ArrayList<>();
            float other = 100;
            for (Map.Entry<String, Double> entry : pieData.entrySet()) {
                double temp_val = entry.getValue();
                float val = (float) temp_val;
                other -= val;
                if(entry.getValue() > 3) {
                    entries.add(new PieEntry(val ,entry.getKey()));
                }
            }
            entries.add(new PieEntry(other, "other"));

            PieDataSet dataSet = new PieDataSet(entries,"Crypto Currencies");

            dataSet.setValueFormatter(new PercentFormatter());

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
