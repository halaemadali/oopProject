package com.hotel.controllers;

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
import java.util.ResourceBundle;

public class GuestManageReservationController implements Initializable {
    @FXML
    private Label welcomeLabel;                          // fx:id="welcomeLabel"
    @FXML private TableView<Reservation> ReservationsTable;    // fx:id="ReservationsTable"
    @FXML private TableColumn<Reservation, String> colId;      // fx:id="colId"
    @FXML private TableColumn<Reservation, String> colRoom;    // fx:id="colRoom"
    @FXML private TableColumn<Reservation, String> colType;    // fx:id="colType"
    @FXML private TableColumn<Reservation, String> colCheckin; // fx:id="colCheckin"
    @FXML private TableColumn<Reservation, String> colCheckout;// fx:id="colCheckout"
    @FXML private TableColumn<Reservation, String> colStatus;  // fx:id="colStatus"

    private Guest currentGuest;

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

    public void setGuest(Guest g) {
        this.currentGuest = g;
        welcomeLabel.setText("Welcome,  " + g.getUsername());
        refreshTable();
    }

    private void refreshTable() {
        ReservationsTable.getItems().setAll(currentGuest.getReservations());
    }

    @FXML
    private void handleGoToCancel() throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/Resources/fxml/CancelReservationScreen.fxml"));
        Parent root = loader.load();
        CancelReservationController next = loader.getController();
        next.setGuest(currentGuest);
        ((Stage) welcomeLabel.getScene().getWindow())
                .setScene(new Scene(root));
    }

@FXML
    private void handleMakeReservation() throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/Resources/fxml/ReservationScreen.fxml"));
        Parent root = loader.load();
        ReservationController next = loader.getController();
        next.setGuest(currentGuest);
        ((Stage) welcomeLabel.getScene().getWindow())
                .setScene(new Scene(root));
    }
    @FXML
    private void handleBack() {
        // placeholder until main menu is built
        System.out.println("Main menu not built yet");
    }
}
