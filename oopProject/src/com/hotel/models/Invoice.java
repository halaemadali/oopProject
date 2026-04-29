package com.hotel.models;

import java.time.LocalDate;
import com.hotel.enums.*;
import com.hotel.exceptions.*;
import com.hotel.database.*;
import com.hotel.interfaces.*;

public class Invoice implements Payable {
    private int ID;
    private Reservation reservation;
    private double amount;
    private LocalDate date;
    private boolean isPaid;
    private PaymentMethod method;

    public Invoice() {
        HotelDatabase.invoices.add(this);
    }

    public Invoice(Reservation r) {
        this.reservation = r;
        this.ID = this.reservation.getID();
        this.amount = calculateTotal();
        this.isPaid = false;
        HotelDatabase.invoices.add(this);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean getPaid() {
        return isPaid;
    }

    private void setPaid(boolean paid) {
        isPaid = paid;
    }

    @Override
    public void pay(PaymentMethod method) {
        if (isPaid)
            throw new IllegalStateException("Already paid.");
        this.method = method;
        this.date = LocalDate.now();
        this.isPaid = true;
    }

    public double calculateTotal() {
        if (reservation == null || reservation.getRoom() == null)
            return 0;
        return reservation.getDuration() * reservation.getRoom().getPrice();
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "ID=" + ID +
                ", reservation=" + (reservation != null ? reservation : "null") +
                ", amount=" + amount +
                ", date=" + (date != null ? date : "null") +
                ", isPaid=" + isPaid +
                ", method=" + (method != null ? method : "null") +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Invoice other = (Invoice) obj;
        return this.ID == other.ID;
    }
}