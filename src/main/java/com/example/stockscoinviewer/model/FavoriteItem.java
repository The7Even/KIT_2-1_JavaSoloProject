package com.example.stockscoinviewer.model;

import com.example.stockscoinviewer.ui.TabType;

public class FavoriteItem {
    private final TabType market;
    private final String code;
    private final String name;

    public  FavoriteItem(TabType market, String code, String name) {
        this.market = market;
        this.code = code;
        this.name = name;
    }

    public TabType getMarket() { return market; }
    public String getCode() { return code; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return market + "," + code + "," + name;
    }
}
