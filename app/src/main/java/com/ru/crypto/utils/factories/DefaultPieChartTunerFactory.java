package com.ru.crypto.utils.factories;

public class DefaultPieChartTunerFactory implements IPieChartTunerFactory {
    @Override
    public IPieChartTuner getTuner() {
        return new DefaultPieChartTuner();
    }
}
