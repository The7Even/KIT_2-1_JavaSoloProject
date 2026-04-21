package com.example.stockscoinviewer.model;

public class DomesticPopular {
    private String name;
    private String price;

    public DomesticPopular(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public String getPrice() { return price; }
}
