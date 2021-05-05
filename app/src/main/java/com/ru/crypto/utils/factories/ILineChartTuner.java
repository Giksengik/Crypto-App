package com.ru.crypto.utils.factories;

import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.List;

public interface ILineChartTuner  {

    void setChartProperties(LineChart chart);

    void setLinearChartData(LineChart chart, List<List<Double>> data);

    String getLastTime(List<List<Double>> data);

    String getLastTimePrice(List<List<Double>> data);
}
