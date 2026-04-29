package com.hotel.enums;

public enum View {

    SEA_VIEW(200),
    POOL_VIEW(150),
    GARDEN_VIEW(100),
    CITY_VIEW(120);

    private final double price;

    View(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}