
public class RoomType {
    private RoomCategory roomcategory;    //single,double,suite
    private int capacity;
    private double BasePrice;
    private int roomTypeId;
    private static int numofroomtypes =0;

    public RoomType (RoomCategory roomcategory ,int capacity,double price ){
        numofroomtypes ++;
        this. roomcategory= roomcategory;
        this.capacity=capacity;
        this.BasePrice = price;
        this.roomTypeId= numofroomtypes;
    }
    public RoomType(){
        this. roomcategory= RoomCategory.SINGLE;
        this.capacity=1;
        numofroomtypes ++;
        this.roomTypeId = numofroomtypes;

    }


    public RoomCategory getRoomCategory(){
        return  roomcategory;
    }
    public void setRoomCategory(RoomCategory roomcategory){
        if (roomcategory == null) {
            throw new IllegalArgumentException("Room category cannot be null");
        }
        this.roomcategory = roomcategory;
    }
    public int getCapacity(){
        return capacity ;
    }
    public void  setCapacity(int capacity){
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
        if (capacity > 10) {
            throw new IllegalArgumentException("Capacity too large");
        }

        if (capacity >0)
        {if (roomcategory == RoomCategory.SINGLE && capacity > 2) {
            throw new IllegalArgumentException("Single room max capacity is 2");
        }

            if (roomcategory == RoomCategory.DOUBLE && capacity > 4) {
                throw new IllegalArgumentException("Double room max capacity is 4");
            }
            if (roomcategory == RoomCategory.SUITE && capacity >15) {
                throw new IllegalArgumentException("suite max capacity is 15");
            }
            this.capacity = capacity;}
    }

    public void setroomTypeId(int roomTypeId){
        this.roomTypeId=roomTypeId;
    }
    public int getroomTypeId(){
        return roomTypeId;
    }
    public void setBasePrice(double p){
        this.BasePrice =p;
    }
}
