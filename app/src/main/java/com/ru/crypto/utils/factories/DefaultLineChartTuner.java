package com.ru.crypto.utils.factories;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.ru.crypto.R;
import com.ru.crypto.utils.Converters;
import com.ru.crypto.utils.DefaultLineChartXAxisValueFormatter;
import com.ru.crypto.utils.DefaultLineChatYAxisValueFormatter;


import java.util.ArrayList;
import java.util.List;

public class DefaultLineChartTuner implements ILineChartTuner {

    @Override
    public void setChartProperties(LineChart chart) {
        chart.setNoDataText("");

        chart.setBackgroundColor(Color.WHITE);
        chart.setDrawGridBackground(false);


        chart.getDescription().setEnabled(false);

        chart.setTouchEnabled(true);

        chart.getLegend().setEnabled(false);

        chart.getAxisRight().setValueFormatter(new DefaultLineChatYAxisValueFormatter());
        chart.getXAxis().setValueFormatter(new DefaultLineChartXAxisValueFormatter());

        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisLeft().setDrawLabels(false);
        chart.getAxisLeft().setDrawAxisLine(false);

        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setLabelCount(5);
    }

    public void setLinearChartData(LineChart chart, List<List<Double>> data) {
        ArrayList<Entry> chartValues = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            double tempVal1 =  data.get(i).get(1);
            double tempVal2 = data.get(i).get(0);
            float nextVal1 = (float)tempVal1;
            float nextVal2 = (float)tempVal2;
            chartValues.add(new Entry(nextVal2, nextVal1));
        }

        LineDataSet lineChartSet;
        boolean isNeedToUpdate = false;
        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            isNeedToUpdate = true;
        }

            lineChartSet = new LineDataSet(chartValues, "Default Line Chart");
            lineChartSet.setLineWidth(3f);

            lineChartSet.setColor(Color.BLACK);
            lineChartSet.setDrawCircleHole(false);
            lineChartSet.setDrawCircles(false);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(lineChartSet);
            LineData chartData = new LineData(dataSets);
            chartData.setDrawValues(false);
            chart.setData(chartData);
            if(isNeedToUpdate) {
                chart.invalidate();
            }
    }

    @Override
    public String getLastTime(List<List<Double>> data) {
        return Converters.getFormattedWithHourDataStringByUnixTimestamp(data.get(data.size() - 1).get(0));

    }

    @Override
    public String getLastTimePrice(List<List<Double>> data) {
        return "$" + data.get(data.size() - 1).get(1);
    }
}
