import java.util.ArrayList;

public class Room {
    private int roomnumber;
    private RoomType type;
   public ArrayList<Amenity> amenities=new ArrayList<>();
    private boolean isavailable ;
    private double price;

    public Room(){

    }
    public Room(int roomnumber, RoomType type, boolean isavailable, double price) {
        this.roomnumber = roomnumber;
        this.type = type;
        this.isavailable = isavailable;
        this.price = price;
    }
    public RoomType gettype(){
        return type ;
    }
    public void  settype(RoomType type){
        this.type=type;

    }
    public int getroomnumber(){
        return roomnumber ;
    }
    public void  setroomnumber(int roomnumber){
        this.roomnumber=roomnumber;
    }

    public double getprice(){
        return price ;
    }
    public void  setprice(double price){
        if (price>0)
        this.price=price;
        
    }
    

    public boolean getisavailable(){
        return isavailable ;
    }
    public void  setisavailable(boolean isavailable){
        this.isavailable=isavailable;
    }

    public void checkavailability (boolean isavailable)
    { if (this.isavailable==true)
        System.out.println("is available");

    else
        System.out.println("is not available");


    }
    public ArrayList<Amenity> getAmenities() {
        return this.amenities;
    }
    public void addAmenity(Amenity amenity) {
        amenities.add(amenity);
        System.out.println(amenity + " added successfully!");
    }
    public void removeAmenity(Amenity amenity) {
        amenities.remove(amenity);
        System.out.println(amenity + " removed successfully!");
    }
}
