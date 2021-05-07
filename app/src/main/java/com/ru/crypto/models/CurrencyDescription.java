package com.ru.crypto.models;

import java.util.TreeMap;

public class CurrencyDescription {
    TreeMap<String, String> descriptions;

    public TreeMap<String, String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(TreeMap<String, String> descriptions) {
        this.descriptions = descriptions;
    }
}
