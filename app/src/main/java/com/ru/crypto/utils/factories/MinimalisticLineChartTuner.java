package com.ru.crypto.utils.factories;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class MinimalisticLineChartTuner {

    public void setChartProperties(LineChart chart) {
        chart.setBackgroundColor(Color.WHITE);

        chart.setDrawGridBackground(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setDrawLabels(false);
        chart.getXAxis().setDrawAxisLine(false);

        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisLeft().setDrawLabels(false);
        chart.getAxisLeft().setDrawAxisLine(false);

        chart.getAxisRight().setDrawGridLines(false);
        chart.getAxisRight().setDrawLabels(false);
        chart.getAxisRight().setDrawAxisLine(false);

        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.setTouchEnabled(false);
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);
        chart.setPinchZoom(false);
        chart.setDrawBorders(false);
        Legend l = chart.getLegend();
        l.setEnabled(false);

    }

    public void setLinearChartData(LineChart chart, List<Double> data) {
        if (data != null) {
            ArrayList<Entry> values = new ArrayList<>();
            for (int i = Math.max(data.size() - 72, 0); i < data.size(); i++) {
                double temp_val = data.get(i);
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
                if(data.get(data.size() - 1) - data.get(0)  < 0) {
                    set1.setColor(Color.RED);
                }else
                    set1.setColor(Color.GREEN);
                set1.setLineWidth(1f);
            }
            set1.setDrawValues(false);
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            set1.setDrawCircles(false);
            dataSets.add(set1);
            LineData chartData = new LineData(dataSets);
            chart.setData(chartData);
        }
    }
}
