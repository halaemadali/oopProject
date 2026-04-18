
import java.util.ArrayList;

public class Room {
    private int roomNumber;
    private RoomType type;
    private boolean isAvailable;
    private double price;

    // Constants
    private static final int MAXROOMNUMBER = 1000000;
    private static final double MAXPRICE = 1000000;

    // Default constructor
    public Room() {
    }

    // Constructor with validation error
    public Room(int roomNumber, RoomType type, boolean isAvailable, double price) {
        setRoomNumber(roomNumber);
        setType(type);
        setAvailable(isAvailable);
       // setPrice(price);
    }

    private ArrayList<RoomAmenity> roomAmenities = new ArrayList<>();



    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        if (type == null) {
            throw new IllegalArgumentException("Room type cannot be null");
        }
        this.type = type;

    }


    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        if (roomNumber <= 0) {
            throw new IllegalArgumentException("Room number must be positive");
        }

        if (roomNumber > MAXROOMNUMBER) {
            throw new IllegalArgumentException("Room number too large");
        }

        this.roomNumber = roomNumber;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (Double.isNaN(price) || Double.isInfinite(price)) {
            throw new IllegalArgumentException("Price cannot be NaN or infinite");
        }

        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }

        if (price > MAXPRICE) {
            throw new IllegalArgumentException("Price exceeds maximum allowed");
        }

        if (type != null && price < type.getBasePrice()) {
            throw new IllegalArgumentException("Price cannot be less than base price of room type");
        }

        this.price = price;
    }

public double calculateRoomPrice(){
    double  amenitiesTOT= 0;
        for (int i=0;i<roomAmenities.size();i++){
            amenitiesTOT+=roomAmenities.get(i).getPrice();

        }
       double total= this.type.getBasePrice()+amenitiesTOT;
        return total;
}

    public boolean getisavailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public void checkAvailability() {
        if (this.isAvailable) {
            System.out.println("Room is available");
        } else {
            System.out.println("Room is not available");
        }
    }


    public ArrayList<Amenity> getAmenities() {
        return new ArrayList<>(roomAmenities); // return copy (encapsulation)
    }


public void addAmenity(RoomAmenity amenity) {
    if (amenity == null) {
        throw new IllegalArgumentException("Amenity cannot be null");
    }

    if (roomAmenities.contains(amenity)) {
        throw new IllegalArgumentException("Amenity already exists");
    }

    if (roomAmenities.size() >= 20) {
        throw new IllegalStateException("Maximum number of amenities reached");
    }

    roomAmenities.add( amenity);
    System.out.println("Amenity added successfully!");
}

    public void removeAmenity(RoomAmenity amenity) {
        if (amenity == null) {
            throw new IllegalArgumentException("Amenity cannot be null");
        }

        if (!roomAmenities.contains(amenity)) {
            throw new IllegalArgumentException("Amenity not found");
        }

        roomAmenities.remove(amenity);
        System.out.println("Amenity removed successfully!");
    }


    public void validateRoom() {
        if (type == null) {
            throw new IllegalStateException("Room type is not set");
        }

        if (price < type.getBasePrice()) {
            throw new IllegalStateException("Invalid price: less than base price");
        }

        if (roomAmenities.isEmpty()) {
            System.out.println("Warning: Room has no amenities");
        }
    }


    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", type=" + type +
                ", price=" + price +
                ", available=" + isAvailable +
                ", amenities=" + roomAmenities.size() +
                '}';
    }
}
