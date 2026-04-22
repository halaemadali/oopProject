
import java.time.LocalDate;

public abstract class Staff implements Manageable{

    protected String username;
    protected String password;
    protected LocalDate dateOfBirth;
    protected Role role;  //ADMIN or RECEPTIONIST
    protected int workingHours;


    //Constructor
    public Staff(String username, String password, LocalDate dateOfBirth, Role role, int workingHours) {

        setUsername(username);
        setPassword(password);
        setDateOfBirth(dateOfBirth);
        this.role = role;
        setWorkingHours(workingHours);

    }

    public Staff() {

    }


    //Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Role getRole() {
        return role;
    }

    public int getWorkingHours() {
        return workingHours;
    }


    //Setters

    public void setUsername(String username) {

        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        this.username = username;
    }


    public void setPassword(String password) {

        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        if (password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }

        if (password.contains(" ")) {
            throw new IllegalArgumentException("Password cannot contain spaces");
        }

        this.password = password;
    }


    public void setDateOfBirth(LocalDate dateOfBirth) {

        if (dateOfBirth == null) {
            throw new IllegalArgumentException("Invalid date of birth");
        }
        LocalDate today = LocalDate.now();
        if (dateOfBirth.isAfter(today)) {
            throw new IllegalArgumentException("Invalid Birthday.");
        }

        this.dateOfBirth = dateOfBirth;
    }


    public void setWorkingHours(int workingHours) {
        if (workingHours < 0) {
            throw new IllegalArgumentException("INVALID WORKING HOURS");

        }

        this.workingHours = workingHours;
    }


    //view all guests
    public void viewAllGuests() {
        if (HotelDatabase.guests.isEmpty()) {
            System.out.println("No guests found");
            return;
        }
        for (Guest g : HotelDatabase.guests) {
            System.out.println(g);
        }
    }


    //view all rooms
    public void viewAllRooms() {
        for (Room r : HotelDatabase.rooms) {
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

            System.out.println("---------------------------------------");
        }
    }


    // Prints all reservations in the system
    public void viewAllReservations() {

        if (HotelDatabase.reservations.isEmpty()) {
            System.out.println("No reservations found");
            return;
        }

        for (Reservation r : HotelDatabase.reservations) {
            System.out.println(r);
        }
    }


    //view reservation
    public void viewReservation(int reservationId) {


        for (Reservation r : HotelDatabase.reservations) {

            if (r.getID() == reservationId) {

                System.out.println(r);

                return;
            }
        }
        System.out.println("Reservation not found");

    }


    public void viewAllPendingReservations() {
        boolean pending = false;
        if (HotelDatabase.reservations.isEmpty()) {
            System.out.println("No reservations found");
            return;
        }

        System.out.println("=========== View Pending Reservations ===========");

        for (Reservation r : HotelDatabase.reservations) {
            if (r.getStatus() == ReservationStatus.PENDING) {
                pending = true;
                System.out.print(r.toString());
                System.out.println("");
            }
        }
        if (!pending) {
            System.out.println("No PENDING reservations.");
        }
    }


    public void confirmReservation(int reservationId) {
        for (Reservation r : HotelDatabase.reservations) {
            if (r.getID() == reservationId) {
                if (r.getStatus() == ReservationStatus.CONFIRMED) {
                    System.out.println("Already confirmed");
                    return;
                }
                if (r.getStatus() != ReservationStatus.PENDING) {
                    System.out.println("Only PENDING reservations can be confirmed");
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


    @Override
    public String toString() {
        return "Staff: " + username +
                ", Role: " + role +
                ", Date of Birth: " + dateOfBirth +
                ", Working Hours: " + workingHours;
    }

}
