package com.hotel.models;

import java.time.LocalDate;
import com.hotel.database.HotelDatabase;
import com.hotel.enums.*;
import com.hotel.exceptions.*;
import com.hotel.database.*;
import com.hotel.interfaces.*;

public class Admin extends Staff {

    public Admin(String username, String password, LocalDate dateOfBirth, int workingHours) throws InvalidUsernameException {
        super(username, password, dateOfBirth, Role.ADMIN, workingHours);
        HotelDatabase.admins.add(this);

    }

    //add room
    public void addRoom(int roomNumber, RoomType type, boolean isAvailable, View view, int floor) {

        for (Room r :HotelDatabase.rooms) {
            if (r.getRoomNumber() == roomNumber) {
                throw new IllegalArgumentException("Room number already exists");
            }
        }


        Room room = new Room(roomNumber, type, isAvailable, view, floor);
        HotelDatabase.rooms.add(room);
        System.out.println("Room added successfully " );
    }



    //view room
    public void viewRoom(int roomNumber) {
        for (Room r : HotelDatabase.rooms) {
            if (r.getRoomNumber() == roomNumber) {


                System.out.println(r);

                //print amenities in the room
                System.out.println("Amenities:");
                if (r.getAmenities() != null && !r.getAmenities().isEmpty()) {

                    for (Amenity a : r.getAmenities()) {
                        System.out.println("- " + a.getName()
                                + "  Price: " + a.getPrice());
                    }

                } else {
                    System.out.println("No amenities available");
                }
                return;
            }
        }
        System.out.println("Room not found");
    }


    // update room
    public void updateRoom(int roomNumber, Integer newRoomNumber, RoomType type, Boolean isAvailable, Double price) {

        // check new room number doesn't already exist
        if (newRoomNumber != null) {
            for (Room r : HotelDatabase.rooms) {
                if (r.getRoomNumber() == newRoomNumber) {
                    throw new IllegalArgumentException("Room number " + newRoomNumber + " already exists");
                }
            }
        }
        for (Room r : HotelDatabase.rooms) {
            if (r.getRoomNumber() == roomNumber) {
                if (newRoomNumber != null)   r.setRoomNumber(newRoomNumber);
                if (type != null)            r.setType(type);
                if (isAvailable != null)     r.setAvailable(isAvailable);
                if (price != null)           r.setPrice(price);
                System.out.println("Room updated successfully");
                return;
            }
        }
        System.out.println("Room not found");
    }



    //update room number only
    public void updateRoomNumber(int roomNumber, int newRoomNumber) {
        updateRoom(roomNumber, newRoomNumber, null, null, null);
    }


    //  update type only
    public void updateRoom(int roomNumber, RoomType type) {
        updateRoom(roomNumber, null, type, null, null);
    }

    // update availability only
    public void updateRoom(int roomNumber, boolean isAvailable) {
        updateRoom(roomNumber, null, null, isAvailable, null);
    }



    public void deleteRoom(int roomNumber) {
        // check if room has active reservations first
        for (Reservation res : HotelDatabase.reservations) {
            if (res.getRoom().getRoomNumber() == roomNumber &&
                    (res.getStatus() == ReservationStatus.PENDING ||
                            res.getStatus() == ReservationStatus.CONFIRMED)) {
                throw new IllegalStateException("Cannot delete room with active reservations");
            }
        }

        for (int i = 0; i < HotelDatabase.rooms.size(); i++) {
            if (HotelDatabase.rooms.get(i).getRoomNumber() == roomNumber) {
                HotelDatabase.rooms.remove(i);
                System.out.println("Room deleted successfully");
                return;
            }
        }
        System.out.println("Room not found");
    }




    //Add Room Type
    public void addRoomType(String category, int capacity, double price) {

        RoomType type = new RoomType(category, capacity, price);

        HotelDatabase.roomTypes.add(type);

        System.out.println("RoomType added successfully!");
    }



    // view all room types
    public void viewAllRoomTypes() {

        if (HotelDatabase.roomTypes.isEmpty()) {
            System.out.println("No Room Types found");
            return;
        }

        for (RoomType t : HotelDatabase.roomTypes) {

            System.out.println(t);
        }
    }



    //view Room Type
    public void viewRoomType(int roomTypeId) {

        for (RoomType t : HotelDatabase.roomTypes) {

            if (t.getroomTypeId() == roomTypeId) {

                System.out.println(t);

                return;
            }
        }

        System.out.println("Room type not found");
    }



    // update room type
    public void updateRoomType(int roomTypeId, Integer newRoomTypeId, Integer newCapacity, Double newBasePrice) {

        if (newRoomTypeId != null) {
            for (RoomType t : HotelDatabase.roomTypes) {
                if (t.getroomTypeId() == newRoomTypeId) {
                    throw new IllegalArgumentException("RoomType ID already exists");
                }
            }
        }

        for (RoomType t : HotelDatabase.roomTypes) {
            if (t.getroomTypeId() == roomTypeId) {
                if (newRoomTypeId != null) t.setroomTypeId(newRoomTypeId);
                if (newCapacity != null)   t.setCapacity(newCapacity);
                if (newBasePrice != null)  t.setBasePrice(newBasePrice);
                System.out.println("RoomType updated successfully");
                return;
            }
        }
        System.out.println("RoomType not found");
    }

    // update room type id only
    public void updateRoomTypeId(int roomTypeId, int newRoomTypeId) {
        updateRoomType(roomTypeId, newRoomTypeId, null, null);
    }

    //update capacity only
    public void updateRoomType(int roomTypeId, int newCapacity) {
        updateRoomType(roomTypeId, null, newCapacity, null);
    }

    // update base price only
    public void updateRoomType(int roomTypeId, double newBasePrice) {
        updateRoomType(roomTypeId, null, null, newBasePrice);
    }



    // Delete room type
    public void deleteRoomType(int roomTypeId) {

        // check if any room is using this type
        for (Room r : HotelDatabase.rooms) {
            if (r.getType().getroomTypeId() == roomTypeId) {
                throw new IllegalStateException("Cannot delete room type that is assigned to a room");
            }
        }

        for (int i = 0; i < HotelDatabase.roomTypes.size(); i++) {
            if (HotelDatabase.roomTypes.get(i).getroomTypeId()  == roomTypeId) {

                HotelDatabase.roomTypes.remove(i);
                System.out.println("RoomType deleted successfully");
                return;
            }
        }

        System.out.println("RoomType not found");
    }




    //Add Amenity
    public void addAmenity(String name ,double  price ) {

        for (Amenity a : HotelDatabase.amenities) {
            if (a.getName().equalsIgnoreCase(name)) {
                System.out.println("Amenity already exists");
                return;
            }
        }

        Amenity a = new Amenity(name, price);
        HotelDatabase.amenities.add(a);
        System.out.println("Amenity added: " + name);

    }


    //View all amenity
    public void viewAllAmenities() {
        if (HotelDatabase.amenities.isEmpty()) {
            System.out.println("No amenities found");
            return;
        }


        for (Amenity a : HotelDatabase.amenities) {
            System.out.println(a);

        }
    }


    //Update Amenity
    public void updateAmenity(String Amenity , double newPrice) {
        for (Amenity a : HotelDatabase.amenities) {
            if (a.getName ().equalsIgnoreCase(Amenity)) {
                a.setPrice(newPrice);
                System.out.println("Amenity price updated successfully");
                return;
            }
        }
        System.out.println("Amenity not found");
    }


    //Delete Amenity
    public void deleteAmenity(String name) {
        // check if amenity is used in any room first
        for (Room r : HotelDatabase.rooms) {
            for (Amenity a : r.getAmenities()) {
                if (a.getName().equalsIgnoreCase(name)) {
                    throw new IllegalStateException("Cannot delete amenity that is assigned to a room");
                }
            }
        }

        for (int i = 0; i < HotelDatabase.amenities.size(); i++) {
            if (HotelDatabase.amenities.get(i).getName().equalsIgnoreCase(name)) {
                HotelDatabase.amenities.remove(i);
                System.out.println("Amenity deleted successfully");
                return;
            }
        }
        System.out.println("Amenity not found");
    }


    // add amenity to room
    public void addAmenityToRoom(int roomNumber, String amenityName) {

        for (Room r : HotelDatabase.rooms) {

            if (r.getRoomNumber() == roomNumber) {

                for (Amenity a : HotelDatabase.amenities) {

                    if (a.getName().equalsIgnoreCase(amenityName)) {

                        for (Amenity b : r.getAmenities()) {
                            if (b.getName().equalsIgnoreCase(amenityName)) {
                                System.out.println("Amenity already in room");
                                return;
                            }
                        }

                        r.addAmenity(a);

                        System.out.println("Amenity added to room " + roomNumber);
                        return;
                    }
                }

                System.out.println("Amenity not found in system");
                return;
            }
        }

        System.out.println("Room not found");
    }

    //view amenities in room
    public void viewAmenitiesInRoom(int roomNumber) {

        for (Room r : HotelDatabase.rooms) {

            if (r.getRoomNumber() == roomNumber) {

                if (r.getAmenities().isEmpty()) {
                    System.out.println("No amenities in this room");
                    return;
                }

                System.out.println(" \n Amenities in Room " + roomNumber + ":");

                for (Amenity a : r.getAmenities()) {
                    System.out.println("- " + a.getName() + "\tprice: " + a.getPrice() );
                }
                System.out.println("\n");
                return;
            }
        }

        System.out.println("Room not found");

    }


    //update amenity in room
    public void updateAmenityInRoom(int roomNumber, String amenity , double newPrice) {
        for (Room r : HotelDatabase.rooms) {
            if (r.getRoomNumber() == roomNumber) {

                for (Amenity a : r.getAmenities()) {
                    if (a.getName().equalsIgnoreCase(amenity)) {

                        a.setPrice(newPrice);

                        System.out.println("Amenity price updated in room");
                        return;
                    }
                }

                System.out.println("Amenity not found in room");
                return;
            }
        }

        System.out.println("Room not found");
    }




    //Remove amenity from room
    public void removeAmenityFromRoom(int roomNumber, String amenity) {

        for (Room r : HotelDatabase.rooms) {

            if (r.getRoomNumber() == roomNumber) {

                if (r.getAmenities().isEmpty()) {
                    System.out.println("No amenities to remove");
                    return;
                }

                for (int i = 0; i < r.getAmenities().size(); i++) {

                    if (r.getAmenities().get(i).getName().equalsIgnoreCase(amenity)) {

                        r.getAmenities().remove(i);

                        System.out.println("Amenity removed successfully");
                        return;
                    }
                }

                System.out.println("Amenity not found");
                return;
            }
        }

        System.out.println("Room not found");
    }

    // add receptionist account to system
    public void addReceptionist(String Username, String Password, LocalDate bday, int workinghours) throws InvalidUsernameException {
        Receptionist r = new Receptionist(Username, Password, bday, workinghours); // ← correct
        HotelDatabase.receptionists.add(r);
    }


}