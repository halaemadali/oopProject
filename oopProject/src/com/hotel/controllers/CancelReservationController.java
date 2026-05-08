package com.hotel.controllers;

import com.hotel.enums.ReservationStatus;
import com.hotel.models.Guest;
import com.hotel.models.Reservation;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CancelReservationController implements Initializable {
    @FXML private TableView<Reservation> cancelTable;
    @FXML private Label welcomeLabel;
    @FXML private Label statusLabel;
    @FXML private TableColumn<Reservation, String> colId;      // fx:id="colId"
    @FXML private TableColumn<Reservation, String> colRoom;    // fx:id="colRoom"
    @FXML private TableColumn<Reservation, String> colType;    // fx:id="colType"
    @FXML private TableColumn<Reservation, String> colCheckin; // fx:id="colCheckin"
    @FXML private TableColumn<Reservation, String> colCheckout;// fx:id="colCheckout"
    @FXML private TableColumn<Reservation, String> colStatus;  // fx:id="colStatus"
    @FXML private Label guestTagLabel;
    private Guest currentGuest ;

    public Guest getGuest(){
        return currentGuest;
    }
    public void setGuest(Guest g) {
        this.currentGuest = g;
        welcomeLabel.setText(
                "Select a reservation below to cancel it"
        );
        guestTagLabel.setText(g.getUsername());

        List<Reservation> cancellable = new ArrayList<>();
        for (Reservation r : g.getReservations()) {
            if (r.getStatus() == ReservationStatus.PENDING ||
                    r.getStatus() == ReservationStatus.CONFIRMED) {
                cancellable.add(r);
            }
        }

        if (cancellable.isEmpty()) {
            statusLabel.setText(
                    "You have no active reservations to cancel.");
        } else {
            cancelTable.getItems().setAll(cancellable);
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(c ->
                new SimpleStringProperty(
                        String.valueOf(c.getValue().getID())));

        colRoom.setCellValueFactory(c ->
                new SimpleStringProperty(
                        String.valueOf(c.getValue().getRoom().getRoomNumber())));

        colType.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getRoom().getType().getCategory()));

        colCheckin.setCellValueFactory(c ->
                new SimpleStringProperty(
                        String.valueOf(c.getValue().getCheckin())));

        colCheckout.setCellValueFactory(c ->
                new SimpleStringProperty(
                        String.valueOf(c.getValue().getCheckout())));

        colStatus.setCellValueFactory(c ->
                new SimpleStringProperty(
                        String.valueOf(c.getValue().getStatus())));

    }

    public void handleConfirmCancel(){
        Reservation selected = cancelTable.getSelectionModel().getSelectedItem();

        if(selected == null){
            statusLabel.setText("Please Choose a Reservation to cancel");
            return;
        }

        currentGuest.cancelReservation(selected.getRoom().getRoomNumber());
        cancelTable.getItems().remove(selected);
        statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 13px;");
        statusLabel.setText("Reservation cancelled successfully.");
    }

    @FXML
    private void handleBack() throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/Resources/fxml/ManageReservationScreen.fxml"));

        Parent root = loader.load();
        GuestManageReservationController prev = loader.getController();
        prev.setGuest(currentGuest);

        Stage stage = (Stage) statusLabel.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}