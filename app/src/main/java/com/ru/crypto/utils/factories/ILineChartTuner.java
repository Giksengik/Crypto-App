package com.ru.crypto.utils.factories;

import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;

public interface ILineChartTuner {

    void setChartProperties(LineChart chart);

    void setLinearChartData(LineChart chart, ArrayList<ArrayList<Double>> data);
}
