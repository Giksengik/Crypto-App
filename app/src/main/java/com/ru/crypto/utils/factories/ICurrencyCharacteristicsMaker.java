package com.ru.crypto.utils.factories;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.ru.crypto.models.CryptoCurrency;
import com.ru.crypto.models.CurrencyCharacteristic;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface ICurrencyCharacteristicsMaker {

    ArrayList<CurrencyCharacteristic> getCharacteristics();

    void setCryptoCurrency(CryptoCurrency cryptoCurrency);

    String getCurrencyName();

    void setIcon(ImageView imageView);

}
