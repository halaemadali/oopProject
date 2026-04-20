

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
                    && room.getisavailable()) {

                availableRooms.add(room);
            }
        }

        return availableRooms;

    }

    public void chooseAmenities(Room room, Scanner input) {

        System.out.println("Available amenities:");

        for (int i = 0; i < HotelDatabase.amenities.size(); i++) {
            System.out.println(i + ": " + HotelDatabase.amenities.get(i));
        }

        System.out.println("How many amenities do you want to add?");
        int count = input.nextInt();

        for (int i = 0; i < count; i++) {

            System.out.println("Enter index:");
            int choice = input.nextInt();


            if (choice >= 0 && choice < HotelDatabase.amenities.size()) {
                Amenity a = HotelDatabase.amenities.get(choice);
                room.addAmenity(a);
            } else {
                System.out.println("Invalid index!");
                i--;
            }
        }
    }

    //make Reservation
    public void makeReservation(Reservation r){

        if (r==null){
            System.out.println("Invalid reservation");
            return;
        }
        reservations.add(r);//list of the guest
        HotelDatabase.reservations.add(r);//global
        System.out.println("Reservation added successfully!");

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

}












