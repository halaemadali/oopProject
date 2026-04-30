package com.hotel.database;

import com.hotel.enums.Gender;
import com.hotel.enums.ReservationStatus;
import com.hotel.enums.View;
import com.hotel.models.*;
import java.util.ArrayList;
import java.util.Random;
import java.time.LocalDate;
import com.hotel.enums.*;
import com.hotel.exceptions.*;
import com.hotel.interfaces.*;

public class HotelDatabase {

  public static ArrayList<Guest> guests = new ArrayList<>();
  public static ArrayList<Room> rooms = new ArrayList<>();
  public static ArrayList<Reservation> reservations = new ArrayList<>();
  public static ArrayList<RoomType> roomTypes = new ArrayList<>();
  public static ArrayList<Amenity> amenities = new ArrayList<>();
  public static ArrayList<Admin> admins = new ArrayList<>();
  public static ArrayList<Receptionist> receptionists = new ArrayList<>();
  public static ArrayList<Invoice> invoices = new ArrayList<>();

  static Random rand = new Random();

  public static void initializeData() {
    createRoomTypes();
    createAmenities();
    createRooms();
    createGuests();
    createStaff();
    createReservations();
  }

  private static void createRoomTypes() {
    // RoomType constructor adds itself to HotelDatabase.roomTypes automatically
    RoomType single = new RoomType("Single", 1, 500);
    HotelDatabase.roomTypes.add(single);
    RoomType duplex= new RoomType("Double", 2, 800);
    HotelDatabase.roomTypes.add(duplex);
    RoomType tri = new RoomType("Triple", 3, 1100);
    HotelDatabase.roomTypes.add(tri);
    RoomType suite = new RoomType("Suite", 4, 2000);
    HotelDatabase.roomTypes.add(suite);
  }

  private static void createAmenities() {
    Amenity a1= new Amenity("WiFi", 50);
    HotelDatabase.amenities.add(a1);
    Amenity a2= new Amenity("TV", 30);
    HotelDatabase.amenities.add(a2);
    Amenity a3= new Amenity("Mini Bar", 100);
    HotelDatabase.amenities.add(a3);
    Amenity a4= new Amenity("Air Conditioning", 80);
    HotelDatabase.amenities.add(a4);
    Amenity a5= new Amenity("Room Service", 120);
    HotelDatabase.amenities.add(a5);
  }

  private static void createRooms() {
    // Room constructor does NOT add itself — must add manually
    View[] views = View.values();
    for (int i = 0; i < 130; i++) {
      int roomNumber = 100 + i;
      RoomType type = HotelDatabase.roomTypes.get(rand.nextInt(HotelDatabase.roomTypes.size()));
      boolean available = rand.nextBoolean();
      View view = views[rand.nextInt(views.length)];
      int floor = rand.nextInt(6);
      Room room = new Room(roomNumber, type, available, view, floor);
      HotelDatabase.rooms.add(room);
    }
  }

  private static void createGuests() {
    String[] firstNames = {
            "Hana", "Mohamed", "Sara", "Ali", "Fatma", "Youssef", "Nour",
            "Omar", "Layla", "Hassan", "Salma", "Karim", "Rana", "Tarek",
            "Reem", "Khaled", "Lina", "Ibrahim", "Jana", "Farah",
            "Mostafa", "Yasmin", "Amr", "Dina", "Mahmoud", "Hala", "Tamer", "Mariam", "Ahmed"
    };

    String[] lastNames = {
            "Hassan", "Ali", "Mahmoud", "Ibrahim", "Khalil", "Youssef",
            "Mostafa", "Fathy", "Nasser", "Saad", "Adel", "Hamdy",
            "Zaki", "Farouk", "Tawfik", "Rashad", "Shawky", "Aziz"
    };

    boolean[] isFemale = {
            true, false, true, false, true, false, true,
            false, true, false, true, false, true, false,
            true, false, true, false, true, false,
            false, true, false, true, false, true, false, true, false
    };

    for (int i = 0; i < 29; i++) {
      try {
        int index = i % firstNames.length;

        String first = firstNames[index];
        String last = lastNames[rand.nextInt(lastNames.length)];
        String username = (first + "_" + last + "_" + i).toLowerCase();
        String password = "pass0" + i;

        LocalDate dob = LocalDate.of(
                1970 + rand.nextInt(30),
                1 + rand.nextInt(12),
                1 + rand.nextInt(28)
        );

        double balance = 1000 + rand.nextInt(5000);
        String address = "Cairo";
        Gender gender = isFemale[index] ? Gender.FEMALE : Gender.MALE;

        Guest g = new Guest(username, password, dob, balance, address, gender);
        HotelDatabase.guests.add(g);

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private static void createStaff() {
    try {
      Admin a1 = new Admin("admin1", "admin123", LocalDate.of(1980, 5, 10), 8);
      Admin a2 = new Admin("admin2", "admin456", LocalDate.of(1978, 3, 15), 8);
      HotelDatabase.admins.add(a1);
      HotelDatabase.admins.add(a2);
    } catch (Exception e) {
      e.printStackTrace();
    }

    try {
      Receptionist r1= new Receptionist("receptionist1", "recep123", LocalDate.of(1995, 6, 20), 8);
      Receptionist r2= new Receptionist("receptionist2", "recep456", LocalDate.of(1998, 9, 25), 8);
      HotelDatabase.receptionists.add(r1);
      HotelDatabase.receptionists.add(r2);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void createReservations() {
    for (int i = 0; i < 40; i++) {
      try {
        Guest g = HotelDatabase.guests.get(rand.nextInt(HotelDatabase.guests.size()));
        Room r = HotelDatabase.rooms.get(rand.nextInt(HotelDatabase.rooms.size()));

        LocalDate in = LocalDate.now().plusDays(1 + rand.nextInt(10));
        LocalDate out = in.plusDays(1 + rand.nextInt(5));

        // Only create reservation if room is actually free
        if (!r.checkAvailabilityPeriod(in, out)) continue; // ← add this line

        Reservation res = new Reservation(r, g, in, out);
        g.getReservations().add(res);

        ReservationStatus[] statuses = {
                ReservationStatus.PENDING,
                ReservationStatus.CONFIRMED,
                ReservationStatus.CANCELLED
        };
        res.setStatus(statuses[rand.nextInt(statuses.length)]);

      } catch (Exception e) {
        // skip this one silently
      }
    }
  }
}
