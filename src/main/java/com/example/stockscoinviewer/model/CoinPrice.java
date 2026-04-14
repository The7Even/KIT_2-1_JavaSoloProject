package com.example.stockscoinviewer.model;

public class CoinPrice {
    private String name;
    private double price;

    public CoinPrice(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
}
