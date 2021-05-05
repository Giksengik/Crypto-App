package com.ru.crypto.models;

import java.util.SortedMap;
import java.util.TreeMap;

public class CurrencyCharacteristic {

    String title;

    TreeMap<String, String> characteristics;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TreeMap<String, String> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(TreeMap<String, String> characteristics) {
        this.characteristics = characteristics;
    }

    public CurrencyCharacteristic(String title, TreeMap<String, String> characteristics) {
        this.title = title;
        this.characteristics = characteristics;
    }
}
