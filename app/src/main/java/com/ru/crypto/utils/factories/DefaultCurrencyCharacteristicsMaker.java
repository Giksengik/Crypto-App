package com.ru.crypto.utils.factories;

import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ru.crypto.adapters.CharacteristicsBlockAdapter;
import com.ru.crypto.models.CryptoCurrency;
import com.ru.crypto.models.CurrencyCharacteristic;
import com.ru.crypto.utils.Converters;

import java.util.ArrayList;
import java.util.TreeMap;

public class DefaultCurrencyCharacteristicsMaker implements ICurrencyCharacteristicsMaker {

    private CryptoCurrency currency;

    @Override
    public ArrayList<CurrencyCharacteristic> getCharacteristics() {
        ArrayList<CurrencyCharacteristic> characteristics = new ArrayList<>();

        TreeMap<String,String> priceMap = new TreeMap<>();
        TreeMap<String, String> changeMap = new TreeMap<>();
        TreeMap<String, String> marketMap = new TreeMap<>();
        TreeMap<String, String> supplyMap = new TreeMap<>();

        priceMap.put("USD","$" + currency.getCurrentPrice());

        String dayChange = currency.getPriceChangePercentage24H() + "%";
        changeMap.put("24H", dayChange);

        marketMap.put("Market cap rank", currency.getMarketCapRank() + "#");
        marketMap.put("Cap", "$" + currency.getMarketCap());

        characteristics.add(new CurrencyCharacteristic("PRICE", priceMap));
        characteristics.add(new CurrencyCharacteristic("CHANGE", changeMap));
        characteristics.add(new CurrencyCharacteristic("MARKET", marketMap));

        return characteristics;
    }

    @Override
    public void setCryptoCurrency(CryptoCurrency cryptoCurrency) {
        this.currency = cryptoCurrency;
    }

    @Override
    public String getCurrencyName() {
        return currency.getName();
    }

    @Override
    public void setIcon(ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(currency.getImageUrl()).apply(new RequestOptions().circleCrop())
                .into(imageView);
    }

}
