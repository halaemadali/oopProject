import java.time.LocalDate;
public class Admin extends Staff {

    public Admin(String username, String password, LocalDate dateOfBirth, int workingHours) {
        super(username, password, dateOfBirth, Role.ADMIN, workingHours);

    }
public Admin(){
    super();
}

    //add room
    public void addRoom(int roomNumber, double price, boolean isAvailable, RoomCategory roomcategory, int capacity, double basePrice, int roomTypeId) {

        RoomType type = new RoomType(roomcategory, capacity, roomTypeId);

        Room newRoom = new Room();
        newRoom.setRoomNumber(roomNumber);
        newRoom.setPrice(price);
        newRoom.setAvailable(isAvailable);
        newRoom.setType(type);

        HotelDatabase.rooms.add(newRoom);

        System.out.println("Room " + roomNumber + " added successfully!");
    }


    //view room
    public void viewRoom(int roomNumber) {
        for (Room r : HotelDatabase.rooms) {
            if (r.getRoomNumber() == roomNumber) {


            }
        }
        System.out.println("Room not found");
    }


    //update room
    public void updateRoom(int roomnumber, double newPrice, boolean isAvailable, int roomTypeId, RoomCategory roomcategory, int capacity, double basePrice) {
        for (Room r : HotelDatabase.rooms) {
            if (r.getRoomNumber() == roomnumber) {

                r.setPrice(newPrice);
                r.setAvailable(isAvailable);
                r.getType().setroomTypeId(roomTypeId);
                r.getType().setRoomCategory(roomcategory);
                r.getType().setCapacity(capacity);
                r.getType().getRoomCategory().setBasePrice(basePrice);


                System.out.println("Room updated successfully");
                return;
            }
        }
        System.out.println("Room not found");

    }


    //delete room
    public void deleteRoom(int roomnumber) {
        for (int i = 0; i < HotelDatabase.rooms.size(); i++) {
            if (HotelDatabase.rooms.get(i).getRoomNumber()  == roomnumber){
                HotelDatabase.rooms.remove(i);
                System.out.println(" Room deleted successfully");
                return;
            }

        }

        System.out.println("Room not found");

    }



    //Add Amenity
    public void addAmenity(String name ,double  price ,int quantity) {
        Amenity a = new Amenity(name , price , quantity );
        HotelDatabase.amenities.add(a);
        System.out.println("Amenity added: " + name);
    }


    //View all amenity
    public void viewAllAmenities() {
        if (HotelDatabase.amenities.isEmpty()) {
            System.out.println("No amenities found");
            return;
        }

        System.out.println("\n========= ALL AMENITIES =========");

        for (Amenity a : HotelDatabase.amenities) {
            System.out.println(a.getName() + "\t price: "+a.getPrice());

        }
        System.out.println("======================================= \n");
    }


    //Update Amenity
    public void updateAmenity(String Amenity , double newPrice) {
        for (Amenity a : HotelDatabase.amenities) {
            if (a.getName ().equalsIgnoreCase(Amenity)) {
                a.setPrice(newPrice);
                System.out.println("Amenity price updated successfully");
                return;
            }
        }
        System.out.println("Amenity not found");
    }


    //Delete Amenity
    public void deleteAmenity(String name) {
        for (int i = 0; i < HotelDatabase.amenities.size(); i++) {
            if (HotelDatabase.amenities.get(i).getName().equalsIgnoreCase(name)) {
                HotelDatabase.amenities.remove(i);
                System.out.println("Amenity deleted successfully");
                return;
            }
        }
        System.out.println("Amenity not found");
    }


    //view amenities in room
    public void viewAmenitiesInRoom(int roomNumber) {

        for (Room r : HotelDatabase.rooms) {

            if (r.getRoomNumber() == roomNumber) {

                if (r.getAmenities().isEmpty()) {
                    System.out.println("No amenities in this room");
                    return;
                }

                System.out.println(" \n Amenities in Room " + roomNumber + ":");

                for (Amenity a : r.getAmenities()) {
                    System.out.println("- " + a.getQuantity() + "\t"+ a.getName() + "\tprice: " + a.getTotalPrice() );
                }
                System.out.println("\n");
                return;
            }
        }

        System.out.println("Room not found");

    }



    //update amenity in room
    public void updateAmenityInRoom(int roomNumber, String amenity, int newQuantity) {
        for (Room r : HotelDatabase.rooms) {
            if (r.getRoomNumber() == roomNumber) {

                for (Amenity a : r.getAmenities()) {
                    if (a.getName().equalsIgnoreCase(amenity)) {

                        a.setQuantity(newQuantity);

                        System.out.println("Amenity quantity updated in room");
                        return;
                    }
                }

                System.out.println("Amenity not found in room");
                return;
            }
        }

        System.out.println("Room not found");
    }




    //Remove amenity from room
    public void removeAmenityFromRoom(int roomNumber, String amenity) {

        for (Room r : HotelDatabase.rooms) {

            if (r.getRoomNumber() == roomNumber) {

                if (r.getAmenities().isEmpty()) {
                    System.out.println("No amenities to remove");
                    return;
                }

                for (int i = 0; i < r.getAmenities().size(); i++) {

                    if (r.getAmenities().get(i).getName().equalsIgnoreCase(amenity)) {

                        r.getAmenities().remove(i);

                        System.out.println("Amenity removed successfully");
                        return;
                    }
                }

                System.out.println("Amenity not found");
                return;
            }
        }

        System.out.println("Room not found");
    }


    // add amenity to room
    public void addAmenityToRoom(int roomNumber, String name , double price ,int quantity) {

        for (Room r : HotelDatabase.rooms) {

            if (r.getRoomNumber() == roomNumber) {

                // check duplicate
                for (Amenity a : r.getAmenities()) {
                    if (a.getName().equalsIgnoreCase(name)) {
                        a.setQuantity(a.getQuantity() + quantity);
                        System.out.println( quantity + " " + name + " added successfully in room " + roomNumber);
                        return;
                    }
                }

                Amenity a = new Amenity(name , price , quantity );
                r.getAmenities().add(a);

                System.out.println( quantity + name + " added successfully in room " + roomNumber);
                return;
            }
        }

        System.out.println("Room not found");
    }

}
