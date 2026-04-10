
import java.time.LocalDate;

public class Admin extends Staff {

     public Admin(String username, String password, LocalDate dateOfBirth, int workingHours){
         super(username, password,dateOfBirth,Role.ADMIN, workingHours);

     }

     
       //add room
     public void addRoom(int roomNumber, double price, boolean isAvailable, String NO3, int capacity, double basePrice) {

        RoomType type = new RoomType(NO3, capacity, basePrice);

        Room newRoom = new Room();
        newRoom.setroomnumber(roomNumber);
        newRoom.setPrice(price);
        newRoom.setisavailable(isAvailable);
        newRoom.settype(type);

        HotelDatabase.rooms.add(newRoom);

        System.out.println("Room " + roomNumber + " added successfully!");
    }

     
       //view room
    public void viewRoom(int roomNumber) {
        for (Room r : HotelDatabase.rooms) {
            if (r.getroomnumber() == roomNumber) {

                System.out.println("\n=============VIEW ROOM ==============" );

                System.out.println("Room Number: " + r.getroomnumber());
                System.out.println("Price: " + r.getPrice());
                System.out.println("Available: " + r.getisavailable());

                // RoomType details
                System.out.println("NO3: " + r.gettype().getNO3());
                System.out.println("Capacity: " + r.gettype().getCapacity());
                System.out.println("Base Price: " + r.gettype().getBasePrice());
                System.out.println("Amenities: " + r.getAmenities());

                System.out.println("======================================= \n");
              return;
            }
        }
        System.out.println("Room not found");
    }

  

     //update room
    public void updateRoom(int roomnumber, double newPrice, boolean isAvailable, String NO3 , int capacity , double basePrice ) {
       for (Room r : HotelDatabase.rooms) {
           if (r.getroomnumber() == roomnumber) {

               r.setPrice(newPrice);
               r.setisavailable(isAvailable);

               r.gettype().setNO3(NO3);
               r.gettype().setCapacity(capacity);
               r.gettype().setBasePrice(basePrice);


               System.out.println("Room updated successfully");
            return;
           }
       }
       System.out.println("Room not found");

   }


     
      //delete room
     public void deleteRoom(int roomnumber){
         for (int i=0 ; i < HotelDatabase.rooms.size() ; i++){
             if(HotelDatabase.rooms.get(i).getroomnumber() == roomnumber) {
                 HotelDatabase.rooms.remove(i);
                 System.out.println(" Room deleted successfully");
                 return;
             }
         }

         System.out.println ("Room not found");

     }
     

      //view Room Type
    public void viewRoomType(String NO3) {
        System.out.println("\n=========== VIEW ROOM TYPE =========");
        for (RoomType t : HotelDatabase.roomTypes) {
            if (t.getNO3().equalsIgnoreCase(NO3)) {

                System.out.println("Type: " + t.getNO3());
                System.out.println("Capacity: " + t.getCapacity());
                System.out.println("Base Price: " + t.getBasePrice());
                
                return;
            }
        }

        System.out.println("RoomType not found");
    }


     
     // Update room type
    public void updateRoomType(String NO3, int newCapacity, double newBasePrice) {
        for (RoomType t : HotelDatabase.roomTypes) {
            if (t.getNO3().equalsIgnoreCase(NO3)) {

                t.setCapacity(newCapacity);
                t.setBasePrice(newBasePrice);

                System.out.println("RoomType updated successfully");
                return;
            }
        }
        System.out.println("RoomType not found");
    }



 // Delete room type
    public void deleteRoomType(String NO3) {
        for (int i = 0; i < HotelDatabase.roomTypes.size(); i++) {
            if (HotelDatabase.roomTypes.get(i).getNO3().equalsIgnoreCase(NO3)) {

                HotelDatabase.roomTypes.remove(i);
                System.out.println("RoomType deleted successfully");
                return;
            }
        }

        System.out.println("RoomType not found");
    }


      //Add Amenity
    public void addAmenity(String name) {
        Amenity a = new Amenity(name);
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
            System.out.println(a.getAmenity());

        }
        System.out.println("======================================= \n");
    }


     
    //Update Amenity
    public void updateAmenity(String oldAmenity, String newAmenity) {
        for (Amenity a : HotelDatabase.amenities) {
            if (a.getAmenity().equalsIgnoreCase(oldAmenity)) {
                a.setamenity(newAmenity);
                System.out.println("Amenity updated");
                return;
            }
        }
        System.out.println("Amenity not found");
    }


     
     //Delete Amenity
    public void deleteAmenity(String name) {
        for (int i = 0; i < HotelDatabase.amenities.size(); i++) {
            if (HotelDatabase.amenities.get(i).getAmenity().equalsIgnoreCase(name)) {
                HotelDatabase.amenities.remove(i);
                System.out.println("Amenity deleted");
                return;
            }
        }
        System.out.println("Amenity not found");
    }

}

}
