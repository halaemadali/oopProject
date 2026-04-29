package com.hotel.models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import com.hotel.enums.*;
import com.hotel.exceptions.*;
import com.hotel.database.*;
import com.hotel.interfaces.*;

public class Reservation {
    private int ID;
    private Room room;
    private Guest guest;
    private LocalDate checkin;
    private LocalDate checkout;
    private ReservationStatus status;
    private Invoice invoice;
    private static int numofreservations = 0;
    private List<Amenity> required_amenities = new ArrayList<>();

    public Reservation(){
        numofreservations++;
        this.ID = numofreservations;
        this.status = ReservationStatus.PENDING;
        HotelDatabase.reservations.add(this);
    }

    public Reservation(Room r, Guest g, LocalDate in, LocalDate out) throws Exception {
        numofreservations++;
        this.ID = numofreservations;

        this.guest = g;

        setCheckin(in);
        setCheckout(out);

        validateRange(in, out);

        setRoom(r, in, out);

        setStatus(ReservationStatus.PENDING);

        generateInvoice();
        HotelDatabase.reservations.add(this);
    }

    private void setID(int ID) {
        this.ID = ID;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public void setRoom(Room room, LocalDate in, LocalDate out) throws RoomNotAvailableException {
        if (!room.checkAvailabilityPeriod(in, out))
            throw new RoomNotAvailableException("Room is booked during this period.");
        this.room = room;
    }

    public void setRoom(Room room) throws Exception {
        if (!room.checkAvailabilityPeriod(this.checkin, this.checkout))
            throw new RoomNotAvailableException("Room is booked during this period.");
        this.room = room;
    }

    public void setCheckin(LocalDate checkin) {
        validateDate(checkin);
        if (this.checkout != null)
            validateRange(checkin, this.checkout);
        this.checkin = checkin;
    }

    public void setCheckout(LocalDate checkout) {
        validateDate(checkout);
        if (this.checkin != null)
            validateRange(this.checkin, checkout);
        this.checkout = checkout;
    }

    public Room getRoom() {
        return room;
    }

    public int getID() {
        return ID;
    }

    public Guest getGuest() {
        return guest;
    }

    public LocalDate getCheckin() {
        return checkin;
    }

    public LocalDate getCheckout() {
        return checkout;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public Invoice getInvoice() {
        return this.invoice;
    }

    public List<Amenity> getRequired_amenities() {
        return required_amenities;
    }

    public void setRequired_amenities(List<Amenity> required_amenities) {
        this.required_amenities = required_amenities;
    }

    public int getDuration() {
        if (checkin == null || checkout == null) return 0;
        long days = ChronoUnit.DAYS.between(checkin, checkout);
        return days < 1 ? 1 : (int) days;
    }

    private void validateDate(LocalDate d) {
        if (d == null)
            throw new IllegalArgumentException("Selected date cannot be null");
        if (d.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("Selected date is before current date.");
    }

    private void validateRange(LocalDate in, LocalDate out) {
        if (out.isBefore(in))
            throw new IllegalArgumentException("The check out date cannot be before check in date.");
    }

    public void confirm() {
        setStatus(ReservationStatus.CONFIRMED);
    }

    public void cancel() {
        setStatus(ReservationStatus.CANCELLED);
    }

    public void complete() {
        setStatus(ReservationStatus.COMPLETED);
    }

    public void generateInvoice() {
        if (this.invoice != null)
            throw new IllegalStateException("An invoice is already created for this reservation.");
        invoice = new Invoice(this);
    }

    @Override
    public String toString() {
        return "Reservation ID: " + ID +
                ", Guest: " + (guest != null ? guest.getUsername() : "null") +
                ", Room: " + (room != null ? room.getRoomNumber() : "null") +
                ", Check-in: " + (checkin != null ? checkin : "null") +
                ", Check-out: " + (checkout != null ? checkout : "null") +
                ", Status: " + (status != null ? status : "null");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Reservation other = (Reservation) obj;
        return this.ID == other.ID;
    }
}