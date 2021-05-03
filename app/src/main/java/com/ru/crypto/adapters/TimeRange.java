package com.ru.crypto.adapters;

import org.jetbrains.annotations.NotNull;

public enum TimeRange {

    ALL_TIME_RANGE("ALL"),
    THREE_YEAR_TIME_RANGE("3Y"),
    ONE_YEAR_TIME_RANGE("1Y"),
    SIX_MONTH_TIME_RANGE("6M"),
    THREE_MONTH_TIME_RANGE("3M"),
    ONE_MONTH_TIME_RANGE("1M"),
    ONE_WEEK_TIME_RANGE("1W"),
    ONE_DAY_TIME_RANGE("1D");


    private String title;

    TimeRange(String title) {
        this.title = title;
    }

    @NotNull
    @Override
    public String toString() {
        return title;
    }
}
