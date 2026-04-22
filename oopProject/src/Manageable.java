public interface Manageable {
    void viewAllGuests();
    void viewAllRooms();
    void viewAllReservations();
    void confirmReservation(int reservationId);
    void cancelReservation(int reservationId);
}
