package com.ru.crypto.utils;

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

public class LineChatYAxisValueFormatterBitcoin extends IndexAxisValueFormatter {

    @Override
    public String getFormattedValue(float value) {
        int val = (int)value;
        return "$" + val;
    }
}
