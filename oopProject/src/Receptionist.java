
import java.time.LocalDate;

public class Receptionist extends Staff {
  
    public Receptionist(String username, String password , LocalDate dateOfBirth , int workingHours){
        super(username, password, dateOfBirth,Role.RECEPTIONIST,workingHours);
    }


     public void checkIn(int reservationID ){
        for (Reservation r : HotelDatabase.reservations) {

            if (r.getID() == reservationID) {

                if (r.getStatus() == ReservationStatus.CONFIRMED) {
                    System.out.println("Already checked in");
                    return;
                }

                r.confirm();

                System.out.println("Guest checked in");
                return;
            }
        }

        System.out.println("Reservation not found");
    }

  

    public void checkOut(int reservationID){
        for (Reservation r : HotelDatabase.reservations) {

            if (r.getID() == reservationID) {

                if (r.getStatus() == ReservationStatus.COMPLETED) {
                    System.out.println("Already checked out");
                    return;
                }

                r.complete();

                System.out.println("Guest checked out");
                return;
            }
        }

        System.out.println("Reservation not found");
    }




}
