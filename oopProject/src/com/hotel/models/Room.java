package com.hotel.models;

import java.time.LocalDate;
import java.util.ArrayList;
import com.hotel.enums.*;
import com.hotel.exceptions.*;
import com.hotel.database.*;
import com.hotel.interfaces.*;

public class Room {
    private int roomNumber;
    private RoomType type;
    private boolean isAvailable;
    private double price;
    private View view;
    private int floor;
    private ArrayList<Amenity> Amenities = new ArrayList<>();

    private static final int MAXROOMNUMBER = 1000000;
    private static final double MAXPRICE = 1000000;

    public Room() {
        //HotelDatabase.rooms.add(this);
    }

    public Room(int roomNumber, RoomType type, boolean isAvailable, View v, int f) {
        setRoomNumber(roomNumber);
        setType(type);
        setAvailable(isAvailable);
        setFloor(f);
        setView(v);
        this.price = calculateRoomPrice();
        //HotelDatabase.rooms.add(this);
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        if (type == null)
            throw new IllegalArgumentException("Room type cannot be null");
        this.type = type;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        if (roomNumber <= 0)
            throw new IllegalArgumentException("Room number must be positive");
        if (roomNumber > MAXROOMNUMBER)
            throw new IllegalArgumentException("Room number too large");
        for (Room room : HotelDatabase.rooms) {
            if (room != this && room.getRoomNumber() == roomNumber)
                throw new IllegalArgumentException("Room number is already taken");
        }
        this.roomNumber = roomNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (Double.isNaN(price) || Double.isInfinite(price))
            throw new IllegalArgumentException("Price cannot be NaN or infinite");
        if (price <= 0)
            throw new IllegalArgumentException("Price must be greater than 0");
        if (price > MAXPRICE)
            throw new IllegalArgumentException("Price exceeds maximum allowed");
        if (type != null && price < type.getBasePrice())
            throw new IllegalArgumentException("Price cannot be less than base price of room type");
        this.price = price;
    }

    public void setFloor(int floor) {
        if (floor < 0 || floor > 5)
            throw new IllegalArgumentException("Floor can only be from 0 to 5");
        this.floor = floor;
    }

    public void setView(View view) {
        this.view = view;
    }

    public int getFloor() {
        return floor;
    }

    public View getView() {
        return view;
    }

    public double calculateRoomPrice() {
        double amenitiesTOT = 0;
        for (int i = 0; i < Amenities.size(); i++)
            amenitiesTOT += Amenities.get(i).getPrice();
        return this.type.getBasePrice() + amenitiesTOT + this.view.getPrice();
    }

    public boolean getisavailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public void checkAvailability() {
        if (this.isAvailable)
            System.out.println("Room is available");
        else
            System.out.println("Room is not available");
    }

    public boolean checkAvailabilityPeriod(LocalDate start, LocalDate end) {
        for (Reservation r : HotelDatabase.reservations) {
            if (r.getRoom().equals(this)) {
                if (r.getStatus() != ReservationStatus.CANCELLED &&
                        r.getStatus() != ReservationStatus.COMPLETED) {
                    if (!(end.isBefore(r.getCheckin()) || start.isAfter(r.getCheckout()))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public ArrayList<Amenity> getAmenities() {
        return new ArrayList<>(Amenities);
    }

    public void addAmenity(Amenity amenity) {
        if (amenity == null)
            throw new IllegalArgumentException("Amenity cannot be null");
        if (Amenities.contains(amenity))
            throw new IllegalArgumentException("Amenity already exists");
        if (Amenities.size() >= 20)
            throw new IllegalStateException("Maximum number of amenities reached");
        Amenities.add(amenity);
        System.out.println("Amenity added successfully!");
    }

    public void removeAmenity(Amenity amenity) {
        if (amenity == null)
            throw new IllegalArgumentException("Amenity cannot be null");
        if (!Amenities.contains(amenity))
            throw new IllegalArgumentException("Amenity not found");
        Amenities.remove(amenity);
        System.out.println("Amenity removed successfully!");
    }

    public void validateRoom() {
        if (type == null)
            throw new IllegalStateException("Room type is not set");
        if (price < type.getBasePrice())
            throw new IllegalStateException("Invalid price: less than base price");
        if (Amenities.isEmpty())
            System.out.println("Warning: Room has no amenities");
    }

    public void clearAmenities() {
        Amenities.clear();
    }

    @Override
    public String toString() {
        return "Room " + roomNumber +
                ", Type: " + type +
                ", View: " + view +
                ", Floor: " + floor +
                ", Price: " + price +
                ", Available: " + (isAvailable ? "Yes" : "No");
    }
}