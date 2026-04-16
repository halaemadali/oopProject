
import java.time.LocalDate;

public abstract class Staff {

    private String username;
    private String password;
    private LocalDate dateOfBirth;
    private Role role;  //ADMIN or RECEPTIONIST
    private int workingHours;


    //Constructor
    public Staff(String username, String password, LocalDate dateOfBirth, Role role, int workingHours) {

        setUsername(username);
        setPassword(password);
        setDateOfBirth(dateOfBirth);
        this.role = role;
        setWorkingHours(workingHours);

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

        this.dateOfBirth = dateOfBirth;
    }



    public void setWorkingHours(int workingHours){
        if (workingHours < 0) {
            throw new IllegalArgumentException("INVALID WORKING HOURS");

        }

        this.workingHours = workingHours;
    }



 public void viewAllGuests(){

     if (HotelDatabase.guests.isEmpty()) {
         System.out.println("No guests found");
         return;
     }
     System.out.println("============== View All Guests ============");
        for(Guest g : HotelDatabase.guests) {

            System.out.println("Username: " + g.getUsername());
            System.out.println("Date of Birth: " + g.getDateOfBirth() );
            System.out.println("Balance: " + g.getBalance());

            System.out.println("---------------------------------------");

        }
    }

    public void viewAllRooms() {

        System.out.println("============== View All Rooms ============");
        for (Room r : HotelDatabase.rooms) {

            System.out.println("Room Number: " + r.getRoomNumber());
            System.out.println("Price: " + r.getPrice());
            System.out.println("Available: " + r.isAvailable());

            // RoomType details
            System.out.println("Room Type Id: "+ r.getType().getRoomTypeId());
            System.out.println("\t\tRoom Category: " + r.getType().getRoomCategory());
            System.out.println("\t\tCapacity: " + r.getType().getCapacity());
            System.out.println("\t\tBase Price: " + r.getType().getBasePrice());

            System.out.println("Amenities:");
            if (r.getAmenities() != null && !r.getAmenities().isEmpty()) {
                for (Amenity a : r.getAmenities()) {
                    System.out.println("- " +a.getQuantity()+" "+ a.getName() + "  Price: " + a.getTotalPrice());
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

            System.out.println("=========== View All Reservations ===========");

            for (Reservation r : HotelDatabase.reservations) {

                System.out.println("Reservation ID: " + r.getID());
                System.out.println("Guest username: " +r.getGuest().getUsername() );
                System.out.println("Room Number: " + r.getRoom().getRoomNumber());
                System.out.println("Checkin: " + r.getCheckin());
                System.out.println("Checkout: " + r.getCheckout());
                System.out.println("Status: " + r.getStatus());
                System.out.println("Duration : " + r.getDuration());

                System.out.println("-------------------------------------------");


            }
        }



        //view reservation
    public void viewReservation(int reservationId) {


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



}
