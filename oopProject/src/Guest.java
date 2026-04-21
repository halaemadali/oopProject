

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class Guest {

    // Variables
    private String username;
    private String password;
    private LocalDate dateOfBirth;
    private double balance;
    private String address;
    private Gender gender;

    private List<RoomType> roomPreferences;

    private List<Room> availableRooms;
    private List<Reservation> reservations ;


    //Constructor
    public Guest() {
        this.availableRooms = new ArrayList<>();
        this.reservations = new ArrayList<>();
        HotelDatabase.guests.add(this);
    }


    public Guest(String username, String password, LocalDate dateOfBirth,
                 double balance, String address, Gender gender,
                 List<RoomType> roomPreferences)throws InvalidUsernameException , Exception  {

        setUsername(username);
        setPassword(password);//set for validation
        setDateOfBirth(dateOfBirth);
        setBalance(balance);
        this.address = address;
        this.gender = gender;
        this.roomPreferences = roomPreferences != null ? roomPreferences : new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.availableRooms = new ArrayList<>();
        HotelDatabase.guests.add(this);
    }




    // Getters & Setters

    public String getUsername() {
        return username;
    }




    public void setUsername(String username) throws InvalidUsernameException {

        if (username == null) {
            throw new InvalidUsernameException("Username cannot be null");
        }
        else if (username.trim().isEmpty()) {
            throw new InvalidUsernameException("Username cannot be empty");
        }
        else {
            this.username = username;
        }
    }




    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws Exception {
        if (password == null) {
            throw new Exception("Password cannot be null");
        }
        else if (password.length() < 6) {
            throw new Exception("Password must be at least 6 characters");
        }
        else {
            this.password = password;
        }
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {

        if (dateOfBirth == null) {
            System.out.println("Date Of Birth is null");
        }
        LocalDate today = LocalDate.now();
        if (dateOfBirth.isAfter(today)){
            throw new IllegalArgumentException("Invalid Birthday.");
        }
        else{ this.dateOfBirth = dateOfBirth;}
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        if (balance < 0) {
            System.out.println("Balance cannot be negative");

        }
        else{  this.balance = balance;}
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    //methods

    //register

    public void register(){

        for (Guest g : HotelDatabase.guests) {

            if(g.getUsername().equals(this.username)){
                System.out.println("Username already exists!");
                return;
            }
        }
        HotelDatabase.guests.add(this);
        System.out.println("Registered successfully!");
    }

    //login

    public static Guest login(String username, String password){
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



    //View Available Rooms
    public List<Room> viewAvailableRooms(){
        availableRooms.clear();
        for (Room room : HotelDatabase.rooms) {
            if ((roomPreferences == null || roomPreferences.contains(room.getType()))
                    && room.isAvailable()) {

                availableRooms.add(room);
            }
        }

        return availableRooms;

    }
    //chooseAmenities
    public void chooseAmenities(Room room, Scanner input) {

        System.out.println("Available amenities:");

        for (int i = 0; i < HotelDatabase.amenities.size(); i++) {
            System.out.println(i + ": " + HotelDatabase.amenities.get(i));
        }

        System.out.println("Enter amenity index or type 'done' to finish:");

        while (true) {

            String inputValue = input.next();

            if (inputValue.equalsIgnoreCase("done")) {
                break;
            }

            try {
                int choice = Integer.parseInt(inputValue);

                if (choice >= 0 && choice < HotelDatabase.amenities.size()) {

                    Amenity a = HotelDatabase.amenities.get(choice);

                    try {
                        room.addAmenity(a);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                } else {
                    System.out.println("Invalid index!");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number or 'done'");
            }
        }
    }

    //make Reservation
    public void makeReservation(Scanner input) {

        try {

            Room selectedRoom = null;

            while (true) {

                System.out.println("Enter your preferred room type (Single/Double/Triple) or 'exit' to stop:");
                String pref = input.next();

                if (pref.equalsIgnoreCase("exit")) {
                    System.out.println("Reservation cancelled.");
                    return;
                }


                List<Room> foundRooms = new ArrayList<>();

                for (Room room : HotelDatabase.rooms) {

                    if (room.isAvailable() &&
                            room.getType().getCategory().equalsIgnoreCase(pref)) {
                        foundRooms.add(room);
                    }
                }


                if (!foundRooms.isEmpty()) {

                    System.out.println("Available rooms:");

                    for (int i = 0; i < foundRooms.size(); i++) {
                        Room r = foundRooms.get(i);

                        System.out.println(i + ": Room " + r.getRoomNumber()
                                + " | Price: " + r.getPrice());
                    }

                    System.out.println("Choose room index:");
                    int choice = input.nextInt();

                    if (choice < 0 || choice >= foundRooms.size()) {
                        System.out.println("Invalid choice!");
                        continue;
                    }

                    selectedRoom = foundRooms.get(choice);
                    break;
                }

                else {
                    System.out.println("No rooms available for this preference. Try another.");
                }
            }


            chooseAmenities(selectedRoom, input);


            System.out.println("Enter check-in day:");
            int inDay = input.nextInt();

            System.out.println("Enter check-out day:");
            int outDay = input.nextInt();

            Date checkin = new Date(2026 - 1900, 5, inDay);
            Date checkout = new Date(2026 - 1900, 5, outDay);


            Reservation res = new Reservation(selectedRoom, this, checkin, checkout);

            reservations.add(res);
            HotelDatabase.reservations.add(res);

            res.confirm();

            System.out.println("Reservation successful!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }





    //cancle reservation
    public void cancelReservation(Reservation r) {

        if (r == null) {
            System.out.println("Invalid reservation");
            return;
        }

        r.cancel();
    }

    //view reservation
    public void viewReservations() {

        for (Reservation r : reservations) {
            System.out.println("Room: " + r.getRoom().getRoomNumber());
        }
    }

    //checkout
    public void checkOut(int roomnumber, PaymentMethod method) throws InvalidPaymentException {

        for (Reservation r : HotelDatabase.reservations) {

            if (r.getRoom().getRoomNumber() == roomnumber) {

                if (r.getStatus() == ReservationStatus.COMPLETED) {
                    System.out.println("Already Checked out.");
                    return;
                }

                if (r.getInvoice() == null) {
                    throw new IllegalStateException("No invoice found.");
                }

                double total = r.getInvoice().calculateTotal();
                System.out.println("Guest must pay " + total);

                if (r.getGuest().getBalance() < total) {
                    throw new InvalidPaymentException("Guest Balance is insufficient.");
                }


                r.getGuest().setBalance(r.getGuest().getBalance() - total);


                r.getInvoice().pay(method);

                System.out.println("Payment Successful. Awaiting Receptionist Approval");

                return;
            }
        }
    }

    @Override
    public String toString(){
        return "Guest {" +
                "username: " + username +
                ", dateOfBirth: " + dateOfBirth +
                ", balance: " + balance +
                ", address: " + address +
                ", gender: " + gender +
                "Reservations: " + reservations.size() +
                '}';


    }


}











