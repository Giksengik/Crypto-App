package com.ru.crypto.utils.factories;

import com.github.mikephil.charting.charts.PieChart;

import java.util.TreeMap;

public interface IPieChartTuner {
    void setChartProperties(PieChart chart);
    void setData(PieChart chart, TreeMap<String,Double> pieData);
}
