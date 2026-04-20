
public class RoomType {
    //single,double,suite
    private String category;
    private int capacity;
    private double BasePrice;
    private int roomTypeId;
    private static int numofroomtypes =0;


    public RoomType(){
        numofroomtypes ++;
        this.roomTypeId = numofroomtypes;
        HotelDatabase.roomTypes.add(this);
    }

    public RoomType(String type, int capacity, double price){
        numofroomtypes ++;
        this.roomTypeId = numofroomtypes;
        setCategory(type);
        setCapacity(capacity);
        setBasePrice(price);
        HotelDatabase.roomTypes.add(this);

    }

    public String getCategory(){return category;}

     public void setCategory(String type) {
    if (type == null || type.trim().isEmpty()) {
        throw new IllegalArgumentException("Room type cannot be empty");
    }
    if (!type.matches("[a-zA-Z ]+")) {
        throw new IllegalArgumentException("Category must contain letters only");
    }

    this.category = type;

    if (type.equalsIgnoreCase("Single")) {
        this.capacity = 1;
        System.out.println("Category is Single, Capacity is automatically set to 1");
    } else if (type.equalsIgnoreCase("Double")) {
        this.capacity = 2;
        System.out.println("Category is Double, Capacity is automatically set to 2");
    } else if (type.equalsIgnoreCase("Triple")) {
        this.capacity = 3;
        System.out.println("Category is Triple, Capacity is automatically set to 3");
    }
}

    public void setCapacity(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        if (capacity >=10) {
            throw new IllegalArgumentException("Capacity cannot be more than 9");
        }

        if (category != null &&
                (category.equalsIgnoreCase("Single") ||
                        category.equalsIgnoreCase("Double") ||
                        category.equalsIgnoreCase("Triple"))) {

            throw new IllegalArgumentException("Capacity is fixed for this category");
        }

        this.capacity = capacity;
    }
    public int getCapacity() {
        return capacity;
    }

    public  double getBasePrice(){
        return  BasePrice;
    }
    public void setBasePrice(double basePrice){
        if (basePrice <= 0) {
            throw new IllegalArgumentException("Base price must be >0");
        }
        if (basePrice > 10000000) { //  upper limit
            throw new IllegalArgumentException("Price very high");
        }
        this.BasePrice = basePrice;
        System.out.println(" price of  " +getCategory()+ " rooms is updated with new price :"+getBasePrice());


    }

    public void setroomTypeId(int roomTypeId){
        this.roomTypeId=roomTypeId;
    }
    public int getroomTypeId(){
        return roomTypeId;
    }

}
