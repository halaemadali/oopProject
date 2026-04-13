
package com.mycompany.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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
}

   
    public Guest(String username, String password, LocalDate dateOfBirth,
                 double balance, String address, Gender gender,
                 List<RoomType> roomPreferences)throws InvalidUsernameException  {

         setUsername(username);
        setPassword(password);//set for validation
        setDateOfBirth(dateOfBirth);
        setBalance(balance);
        this.address = address;
        this.gender = gender;
        this.roomPreferences = roomPreferences != null ? roomPreferences : new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.availableRooms = new ArrayList<>();
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

    public void setPassword(String password) {

    if (password == null) {
        System.out.println("Password is null");
    } 
    else if (password.length() < 6) {
        System.out.println("Password must be at least 6 characters");
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

 //methodes

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
    if (roomPreferences.contains(room.gettype()) && room.getisavailable()) {
        
        availableRooms.add(room);
    }
}

    return availableRooms;
       
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
        System.out.println("Room: " + r.getRoom().getroomnumber());
    }
}

//checkout
public void checkout() {

    for (Reservation r : reservations) {
        r.complete();
    }
}
    
  }
  


  



    


    
  
  


 

