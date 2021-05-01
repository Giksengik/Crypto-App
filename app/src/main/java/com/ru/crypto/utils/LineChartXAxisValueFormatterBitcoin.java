package com.ru.crypto.utils;

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.ru.crypto.utils.Converters;

public class LineChartXAxisValueFormatterBitcoin extends IndexAxisValueFormatter {

    @Override
    public String getFormattedValue(float value) {
        return Converters.getFormattedDataStringByUnixTimestamp(value);
        }
}
