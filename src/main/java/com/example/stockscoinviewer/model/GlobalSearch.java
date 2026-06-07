package com.example.stockscoinviewer.model;

import com.example.stockscoinviewer.ui.TabType;

public class GlobalSearch implements FavoriteAble {
    private String name;
    private String code;
    private String price;
    private String diff;

    public GlobalSearch(String name, String code, String price, String diff) {
        this.name = name;
        this.code = code;
        this.price = price;
        this.diff = diff;
    }

    public String getName() { return name; }
    public String getPrice() { return price; }
    public String getDiff() { return diff; }
    @Override
    public String getCode() { return code; }

    @Override
    public TabType getMarket() { return TabType.GLOBAL; }

    @Override
    public String toString() {
        return name + " : " + code + " : " + price + " : " + diff;
    }
}
