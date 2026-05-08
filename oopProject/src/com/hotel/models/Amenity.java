package com.hotel.models;

import com.hotel.database.HotelDatabase;

public class Amenity {
    private String name;
    private double price;   // price per unit

    public Amenity(String name, double price) {
        setName(name);
        setPrice(price);

    }

    public String getName() {

        return name;
    }

    public double getPrice() {
        return price;
    }


    public void setName(String name) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Amenity name cannot be null or empty");
        }
        for (Amenity a : HotelDatabase.amenities) {

            if (a != this &&
                    a.getName().equalsIgnoreCase(name)) {

                throw new IllegalArgumentException(
                        "Amenity name already exists");
            }
        }

        this.name = name;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }


    @Override
    public String toString(){
        return    "( " + name +
                " price: $"  + price + " )";

    }

}