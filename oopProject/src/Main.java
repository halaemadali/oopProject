import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

   
    public static void main(String[] args) {
        HotelDatabase.initializeData();
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║  Welcome to Hotel Management System  ║");
        System.out.println("╚══════════════════════════════════════╝");

        while (true) {
            System.out.println("\n********** MAIN MENU **********");
            System.out.println("1. com.hotel.models.Guest");
            System.out.println("2. com.hotel.models.Receptionist Login");
            System.out.println("3. com.hotel.models.Admin Login");
            System.out.println("0. Exit");
            System.out.print("Select: ");

            int choice = readInt();
            switch (choice) {
                case 1 -> guestEntryMenu();
                case 2 -> receptionistLoginMenu();
                case 3 -> adminLoginMenu();
                case 0 -> { System.out.println("Thank you for using our application"); return; }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }


    static LocalDate readDateOfBirth() {
        while (true) {
            System.out.print("Date of Birth (yyyy-MM-dd): ");
            String input = sc.nextLine().trim();

            try {
                LocalDate dob = LocalDate.parse(input, DATE_FMT);

                if (dob.isAfter(LocalDate.now())) {
                    System.out.println(" Date of birth cannot be in the future. Try again.");
                    continue;
                }

                return dob;

            } catch (DateTimeParseException e) {
                System.out.println("Invalid format. Please use yyyy-MM-dd");
            }
        }
    }

    //com.hotel.models.Guest Registration and Login
    static void guestEntryMenu() {
        System.out.println("\n--- com.hotel.models.Guest Entry ---");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("0. Back");
        System.out.print("Select: ");

        switch (readInt()) {
            case 1 -> guestRegister();
            case 2 -> {
                Guest g = guestLogin();
                if (g != null) guestMenu(g);
            }
            case 0 -> { }
            default -> System.out.println("Invalid option.");
        }
    }

    static void guestRegister() {
        System.out.println("\n--- com.hotel.models.Guest Registration ---");

        try {
            System.out.print("Username: ");
            String username = sc.nextLine().trim();

            System.out.print("Password (min 6 chars): ");
            String password = sc.nextLine().trim();


            while (true) {
                try {
                    Guest temp = new Guest();
                    temp.setPassword(password);
                    break; // valid password
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.print("Enter password again: ");
                    password = sc.nextLine().trim();
                }
            }

            LocalDate dob = readDateOfBirth();

            System.out.print("Balance: ");
            double balance = readDouble();
            if (balance < 0) {
                System.out.println("Balance cannot be negative.");
                return;
            }

            System.out.print("Address: ");
            String address = sc.nextLine().trim();

            System.out.print("com.hotel.enums.Gender (MALE/FEMALE): ");
            Gender gender = parseGender(sc.nextLine().trim());
            if (gender == null) return;

            Guest g = new Guest(username, password, dob, balance, address, gender);
            g.register();

        } catch (InvalidUsernameException e) {
            System.out.println("Username error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static Guest guestLogin() {
        System.out.println("\n--- com.hotel.models.Guest Login ---");
        System.out.print("Username: ");
        String u = sc.nextLine().trim();
        System.out.print("Password: ");
        String p = sc.nextLine().trim();
        Guest g = Guest.login(u, p);
        return g;
    }

    // com.hotel.models.Guest Menu
    static void guestMenu(Guest guest) {
        while (true) {
            System.out.println("\n========== GUEST MENU [" + guest.getUsername() + "] ==========");
            System.out.println("1.  com.hotel.enums.View My Reservations");
            System.out.println("2.  Make a com.hotel.models.Reservation");
            System.out.println("3.  Cancel a com.hotel.models.Reservation");
            System.out.println("4.  Check Out (self-service)");
            System.out.println("5.  com.hotel.enums.View Available Amenities");
            System.out.println("0.  Logout");
            System.out.print("Select: ");

            switch (readInt()) {
                case 1 -> guest.viewReservations();
                case 2 -> guestMakeReservation(guest);
                case 3 -> guestCancelReservation(guest);
                case 4 -> guestCheckOut(guest);
                case 5 -> guest.viewAvailableAmenities();
                case 0 -> { System.out.println("Logged out."); return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    static void guestMakeReservation(Guest guest) {
        System.out.println("\n--- Make com.hotel.models.Reservation ---");
        try {
            // Show available room types
            System.out.println("Available com.hotel.models.Room Types:");
            for (RoomType rt : HotelDatabase.roomTypes)
                System.out.println("  " + rt.getroomTypeId() + ". " + rt.getCategory()
                        + " | Capacity: " + rt.getCapacity()
                        + " | Base Price: $" + rt.getBasePrice());

            System.out.print("Enter com.hotel.models.Room Type ID: ");
            int typeId = readInt();
            RoomType selectedType = null;
            for (RoomType rt : HotelDatabase.roomTypes)
                if (rt.getroomTypeId() == typeId) { selectedType = rt; break; }
            if (selectedType == null) { System.out.println("com.hotel.models.Room type not found."); return; }

            System.out.println("Available Views:");
            for (View v : View.values())
                System.out.println("  " + v.name() + " (+$" + v.getPrice() + ")");
            System.out.print("Enter com.hotel.enums.View (e.g. SEA_VIEW): ");
            View view = parseView(sc.nextLine().trim());
            if (view == null) return;

            System.out.print("Check-in date (yyyy-MM-dd): ");
            LocalDate checkin = parseDate(sc.nextLine().trim());
            if (checkin == null) return;

            System.out.print("Check-out date (yyyy-MM-dd): ");
            LocalDate checkout = parseDate(sc.nextLine().trim());
            if (checkout == null) return;

            // Show available rooms
            List<Room> availableRooms = guest.makeReservation(selectedType, view, checkin, checkout);

            System.out.print("\nEnter com.hotel.models.Room Number to book: ");
            int roomNumber = readInt();

            // Optional amenities
            guest.viewAvailableAmenities();
            System.out.println("Enter amenity indices to add (comma-separated), or press Enter to skip:");
            String amenityInput = sc.nextLine().trim();
            List<Amenity> selectedAmenities = new ArrayList<>();
            if (!amenityInput.isEmpty()) {
                List<Amenity> allAmenities = guest.getAvailableAmenities();
                for (String token : amenityInput.split(",")) {
                    try {
                        int idx = Integer.parseInt(token.trim());
                        if (idx >= 0 && idx < allAmenities.size())
                            selectedAmenities.add(allAmenities.get(idx));
                        else
                            System.out.println("Index " + idx + " out of range, skipped.");
                    } catch (NumberFormatException e) {
                        System.out.println("'" + token.trim() + "' is not a valid index, skipped.");
                    }
                }
            }

            guest.finalizeReservation(roomNumber, availableRooms, selectedAmenities, checkin, checkout);

        } catch (RoomNotAvailableException e) {
            System.out.println("No rooms available: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void guestCancelReservation(Guest guest) {
        System.out.println("\n--- Cancel com.hotel.models.Reservation ---");
        guest.viewReservations();
        System.out.print("Enter com.hotel.models.Room Number to cancel: ");
        int roomNum = readInt();
        guest.cancelReservation(roomNum);
    }

    static void guestCheckOut(Guest guest) {
        System.out.println("\n--- Self-Service Check Out ---");
        guest.viewReservations();
        System.out.print("Enter com.hotel.models.Room Number: ");
        int roomNum = readInt();
        PaymentMethod method = selectPaymentMethod();
        if (method == null) return;
        try {
            guest.checkOut(roomNum, method);
        } catch (InvalidPaymentException e) {
            System.out.println("Payment failed: " + e.getMessage());
        }
    }

    //com.hotel.models.Receptionist Login
    static void receptionistLoginMenu() {
        System.out.println("\n--- com.hotel.models.Receptionist Login ---");
        System.out.print("Username: ");
        String u = sc.nextLine().trim();
        System.out.print("Password: ");
        String p = sc.nextLine().trim();

        Receptionist found = null;
        for (Receptionist r : HotelDatabase.receptionists)
            if (r.getUsername().equals(u) && r.getPassword().equals(p)) { found = r; break; }

        if (found == null) { System.out.println("Invalid credentials."); return; }
        System.out.println("Welcome, " + found.getUsername() + "!");
        receptionistMenu(found);
    }

    static void receptionistMenu(Receptionist rec) {
        while (true) {
            System.out.println("\n========== RECEPTIONIST MENU [" + rec.getUsername() + "] ==========");
            System.out.println("1.  com.hotel.enums.View All Guests");
            System.out.println("2.  com.hotel.enums.View All Rooms");
            System.out.println("3.  com.hotel.enums.View All Reservations");
            System.out.println("4.  com.hotel.enums.View Pending Reservations");
            System.out.println("5.  Confirm com.hotel.models.Reservation");
            System.out.println("6.  Cancel com.hotel.models.Reservation");
            System.out.println("7.  Check In com.hotel.models.Guest");
            System.out.println("8.  Process com.hotel.models.Guest Checkout");
            System.out.println("9.  com.hotel.enums.View Checked-Out Reservations");
            System.out.println("10. Complete com.hotel.models.Reservation");
            System.out.println("0.  Logout");
            System.out.print("Select: ");

            switch (readInt()) {
                case 1  -> rec.viewAllGuests();
                case 2  -> rec.viewAllRooms();
                case 3  -> rec.viewAllReservations();
                case 4  -> rec.viewAllPendingReservations();
                case 5  -> {
                    System.out.print("com.hotel.models.Reservation ID: ");
                    rec.confirmReservation(readInt());
                }
                case 6  -> {
                    System.out.print("com.hotel.models.Reservation ID: ");
                    rec.cancelReservation(readInt());
                }
                case 7  -> {
                    System.out.print("com.hotel.models.Guest username: ");
                    String uname = sc.nextLine().trim();
                    System.out.print("com.hotel.models.Reservation ID: ");
                    int resId = readInt();
                    rec.checkIn(uname, resId);
                }
                case 8  -> {
                    System.out.print("com.hotel.models.Guest username: ");
                    String uname = sc.nextLine().trim();
                    System.out.print("com.hotel.models.Room Number: ");
                    int roomNum = readInt();
                    PaymentMethod method = selectPaymentMethod();
                    if (method != null) rec.processGuestCheckout(uname, roomNum, method);
                }
                case 9  -> rec.viewAllCheckedOutReservations();
                case 10 -> {
                    System.out.print("com.hotel.models.Reservation ID: ");
                    rec.completeReservation(readInt());
                }
                case 0  -> { System.out.println("Logged out."); return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    // com.hotel.models.Admin Login
    static void adminLoginMenu() {
        System.out.println("\n--- com.hotel.models.Admin Login ---");
        System.out.print("Username: ");
        String u = sc.nextLine().trim();
        System.out.print("Password: ");
        String p = sc.nextLine().trim();

        Admin found = null;
        for (Admin a : HotelDatabase.admins)
            if (a.getUsername().equals(u) && a.getPassword().equals(p)) { found = a; break; }

        if (found == null) { System.out.println("Invalid credentials."); return; }
        System.out.println("Welcome, " + found.getUsername() + "!");
        adminMenu(found);
    }

    static void adminMenu(Admin admin) {
        while (true) {
            System.out.println("\n========== ADMIN MENU [" + admin.getUsername() + "] ==========");
            System.out.println("--- Rooms ---");
            System.out.println("1.  Add com.hotel.models.Room");
            System.out.println("2.  com.hotel.enums.View com.hotel.models.Room");
            System.out.println("3.  Update com.hotel.models.Room");
            System.out.println("4.  Delete com.hotel.models.Room");
            System.out.println("5.  com.hotel.enums.View All Rooms");
            System.out.println("--- com.hotel.models.Room Types ---");
            System.out.println("6.  Add com.hotel.models.Room Type");
            System.out.println("7.  com.hotel.enums.View com.hotel.models.Room Type");
            System.out.println("8.  com.hotel.enums.View All com.hotel.models.Room Types");
            System.out.println("9.  Update com.hotel.models.Room Type");
            System.out.println("10. Delete com.hotel.models.Room Type");
            System.out.println("--- Amenities ---");
            System.out.println("11. Add com.hotel.models.Amenity");
            System.out.println("12. com.hotel.enums.View All Amenities");
            System.out.println("13. Update com.hotel.models.Amenity Price");
            System.out.println("14. Delete com.hotel.models.Amenity");
            System.out.println("15. Add com.hotel.models.Amenity to com.hotel.models.Room");
            System.out.println("16. com.hotel.enums.View Amenities in com.hotel.models.Room");
            System.out.println("17. Remove com.hotel.models.Amenity from com.hotel.models.Room");
            System.out.println("--- com.hotel.models.Staff & Guests ---");
            System.out.println("18. Add com.hotel.models.Receptionist Account");
            System.out.println("19. com.hotel.enums.View All Guests");
            System.out.println("20. com.hotel.enums.View All Reservations");
            System.out.println("0.  Logout");
            System.out.print("Select: ");

            switch (readInt()) {
                case 1  -> adminAddRoom(admin);
                case 2  -> { System.out.print("com.hotel.models.Room Number: "); admin.viewRoom(readInt()); }
                case 3  -> adminUpdateRoom(admin);
                case 4  -> adminDeleteRoom(admin);
                case 5  -> admin.viewAllRooms();
                case 6  -> adminAddRoomType(admin);
                case 7  -> { System.out.print("com.hotel.models.Room Type ID: "); admin.viewRoomType(readInt()); }
                case 8  -> admin.viewAllRoomTypes();
                case 9  -> adminUpdateRoomType(admin);
                case 10 -> { System.out.print("com.hotel.models.Room Type ID to delete: "); adminDeleteRoomType(admin, readInt()); }
                case 11 -> adminAddAmenity(admin);
                case 12 -> admin.viewAllAmenities();
                case 13 -> adminUpdateAmenity(admin);
                case 14 -> adminDeleteAmenity(admin);
                case 15 -> adminAddAmenityToRoom(admin);
                case 16 -> { System.out.print("com.hotel.models.Room Number: "); admin.viewAmenitiesInRoom(readInt()); }
                case 17 -> adminRemoveAmenityFromRoom(admin);
                case 18 -> adminAddReceptionist(admin);
                case 19 -> admin.viewAllGuests();
                case 20 -> admin.viewAllReservations();
                case 0  -> { System.out.println("Logged out."); return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    //com.hotel.models.Admin sub-actions

    static void adminAddRoom(Admin admin) {
        System.out.println("\n--- Add com.hotel.models.Room ---");
        try {
            System.out.print("com.hotel.models.Room Number: ");
            int roomNum = readInt();

            admin.viewAllRoomTypes();
            System.out.print("com.hotel.models.Room Type ID: ");
            int typeId = readInt();
            RoomType selectedType = null;
            for (RoomType rt : HotelDatabase.roomTypes)
                if (rt.getroomTypeId() == typeId) { selectedType = rt; break; }
            if (selectedType == null) { System.out.println("com.hotel.models.Room type not found."); return; }

            System.out.println("Views: " + java.util.Arrays.toString(View.values()));
            System.out.print("com.hotel.enums.View (e.g. SEA_VIEW): ");
            View view = parseView(sc.nextLine().trim());
            if (view == null) return;

            System.out.print("Floor (0-5): ");
            int floor = readInt();

            System.out.print("Available (true/false): ");
            boolean avail = Boolean.parseBoolean(sc.nextLine().trim());

            admin.addRoom(roomNum, selectedType, avail, view, floor);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void adminUpdateRoom(Admin admin) {
        System.out.println("\n--- Update com.hotel.models.Room ---");
        System.out.print("com.hotel.models.Room Number to update: ");
        int roomNum = readInt();

        System.out.println("What to update?");
        System.out.println("1. com.hotel.models.Room Number");
        System.out.println("2. com.hotel.models.Room Type");
        System.out.println("3. Availability");
        System.out.println("4. Price");
        System.out.print("Select: ");

        switch (readInt()) {
            case 1 -> {
                System.out.print("New com.hotel.models.Room Number: ");
                admin.updateRoomNumber(roomNum, readInt());
            }
            case 2 -> {
                admin.viewAllRoomTypes();
                System.out.print("New com.hotel.models.Room Type ID: ");
                int tid = readInt();
                RoomType t = null;
                for (RoomType rt : HotelDatabase.roomTypes)
                    if (rt.getroomTypeId() == tid) { t = rt; break; }
                if (t == null) { System.out.println("Type not found."); return; }
                admin.updateRoom(roomNum, t);
            }
            case 3 -> {
                System.out.print("Available (true/false): ");
                admin.updateRoom(roomNum, Boolean.parseBoolean(sc.nextLine().trim()));
            }
            case 4 -> {
                System.out.print("New Price: ");
                double price = readDouble();
                admin.updateRoom(roomNum, null, null, null, price);
            }
            default -> System.out.println("Invalid option.");
        }
    }

    static void adminDeleteRoom(Admin admin) {
        System.out.println("\n--- Delete com.hotel.models.Room ---");
        System.out.print("com.hotel.models.Room Number: ");
        int rn = readInt();
        try {
            admin.deleteRoom(rn);
        } catch (IllegalStateException e) {
            System.out.println("Cannot delete: " + e.getMessage());
        }
    }

    static void adminAddRoomType(Admin admin) {
        System.out.println("\n--- Add com.hotel.models.Room Type ---");
        try {
            System.out.print("Category name: ");
            String cat = sc.nextLine().trim();
            System.out.print("Capacity: ");
            int cap = readInt();
            System.out.print("Base Price: ");
            double price = readDouble();
            admin.addRoomType(cat, cap, price);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void adminUpdateRoomType(Admin admin) {
        System.out.println("\n--- Update com.hotel.models.Room Type ---");
        admin.viewAllRoomTypes();
        System.out.print("com.hotel.models.Room Type ID to update: ");
        int rtId = readInt();

        System.out.println("What to update?");
        System.out.println("1. Capacity");
        System.out.println("2. Base Price");
        System.out.print("Select: ");

        switch (readInt()) {
            case 1 -> {
                System.out.print("New Capacity: ");
                admin.updateRoomType(rtId, readInt());
            }
            case 2 -> {
                System.out.print("New Base Price: ");
                admin.updateRoomType(rtId, readDouble());
            }
            default -> System.out.println("Invalid option.");
        }
    }

    static void adminDeleteRoomType(Admin admin, int rtId) {
        try {
            admin.deleteRoomType(rtId);
        } catch (IllegalStateException e) {
            System.out.println("Cannot delete: " + e.getMessage());
        }
    }

    static void adminAddAmenity(Admin admin) {
        System.out.println("\n--- Add com.hotel.models.Amenity ---");
        System.out.print("com.hotel.models.Amenity name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) { System.out.println("Name cannot be empty."); return; }
        System.out.print("Price: ");
        double price = readDouble();
        try {
            admin.addAmenity(name, price);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void adminUpdateAmenity(Admin admin) {
        System.out.println("\n--- Update com.hotel.models.Amenity Price ---");
        admin.viewAllAmenities();
        System.out.print("com.hotel.models.Amenity name: ");
        String name = sc.nextLine().trim();
        System.out.print("New price: ");
        double price = readDouble();
        admin.updateAmenity(name, price);
    }

    static void adminDeleteAmenity(Admin admin) {
        System.out.println("\n--- Delete com.hotel.models.Amenity ---");
        admin.viewAllAmenities();
        System.out.print("com.hotel.models.Amenity name: ");
        String name = sc.nextLine().trim();
        try {
            admin.deleteAmenity(name);
        } catch (IllegalStateException e) {
            System.out.println("Cannot delete: " + e.getMessage());
        }
    }

    static void adminAddAmenityToRoom(Admin admin) {
        System.out.println("\n--- Add com.hotel.models.Amenity to com.hotel.models.Room ---");
        System.out.print("com.hotel.models.Room Number: ");
        int rn = readInt();
        admin.viewAllAmenities();
        System.out.print("com.hotel.models.Amenity name: ");
        String name = sc.nextLine().trim();
        admin.addAmenityToRoom(rn, name);
    }

    static void adminRemoveAmenityFromRoom(Admin admin) {
        System.out.println("\n--- Remove com.hotel.models.Amenity from com.hotel.models.Room ---");
        System.out.print("com.hotel.models.Room Number: ");
        int rn = readInt();
        admin.viewAmenitiesInRoom(rn);
        System.out.print("com.hotel.models.Amenity name to remove: ");
        String name = sc.nextLine().trim();
        admin.removeAmenityFromRoom(rn, name);
    }

    static void adminAddReceptionist(Admin admin) {
        System.out.println("\n--- Add com.hotel.models.Receptionist Account ---");
        try {
            System.out.print("Username: ");
            String u = sc.nextLine().trim();
            System.out.print("Password (min 6 chars): ");
            String p = sc.nextLine().trim();
            System.out.print("Date of Birth (yyyy-MM-dd): ");
            LocalDate dob = parseDate(sc.nextLine().trim());
            if (dob == null) return;
            System.out.print("Working Hours: ");
            int wh = readInt();
            admin.addReceptionist(u, p, dob, wh);
            System.out.println("com.hotel.models.Receptionist added successfully.");
        } catch (InvalidUsernameException e) {
            System.out.println("Username error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────
    //  HELPER UTILITIES
    // ─────────────────────────────────────────────

    /** Reads an integer, consuming the leftover newline. Returns -1 on bad input. */
    static int readInt() {
        while (true) {
            String line = sc.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid integer: ");
            }
        }
    }

    /** Reads a double, consuming the leftover newline. */
    static double readDouble() {
        while (true) {
            String line = sc.nextLine().trim();
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    // pasrses date string
    static LocalDate parseDate(String s) {
        try {
            return LocalDate.parse(s, DATE_FMT);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Expected yyyy-MM-dd.");
            return null;
        }
    }

    // parses gender
    static Gender parseGender(String s) {
        try {
            return Gender.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid gender. Use MALE or FEMALE.");
            return null;
        }
    }

    // parses view
    static View parseView(String s) {
        try {
            return View.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid view. Options: SEA_VIEW, POOL_VIEW, GARDEN_VIEW, CITY_VIEW");
            return null;
        }
    }

    // selecting payment methods
    static PaymentMethod selectPaymentMethod() {
        System.out.println("Payment Method:");
        System.out.println("1. CASH");
        System.out.println("2. CREDIT_CARD");
        System.out.println("3. ONLINE");
        System.out.print("Select: ");
        return switch (readInt()) {
            case 1 -> PaymentMethod.CASH;
            case 2 -> PaymentMethod.CREDIT_CARD;
            case 3 -> PaymentMethod.ONLINE;
            default -> { System.out.println("Invalid payment method."); yield null; }
        };
    }
}
