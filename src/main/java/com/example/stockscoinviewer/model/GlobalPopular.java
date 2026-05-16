package com.example.stockscoinviewer.model;

public class GlobalPopular {
    private String name;
    private String price;

    public GlobalPopular(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public String getPrice() { return price; }
    public String toString() { return name + " " + price; }
}
