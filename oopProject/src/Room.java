public class Room {
private int roomnumber;
private RoomType type;
private ArrayList<Amenity> amenities;
private boolean isavailable ;
private double price;
public Room(){
    
    }
  public boolean checkavailability (boolean isavailable)  
    { if (this.isavailable==true)
        return true;
     else 
        return false;
        
    }
      public void addAmenity(String amenity) {
        amenities.add(amenity);
        System.out.println(amenity + " added successfully!");
    }
     public void removeAmenity(String amenity) {
        amenities.remove(amenity);
        System.out.println(amenity + " removed successfully!");
    } 
}
