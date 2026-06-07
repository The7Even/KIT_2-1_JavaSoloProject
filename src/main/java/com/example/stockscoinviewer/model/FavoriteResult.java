package com.example.stockscoinviewer.model;

public class FavoriteResult {
    private String marketType;
    private String name;
    private String code;
    private String price;
    private String Change;

    public FavoriteResult(String marketType, String name, String code, String price, String change) {
        this.marketType = marketType;
        this.name = name;
        this.code = code;
        this.price = price;
        this.Change = change;
    }

    public String getMarketType() { return marketType; }
    public String getName() { return name; }
    public String getCode() { return code; }
    public String getPrice() { return price; }
    public String getChange() { return Change; }
}
