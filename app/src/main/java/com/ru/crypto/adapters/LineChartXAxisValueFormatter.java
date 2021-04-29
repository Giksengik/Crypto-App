package com.ru.crypto.adapters;

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.ru.crypto.Converters;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class LineChartXAxisValueFormatter extends IndexAxisValueFormatter {

    @Override
    public String getFormattedValue(float value) {
        Date timeMilliseconds = Converters.convertUnixTimestamp(value/1000);
        DateFormat dateTimeFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
        return dateTimeFormat.format(timeMilliseconds);
    }
}
