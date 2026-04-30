package com.hotel.controllers;

import com.hotel.database.HotelDatabase;
import com.hotel.models.Amenity;
import com.hotel.models.Guest;
import com.hotel.models.Reservation;
import com.hotel.models.Room;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AvailableRoomsController implements Initializable {


    @FXML private Label              summaryLabel;
    @FXML private ListView<Room>     roomListView;
    @FXML private ListView<Amenity>  amenityListView;
    @FXML private Label              statusLabel;


    private Guest       currentGuest;
    private List<Room>  availableRooms;
    private LocalDate   checkin;
    private LocalDate   checkout;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Room list — custom display (skips availability since all are available)
        roomListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Room r, boolean empty) {
                super.updateItem(r, empty);
                setText(empty || r == null ? null :
                        "Room " + r.getRoomNumber() +
                                " | "   + r.getType().getCategory() +
                                " | "   + r.getView() +
                                " | Floor " + r.getFloor() +
                                " | $"  + r.getPrice() + "/night");
            }
        });


        amenityListView.getItems().addAll(HotelDatabase.amenities);
        amenityListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        amenityListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Amenity a, boolean empty) {
                super.updateItem(a, empty);
                setText(empty || a == null ? null :
                        a.getName() + "  —  $" + a.getPrice());
            }
        });
    }


    public void setData(Guest guest, List<Room> rooms, LocalDate in, LocalDate out) {
        this.currentGuest   = guest;
        this.availableRooms = rooms;
        this.checkin        = in;
        this.checkout       = out;


        roomListView.getItems().setAll(rooms);

        // Shows search summary in the header subtitle
        summaryLabel.setText(
                rooms.get(0).getType().getCategory() +
                        "  |  " + rooms.get(0).getView() +
                        "  |  " + in + "  →  " + out +
                        "  |  " + rooms.size() + " room(s) found"
        );
    }


    @FXML
    private void handleConfirm() {

        Room selectedRoom = roomListView.getSelectionModel().getSelectedItem();

        if (selectedRoom == null) {
            statusLabel.setText("Please select a room to proceed.");
            return;
        }

        List<Amenity> selectedAmenities = new ArrayList<>(
                amenityListView.getSelectionModel().getSelectedItems()
        );

        try {
            Reservation r = currentGuest.finalizeReservation(
                    selectedRoom.getRoomNumber(),
                    availableRooms,
                    selectedAmenities,
                    checkin,
                    checkout
            );


            double amenitiesPrice = 0;
            for (Amenity a : selectedAmenities) {
                amenitiesPrice += a.getPrice();
            }
            double total = r.getInvoice().calculateTotal() + amenitiesPrice;


            statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 13px;");
            statusLabel.setText(
                    "Booked!  Reservation ID: " + r.getID() +
                            "  |  Total: $" + total +
                            "  |  Status: PENDING"
            );

        } catch (Exception e) {
            statusLabel.setText("Booking failed: " + e.getMessage());
        }
    }


    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/Resources/fxml/ReservationScreen.fxml")
            );
            Parent root = loader.load();

            // Passing the guest back to the reservation screen
            ReservationController prev = loader.getController();
            prev.setGuest(currentGuest);

            Stage stage = (Stage) statusLabel.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (Exception e) {
            statusLabel.setText("Navigation error: " + e.getMessage());
        }
    }
}