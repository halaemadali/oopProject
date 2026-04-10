import java.util.Date;

public class Invoice implements Payable {
    private int ID;
    private Reservation reservation;
    private double amount;
    private Date date;
    private boolean isPaid;
    private PaymentMethod method;

    //constructors
    public Invoice(){

    }
    public Invoice(Reservation r){
        this.reservation = r;
        this.ID = this.reservation.getID();
        this.amount = calculateTotal();
        this.isPaid = false;

    }

    //setters and getters
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public double getAmount() {
        return amount;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean getPaid() {
        return isPaid;
    }

    private void setPaid(boolean paid) {
        isPaid = paid;
    }

    //methods
    @Override
    public void pay(PaymentMethod method){
        if (isPaid)
            throw new IllegalStateException("Already paid.");

        this.method = method;
        this.date = new Date();
        this.isPaid = true;

    }

    private double calculateTotal(){
        if (reservation == null || reservation.getRoom() == null)
            return 0;
        double total = (reservation.getDuration()) * (reservation.getRoom().getPrice());
        return total;
    }


}
