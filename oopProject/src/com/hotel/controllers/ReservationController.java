package com.hotel.controllers;

import com.hotel.database.HotelDatabase;
import com.hotel.enums.View;
import com.hotel.exceptions.RoomNotAvailableException;
import com.hotel.models.Amenity;
import com.hotel.models.Guest;
import com.hotel.models.Room;
import com.hotel.models.RoomType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReservationController implements Initializable {


    @FXML private ComboBox<RoomType> roomTypeCombo;
    @FXML private ComboBox<View>     viewCombo;
    @FXML private DatePicker         checkinPicker;
    @FXML private DatePicker         checkoutPicker;
    @FXML private Label              statusLabel;


    private Guest      currentGuest;
    private List<Room> availableRooms = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        roomTypeCombo.getItems().addAll(HotelDatabase.roomTypes);
        viewCombo.getItems().addAll(View.values());
    }


    public void setGuest(Guest g) {
        this.currentGuest = g;
    }


    @FXML
    public void handleSearch() {

        // Validate — all fields must be filled
        if (roomTypeCombo.getValue()   == null ||
                viewCombo.getValue()       == null ||
                checkinPicker.getValue()   == null ||
                checkoutPicker.getValue()  == null) {
            statusLabel.setText("Please fill in all fields.");
            return;
        }

        try {
            availableRooms = currentGuest.makeReservation(
                    roomTypeCombo.getValue(),
                    viewCombo.getValue(),
                    checkinPicker.getValue(),
                    checkoutPicker.getValue()
            );
            goToRooms();

        } catch (RoomNotAvailableException e) {
            statusLabel.setText("No rooms found: " + e.getMessage());

        } catch (Exception e) {
            statusLabel.setText("Error: " + e.getMessage());
        }
    }

    private void goToRooms() throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/Resources/fxml/AvailableRoomsScreen.fxml")
        );
        Parent root = loader.load();

        AvailableRoomsController next = loader.getController();
        next.setData(
                currentGuest,
                availableRooms,
                checkinPicker.getValue(),
                checkoutPicker.getValue()
        );

        Stage stage = (Stage) statusLabel.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}