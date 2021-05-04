package com.ru.crypto.utils;

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

public class DefaultLineChartXAxisValueFormatter extends IndexAxisValueFormatter {

    @Override
    public String getFormattedValue(float value) {
        return Converters.getFormattedDataStringByUnixTimestamp(value);
        }
}
