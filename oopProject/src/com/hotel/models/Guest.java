package com.hotel.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.hotel.enums.*;
import com.hotel.exceptions.*;
import com.hotel.database.*;
import com.hotel.interfaces.*;

public class Guest {

    private String username;
    private String password;
    private LocalDate dateOfBirth;
    private double balance;
    private String address;
    private Gender gender;
    private List<RoomType> roomPreferences;
    private List<Reservation> reservations;

    public Guest() {
        this.reservations = new ArrayList<>();
        this.roomPreferences = new ArrayList<>();
    }

    public Guest(String username, String password, LocalDate dateOfBirth,
                 double balance, String address, Gender gender)
            throws InvalidUsernameException, Exception {

        setUsername(username);
        setPassword(password);
        setDateOfBirth(dateOfBirth);
        setBalance(balance);
        this.address = address;
        this.gender = gender;
        this.reservations = new ArrayList<>();
        this.roomPreferences = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) throws InvalidUsernameException {
        if (username == null || username.trim().isEmpty())
            throw new InvalidUsernameException("Username cannot be null or empty");
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws Exception {
        if (password == null)
            throw new Exception("Password cannot be null");
        if (password.length() < 6)
            throw new Exception("Password must be at least 6 characters");
        this.password = password;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth == null)
            throw new IllegalArgumentException("Date of birth cannot be null");
        if (dateOfBirth.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Invalid date of birth");
        this.dateOfBirth = dateOfBirth;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        if (balance < 0)
            throw new IllegalArgumentException("Balance cannot be negative");
        this.balance = balance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<RoomType> getRoomPreferences() {
        return roomPreferences;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void register() {
        for (Guest g : HotelDatabase.guests) {
            if (g.getUsername().equals(this.username)) {
                System.out.println("Username already exists!");
                return;
            }
        }
        HotelDatabase.guests.add(this);
        System.out.println("Registered successfully!");
    }

    public static Guest login(String username, String password) {
        for (Guest g : HotelDatabase.guests) {
            if (g.getUsername().equals(username) &&
                    g.getPassword().equals(password)) {
                System.out.println("Login successful!");
                return g;
            }
        }
        System.out.println("Invalid username or password!");
        return null;
    }

    public List<Room> viewAvailableRooms(RoomType type, View view, LocalDate in, LocalDate out)
            throws RoomNotAvailableException {

        List<Room> availableRooms = new ArrayList<>();

        for (Room room : HotelDatabase.rooms) {
            if (room.getType() == type &&
                    room.getView() == view &&
                    room.checkAvailabilityPeriod(in, out)) {

                availableRooms.add(room);
                System.out.println("-----------------------------");
                System.out.println("Room Number: " + room.getRoomNumber());
                System.out.println("Room Type:   " + room.getType());
                System.out.println("Room View:   " + room.getView());
                System.out.println("Room Floor:  " + room.getFloor());
                System.out.println("Price/night: $" + room.getPrice());
            }
        }

        if (availableRooms.isEmpty())
            throw new RoomNotAvailableException("No rooms available with these specifications.");

        return availableRooms;
    }

    public Room selectRoom(int roomNumber, List<Room> availableRooms) {
        for (Room room : availableRooms) {
            if (room.getRoomNumber() == roomNumber)
                return room;
        }
        throw new IllegalArgumentException("Selected room number is not in the available rooms list.");
    }

    public void viewAvailableAmenities() {
        if (HotelDatabase.amenities == null || HotelDatabase.amenities.isEmpty()) {
            System.out.println("No amenities available.");
            return;
        }
        System.out.println("Available Amenities:");

        for (int i = 0; i < HotelDatabase.amenities.size(); i++) {
            Amenity a = HotelDatabase.amenities.get(i);
            System.out.println(i + ": " + a.getName() + " | Price: $" + a.getPrice());
        }

    }

    public List<Amenity> getAvailableAmenities() {
        if (HotelDatabase.amenities == null || HotelDatabase.amenities.isEmpty())
            throw new IllegalStateException("No amenities available in the system.");
        return new ArrayList<>(HotelDatabase.amenities);
    }

    public List<Room> makeReservation(RoomType type, View view, LocalDate checkin, LocalDate checkout)
            throws RoomNotAvailableException {
        validateDate(checkin);
        validateDate(checkout);
        validateRange(checkin, checkout);
        return viewAvailableRooms(type, view, checkin, checkout);
    }

    public Reservation finalizeReservation(int roomNumber,
                                           List<Room> availableRooms,
                                           List<Amenity> selectedAmenities,
                                           LocalDate checkin, LocalDate checkout) throws Exception {

        Room selectedRoom = selectRoom(roomNumber, availableRooms);

        if (selectedAmenities != null && !selectedAmenities.isEmpty()) {
            for (Amenity a : selectedAmenities) {
                if (!HotelDatabase.amenities.contains(a))
                    throw new IllegalArgumentException("Invalid amenity: " + a.getName());
            }
        }

        Reservation reservation = new Reservation(selectedRoom, this, checkin, checkout);

        if (selectedAmenities != null && !selectedAmenities.isEmpty())
            reservation.setRequired_amenities(selectedAmenities);

        reservations.add(reservation);


        System.out.println("Reservation placed successfully!");
        System.out.println("Reservation ID: " + reservation.getID());
        System.out.println("Status: PENDING — awaiting staff confirmation.");
        System.out.println("Room: " + roomNumber);
        if (selectedAmenities == null || selectedAmenities.isEmpty()) {
            System.out.println("Amenities: None selected");
        } else {
            System.out.print("Amenities: ");
            for (int i = 0; i < selectedAmenities.size(); i++) {
                System.out.print(selectedAmenities.get(i).getName());
                if (i < selectedAmenities.size() - 1) System.out.print(", ");
            }
            System.out.println();
        }

        return reservation;
    }

    public void cancelReservation(int roomNumber) {

        Reservation toCancel = null;
        for (Reservation r : reservations) {
            if (r.getRoom().getRoomNumber() == roomNumber) {
                toCancel = r;
                break;
            }
        }

        if (toCancel == null) {
            System.out.println("No reservation found for room number " + roomNumber + ".");
            return;
        }

        if (toCancel.getStatus() == ReservationStatus.CANCELLED) {
            System.out.println("This reservation is already cancelled.");
            return;
        }

        if (toCancel.getStatus() == ReservationStatus.COMPLETED) {
            System.out.println("Cannot cancel a completed reservation.");
            return;
        }

        toCancel.cancel();
        reservations.remove(toCancel);
        System.out.println("Reservation for room " + roomNumber + " cancelled successfully.");
    }

    public void viewReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }
        System.out.println("Your Reservations:");
        for (Reservation r : reservations)
            System.out.println(r);
    }

    public void checkOut(int roomNumber, PaymentMethod method) throws InvalidPaymentException {
        for (Reservation r : reservations) {
            if (r.getRoom().getRoomNumber() == roomNumber) {

                if (r.getStatus() == ReservationStatus.COMPLETED) {
                    System.out.println("Already checked out.");
                    return;
                }

                if (r.getStatus() != ReservationStatus.CHECKED_IN) {
                    System.out.println("You are not checked in to this room.");
                    return;
                }

                if (r.getInvoice() == null)
                    throw new IllegalStateException("No invoice found for this reservation.");

                double total = r.getInvoice().calculateTotal();
                System.out.println("Total amount due: $" + total);

                if (r.getGuest().getBalance() < total)
                    throw new InvalidPaymentException("Insufficient balance.");

                r.getGuest().setBalance(r.getGuest().getBalance() - total);
                r.getInvoice().pay(method);
                r.setStatus(ReservationStatus.CHECKED_OUT);

                System.out.println("Payment successful. Status set to CHECKED_OUT.");
                System.out.println("Please proceed to the front desk.");
                return;
            }
        }
        System.out.println("No ongoing reservation found for room number " + roomNumber + ".");
    }

    private void validateDate(LocalDate d) {
        if (d == null)
            throw new IllegalArgumentException("Date cannot be null");
        if (d.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("Date cannot be in the past");
    }

    private void validateRange(LocalDate in, LocalDate out) {
        if (out.isBefore(in))
            throw new IllegalArgumentException("Check-out cannot be before check-in");
    }

    @Override
    public String toString() {
        return "Guest{" +
                "username='" + username + '\'' +
                ", balance=" + balance +
                ", address='" + address + '\'' +
                ", gender=" + gender +
                ", reservations=" + reservations.size() +
                '}';
    }
}
