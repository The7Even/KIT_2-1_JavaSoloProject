package com.example.stockscoinviewer.model;

import com.example.stockscoinviewer.ui.TabType;

public class CoinPrice implements FavoriteAble {
    private String name;
    private double price;

    public CoinPrice(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    @Override
    public String getCode() {
        return name;
    }

    @Override
    public TabType getMarket() {
        return TabType.CRYPTO;
    }
}
