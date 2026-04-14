package com.example.stockscoinviewer.model;

public class CoinVolume {
    private String name;
    private double volume;
    private double price;

    public CoinVolume(String name, double volume, double price) {
        this.name = name;
        this.volume = volume;
        this.price = price;
    }

    public String getName() { return name; }
    public double getVolume() { return volume; }
    public double getPrice() { return price; }
}
