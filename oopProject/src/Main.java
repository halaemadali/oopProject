
//package com.mycompany.main;




import java.util.Scanner;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        RoomType t1 =new RoomType("single",1,200);
        System.out.println(  t1.getNO3());
        Amenity wifi = new Amenity("WiFi");
        Room r1=new Room();
        r1.addAmenity(wifi);
        System.out.println( r1.getAmenities().get(0));
        r1.removeAmenity(wifi);


        RoomType RT=new RoomType();

        Room r=new Room();
        r.settype(RT);
        r.gettype().setBasePrice(2000.650);
        r.gettype().setCapacity(2);
        r.gettype().setNO3("single");

        Amenity TV=new Amenity("TV");
        Amenity bar=new Amenity("bar");
        r.setroomnumber(102);
        r.addAmenity(wifi);
        r.addAmenity(TV);
        r.setisavailable(true);
        r.checkavailability(r.getisavailable());
        r.getAmenities();
        r.getroomnumber();
        r.getisavailable();
        r.removeAmenity(wifi);
        System.out.println (r.gettype().getBasePrice());
        System.out.println (r.gettype().getCapacity());
        System.out.println (r.gettype().getNO3());




    

   // Guest
    Scanner input = new Scanner(System.in);

Guest g1 = new Guest();

// username
String username;
while (true) {
    System.out.println("Enter username:");
    username = input.nextLine();

    try {
        g1.setUsername(username);
        break; // لو صح → اخرج من اللوب
    } catch (InvalidUsernameException e) {
        System.out.println(e.getMessage());
    }
}

String password;
while (true) {
    System.out.println("Enter password:");
    password = input.nextLine();

    try {
        g1.setPassword(password);
        break; // valid → اخرج من اللوب
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
}

    g1.setBalance(1000);

    g1.register();
    
    
    
   
    
    

    // Login
    Guest loggedIn = Guest.login("mariam", "123456");

    // View Rooms
    

r1.setroomnumber(101);
r1.settype(t1);
r1.setisavailable(true);
    
    
    
    HotelDatabase.rooms.add(r1);
    List<Room> available = g1.viewAvailableRooms();
    for (Room room : available) {
    System.out.println("Available Room: " + room.getroomnumber());
}

        
        
        
        
        // create reservation
        Reservation res = null;
       try {
            res = new Reservation(
                    1,
                    r,
                    g1,
                    new Date(),
                    new Date(System.currentTimeMillis() + 86400000),
                    ReservationStatus.PENDING
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    res.confirm();
    // add reservation
    g1.makeReservation(res);
   g1.cancelReservation(res);
    g1.viewReservations();
    g1.checkout();
    
        
        

    }
    }


