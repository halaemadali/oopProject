package com.hotel.models;

import java.time.LocalDate;
import com.hotel.enums.*;
import com.hotel.exceptions.*;
import com.hotel.database.*;
import com.hotel.interfaces.*;

public class Receptionist extends Staff {

    public Receptionist(String username, String password, LocalDate dateOfBirth, int workingHours) throws InvalidUsernameException {

        super(username, password, dateOfBirth, Role.RECEPTIONIST, workingHours);
    }


    public void checkIn(String username, int reservationId) {
        for (Reservation r : HotelDatabase.reservations) {
            if (r.getID() == reservationId) {

                if (r.getGuest() == null || !r.getGuest().getUsername().equalsIgnoreCase(username)) {
                    System.out.println("This reservation does not belong to guest: " + username + ".");
                    return;
                }

                if (r.getStatus() == ReservationStatus.CANCELLED) {
                    System.out.println("Cannot check in a cancelled reservation.");
                    return;
                }

                if (r.getStatus() == ReservationStatus.COMPLETED) {
                    System.out.println("This reservation is already completed.");
                    return;
                }

                if (r.getStatus() == ReservationStatus.CHECKED_IN) {
                    System.out.println("Guest already checked in.");
                    return;
                }

                if (r.getStatus() != ReservationStatus.CONFIRMED) {
                    System.out.println("Reservation must be CONFIRMED before check-in.");
                    return;
                }

                for (int i = 0; i < r.getRequired_amenities().size(); i++) {
                    r.getRoom().addAmenity(r.getRequired_amenities().get(i));
                }

                r.getRoom().setAvailable(false);
                r.setStatus(ReservationStatus.CHECKED_IN);
                System.out.println("Guest " + username + " checked in successfully.");
                return;
            }
        }
        System.out.println("No reservation found with this ID.");
    }

    public void processGuestCheckout(String username, int roomNumber, PaymentMethod method) {

        Reservation targetReservation = null;
        for (Reservation r : HotelDatabase.reservations) {
            if (r.getRoom().getRoomNumber() == roomNumber) {
                targetReservation = r;
                break;
            }
        }

        if (targetReservation == null) {
            System.out.println("No reservation found for room number " + roomNumber + ".");
            return;
        }

        if (targetReservation.getStatus() != ReservationStatus.CHECKED_IN) {
            System.out.println("Guest is not checked in to room " + roomNumber + ".");
            return;
        }

        Guest guest = targetReservation.getGuest();
        if (guest == null) {
            System.out.println("No guest found for this reservation.");
            return;
        }
        if (!guest.getUsername().equalsIgnoreCase(username)) {
            System.out.println("This reservation does not belong to guest: " + username + ".");
            return;
        }

        if (targetReservation.getInvoice() == null) {
            System.out.println("No invoice found for this reservation.");
            return;
        }

        // If already paid from system, just complete
        if (targetReservation.getInvoice().getPaid()) {
            System.out.println("Invoice already paid. Completing reservation...");
            completeReservation(targetReservation.getID());
            return;
        }

        try {
            guest.checkOut(roomNumber, method);
        } catch (InvalidPaymentException e) {
            System.out.println("Checkout failed: " + e.getMessage());
            return;
        }


        completeReservation(targetReservation.getID());
    }

}