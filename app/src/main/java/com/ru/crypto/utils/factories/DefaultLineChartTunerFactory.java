package com.ru.crypto.utils.factories;

public class DefaultLineChartTunerFactory implements ILineChartTunerFactory {
    @Override
    public ILineChartTuner getLineChartTuner() {
        return new DefaultLineChartTuner();
    }
}
