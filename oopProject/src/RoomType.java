
public class RoomType {
    private RoomCategory roomcategory;    //single,double,suite
    private int capacity;
    private int roomTypeId;

    public RoomType (RoomCategory roomcategory ,int capacity,int roomTypeId ){
        this. roomcategory= roomcategory;
        this.capacity=capacity;

        this.roomTypeId=roomTypeId;
    }
    public RoomType(){
        this. roomcategory= RoomCategory.SINGLE;
        this.capacity=1;

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
}
