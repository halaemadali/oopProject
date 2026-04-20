import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Reservation {
    private int ID;
    private Room room;
    private Guest guest;
    private Date checkin;
    private Date checkout;
    private ReservationStatus status;
    private Invoice invoice;
    private static int numofreservations = 0;
    private List<Amenity> required_amenities = new ArrayList<>();


    // constructors
    public Reservation(){
        numofreservations ++;
        this.ID = numofreservations;
        this.status = ReservationStatus.PENDING;
        HotelDatabase.reservations.add(this);

    }
    public Reservation(Room r, Guest g, Date in, Date out) throws Exception {
        numofreservations++;
        this.ID = numofreservations;

        this.guest = g;

        setCheckin(in);
        setCheckout(out);

        validateRange(in, out);

        setRoom(r, in, out);

        setStatus(ReservationStatus.PENDING);

        generateInvoice();
        HotelDatabase.reservations.add(this);

    }

    //setters and getters
    private void setID(int ID) {
        this.ID = ID;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public void setRoom(Room room, Date in, Date out) throws RoomNotAvailableException {
        if (!room.checkavailabiltyPeriod(in, out))
            throw new RoomNotAvailableException("Room is booked during this period.");
        this.room = room;
    }
    public void setRoom(Room room) throws Exception {
        if (!room.checkavailabiltyPeriod(this.checkin, this.checkout))
            throw new RoomNotAvailableException("Room is booked during this period.");
        this.room = room;
    }

    public void setCheckin(Date checkin) {
        validateDate(checkin);
        if(this.checkout != null)
            validateRange(checkin, this.checkout);
        this.checkin = checkin;
    }

    public Room getRoom() {
        return room;
    }

    public int getID() {
        return ID;
    }

    public Guest getGuest() {
        return guest;
    }

    public Date getCheckin() {
        return checkin;
    }

    public Date getCheckout() {
        return checkout;
    }

    public void setCheckout(Date checkout) {
        validateDate(checkout);
        if(this.checkin != null)
            validateRange(this.checkin, checkout);
        this.checkout = checkout;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public Invoice getInvoice(){
        return this.invoice;
    }

    public List<Amenity> getRequired_amenities() {
        return required_amenities;
    }

    public void setRequired_amenities(List<Amenity> required_amenities) {
        this.required_amenities = required_amenities;
    }

    //methods

    //calculating length of duration (no. of days)
    public int getDuration(){
        if (checkout == null || checkin == null) return 0;
        long milliseconds = checkout.getTime() - checkin.getTime();
        double days = milliseconds / (1000.0 * 60 * 60 * 24);
        if (days < 1)
            return 1;
        return (int)Math.ceil(days);
    }

    // validates date entry
    private void validateDate(Date d){
        if (d == null) {
            throw new IllegalArgumentException("Selected date cannot be null");
        }
        Date now = new Date();
        if (d.before(now))
            throw new IllegalArgumentException("Selected Date is before current date.");
    }

    //checks whether the check out is after check in or not
    private void validateRange(Date in, Date out){
        if(out.before(in))
            throw new IllegalArgumentException("The check out date cannot be before check in date.");
    }

    //confirming reservation
    public void confirm(){
        setStatus(ReservationStatus.CONFIRMED);
    }

    //cancelling reservation
    public void cancel(){
        setStatus(ReservationStatus.CANCELLED);
    }

    // completing reservation
    public void complete(){
        setStatus(ReservationStatus.COMPLETED);
    }

    //generating invoice for reservation
    public void generateInvoice(){
        if (this.invoice != null)
            throw new IllegalStateException("An invoice is already created for this reservation.");
        invoice = new Invoice(this);
    }
    @Override
    public String toString() {

        String checkInStr = (checkin != null)
                ? new java.text.SimpleDateFormat("dd/MM/yyyy").format(checkin)
                : "null";

        String checkOutStr = (checkout != null)
                ? new java.text.SimpleDateFormat("dd/MM/yyyy").format(checkout)
                : "null";

        return "Reservation ID: " + ID +
                ", Guest: " + (guest != null ? guest.getUsername() : "null") +
                ", Room: " + (room != null ? room.getRoomNumber() : "null") +
                ", Check-in: " + checkInStr +
                ", Check-out: " + checkOutStr +
                ", Status: " + (status != null ? status : "null");

    }
    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Reservation other = (Reservation) obj;

        return this.ID == other.ID;
    }




}
