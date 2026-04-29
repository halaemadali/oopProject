package com.hotel.models;

import com.hotel.enums.*;
import com.hotel.exceptions.*;
import com.hotel.database.*;
import com.hotel.interfaces.*;

public class RoomType {
    //single,double,suite
    private String category;
    private int capacity;
    private double BasePrice;
    private int roomTypeId;
    private static int numofroomtypes =0;


    public RoomType(){
        numofroomtypes ++;
        this.roomTypeId = numofroomtypes;

    }

    public RoomType(String type, int capacity, double price) {
        numofroomtypes++;
        this.roomTypeId = numofroomtypes;
        setCategory(type);
        if (this.capacity == 0)  // only set manually if setCategory didn't auto-assign it
            setCapacity(capacity);
        setBasePrice(price);

    }

    public String getCategory(){return category;}

    public void setCategory(String type) {
        if (type == null || type.trim().isEmpty())
            throw new IllegalArgumentException("Room type cannot be empty");
        if (!type.matches("[a-zA-Z ]+"))
            throw new IllegalArgumentException("Category must contain letters only");
        for (RoomType rt : HotelDatabase.roomTypes) {
            if (rt.getCategory() != null && rt.getCategory().equalsIgnoreCase(type))
                throw new IllegalArgumentException("Room type category already exists");
        }
        this.category = type;
        if (type.equalsIgnoreCase("Single")) {
            this.capacity = 1;
            System.out.println("Category is Single, capacity automatically set to 1");
        } else if (type.equalsIgnoreCase("Double")) {
            this.capacity = 2;
            System.out.println("Category is Double, capacity automatically set to 2");
        } else if (type.equalsIgnoreCase("Triple")) {
            this.capacity = 3;
            System.out.println("Category is Triple, capacity automatically set to 3");
        }
    }

    public void setCapacity(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        if (capacity >=10) {
            throw new IllegalArgumentException("Capacity cannot be more than 9");
        }

        if (category != null &&
                (category.equalsIgnoreCase("Single") ||
                        category.equalsIgnoreCase("Double") ||
                        category.equalsIgnoreCase("Triple"))) {

            throw new IllegalArgumentException("Capacity is fixed for this category");
        }

        this.capacity = capacity;
    }
    public int getCapacity() {
        return capacity;
    }

    public  double getBasePrice(){
        return  BasePrice;
    }
    public void setBasePrice(double basePrice){
        if (basePrice <= 0) {
            throw new IllegalArgumentException("Base price must be >0");
        }
        if (basePrice > 10000000) { //  upper limit
            throw new IllegalArgumentException("Price very high");
        }
        this.BasePrice = basePrice;
        System.out.println(" price of  " +getCategory()+ " rooms is updated with new price :"+getBasePrice());


    }

    public void setroomTypeId(int roomTypeId){
        this.roomTypeId=roomTypeId;
    }
    public int getroomTypeId(){
        return roomTypeId;
    }


    @Override
    public String toString() {
        return "ID: " + roomTypeId + ", " +
                category +
                " (Capacity: " + capacity +
                ", Base Price: " + BasePrice +
                ")";
    }

}