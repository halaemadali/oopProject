public class Amenity {
    private String name;
    private double price;   // price per unit
    private int quantity;   // how many user ordered

    public Amenity(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        setQuantity(quantity);
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
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

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be > 0");
        }
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return price * quantity;
    }
    @Override
    public String toString(){
        return getName();
    }

}
