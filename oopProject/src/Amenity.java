public class Amenity {
    private String name;
    private double price;   // price per unit

    public Amenity(String name, double price) {
        this.name = name;
        this.price = price;
        HotelDatabase.amenities.add(this);

    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }


    @Override
    public String toString(){
        return getName();
    }

    @Override
    public String toString(){
        return    "( " + name +
                " price: "  + price + " )";

    }

}
