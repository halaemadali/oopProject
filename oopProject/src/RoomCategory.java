public enum RoomCategory {
    SINGLE,DOUBLE,SUITE;
    static double  basePrice;

    public static double getBasePrice(){
        return  basePrice;
    }
    public void setBasePrice(double basePrice){
        if (basePrice <= 0) {
            throw new IllegalArgumentException("Base price must be >0");
        }
        if (basePrice > 10000000) { //  upper limit
            throw new IllegalArgumentException("Price very high");
        }
        this.basePrice = basePrice;

    }
}
