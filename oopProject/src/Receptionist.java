import java.time.LocalDate;

public class Receptionist extends Staff {

    public Receptionist(String username, String password, LocalDate dateOfBirth, int workingHours) {

        super(username, password, dateOfBirth, Role.RECEPTIONIST, workingHours);
    }

    public void viewReservations(int reservationId) {


        for (Reservation r : HotelDatabase.reservations) {

            if (r.getID() == reservationId) {

                System.out.println("\nReservation ID: " + r.getID());
                System.out.println("Guest username: " + r.getGuest().getUsername());
                System.out.println("Room Number: " + r.getRoom().getRoomNumber());
                System.out.println("Checkin: " + r.getCheckin());
                System.out.println("Checkout: " + r.getCheckout());
                System.out.println("Status: " + r.getStatus());
                System.out.println("Duration : " + r.getDuration() + "\n");

                return;
            }

            System.out.println("Reservation not found");
        }
    }


        public void confirmReservation(int reservationId){

            for (Reservation r : HotelDatabase.reservations) {

                if (r.getID() == reservationId) {

                    if (r.getStatus() != ReservationStatus.PENDING) {
                        System.out.println("Only PENDING reservations can be confirmed");
                        return;
                    } else if (r.getStatus() == ReservationStatus.CONFIRMED) {
                        System.out.println("Already confirmed");
                        return;
                    }


                    r.confirm();
                    System.out.println("Reservation CONFIRMED");
                    return;
                }
            }
            System.out.println("Reservation not found");
        }




    public void cancelReservation(int reservationId) {

        for (Reservation r : HotelDatabase.reservations) {

            if (r.getID() == reservationId) {

                if (r.getStatus() == ReservationStatus.COMPLETED) {
                    System.out.println("Cannot cancel completed reservation");
                    return;
                }

                r.cancel();
                System.out.println("Reservation CANCELLED");
                return;
            }
        }
        System.out.println("Reservation not found");
    }



    public void completeReservation(int reservationId) {

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
    }




    public void checkIn(int reservationID ){
        for (Reservation r : HotelDatabase.reservations) {

            if (r.getID() == reservationID) {

                if (r.getStatus() != ReservationStatus.CONFIRMED) {
                    System.out.println(" Reservation not confirmed yet");
                    return;
                }

                r.getRoom().setAvailable(false);

                System.out.println("Guest checked in");
                return;
            }
        }

        System.out.println("Reservation not found");
    }


    public void checkOut(int reservationID){
        for (Reservation r : HotelDatabase.reservations) {

            if (r.getID() == reservationID) {

                if (r.getStatus() != ReservationStatus.COMPLETED) {
                    System.out.println("Reservation not completed yet");
                    return;
                }

                r.getRoom().setAvailable(true);

                System.out.println("Guest checked out");
                return;
            }
        }

        System.out.println("Reservation not found");
    }










    }
 



