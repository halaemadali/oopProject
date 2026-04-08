public class RoomType {
    private String NO3;    //single,double,suite
    private int capacity;
    private double basePrice;

    public RoomType (String NO3 ,int capacity, double basePrice ){
        this. NO3= NO3;
        this.capacity=capacity;
        this.basePrice= basePrice;
    }
public RoomType(){}


    public String getNO3(){
        return  NO3;
    }
    public void setNO3(String n){
        NO3=n;
    }
    public int getCapacity(){
        return capacity ;
    }
    public void  setCapacity(int c){
        capacity=c;

    }
    public double getBasePrice(){
        return  basePrice;
    }
    public void setBasePrice(double p){
        basePrice=p;
    }

}
