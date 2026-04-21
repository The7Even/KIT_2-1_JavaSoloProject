package com.example.stockscoinviewer.model;

public class DomesticSearch {
    private String name;
    private String code;
    private String price;
    private String diff;

    public DomesticSearch(String name, String code,  String price, String diff) {
        this.name = name;
        this.code = code;
        this.price = price;
        this.diff = diff;
    }

    public String getName() { return name; }
    public String getPrice() { return price; }
    public String getDiff() { return diff; }

    @Override
    public String toString() {
        return name + " : " + code + " : " + price + " : " + diff;
    }
}
