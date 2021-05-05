package com.ru.crypto.utils.factories;

public class DefaultCurrencyCharacteristicsMakerFactory implements  ICurrencyCharacteristicsMakerFactory {
    @Override
    public ICurrencyCharacteristicsMaker getMaker() {
        return new DefaultCurrencyCharacteristicsMaker();
    }
}
