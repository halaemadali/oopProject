import java.time.LocalDate;

public class Receptionist extends Staff {

    public Receptionist(String username, String password, LocalDate dateOfBirth, int workingHours) {

        super(username, password, dateOfBirth, Role.RECEPTIONIST, workingHours);
        HotelDatabase.receptionists.add(this);
    }

    public void viewReservations(int reservationId) {
        boolean found =false;


        for (Reservation r : HotelDatabase.reservations) {

            if (r.getID() == reservationId) {
                found =true;

                System.out.println("\nReservation ID: " + r.getID());
                System.out.println("Guest username: " + r.getGuest().getUsername());
                System.out.println("Room Number: " + r.getRoom().getRoomNumber());
                System.out.println("Checkin: " + r.getCheckin());
                System.out.println("Checkout: " + r.getCheckout());
                System.out.println("Status: " + r.getStatus());
                System.out.println("Duration : " + r.getDuration() + "\n");

                return;
            }


        }
        if (!found) System.out.println("Reservation not found");
    }




    /*public void completeReservation(int reservationId) {

        for (Reservation r : HotelDatabase.reservations) {

            if (r.getID() == reservationId) {

                if (r.getStatus() == ReservationStatus.CANCELLED) {
                    System.out.println("Cannot complete a cancelled reservation");
                    return;
                }

                if (r.getStatus() != ReservationStatus.CONFIRMED) {
                    System.out.println("Reservation must be CONFIRMED first");
                    return;
                }

                r.complete();
                System.out.println("Reservation COMPLETED");
                return;
            }
        }

        System.out.println("Reservation not found");
    }*/




    public void checkIn(int roomnumber ){
        for (Reservation r : HotelDatabase.reservations) {

            if (r.getRoom().getRoomNumber() == roomnumber) {

                if (r.getStatus() != ReservationStatus.CONFIRMED) {
                    System.out.println(" Reservation not confirmed yet");
                    return;
                }

                r.getRoom().setAvailable(false);
                for (int i=0; i<r.getRequired_amenities().size(); i++){
                    r.getRoom().addAmenity(r.getRequired_amenities().get(i));
                }

                System.out.println("Guest checked in");
                return;
            }
        }

        System.out.println("Reservation not found");
    }


    public void checkoutGuest(int roomNumber) {

        for (Reservation r : HotelDatabase.reservations) {

            if (r.getRoom().getRoomNumber() == roomNumber) {

                // Already checked out
                if (r.getStatus() == ReservationStatus.COMPLETED) {
                    System.out.println("Guest already checked out.");
                    return;
                }

                // Make sure reservation is active
                if (r.getStatus() == ReservationStatus.CANCELLED) {
                    System.out.println("Reservation is cancelled.");
                    return;
                }

                // Check payment
                if (r.getInvoice() == null || !r.getInvoice().getPaid()) {
                    System.out.println("Payment not completed yet.");
                    return;
                }

                // Finalize checkout
                r.setStatus(ReservationStatus.COMPLETED);
                r.getRoom().setAvailable(true);
                r.getRoom().getAmenities().clear();

                System.out.println("Checkout confirmed. Room is now available.");

                return;
            }
        }

        System.out.println("No reservation found for this room.");
    }



    }
 



