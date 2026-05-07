package com.hotel.controllers;


import com.hotel.enums.ReservationStatus;
import com.hotel.models.Amenity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.hotel.models.Guest;
import com.hotel.models.Reservation;
import com.hotel.enums.PaymentMethod;
import com.hotel.exceptions.InvalidPaymentException;

public class CheckoutController implements Initializable {

    @FXML private Label lblUsername;
    @FXML private Label lblBalance;
    @FXML private Label lblRoomTotal;
    @FXML private Label lblAmenities;
    @FXML private Label lblTotalDue;
    @FXML private Label statusLabel;

    @FXML private TableView<Reservation> reservationsTable;
    @FXML private TableColumn<Reservation, Integer> colID;
    @FXML private TableColumn<Reservation, Integer> colRoom;
    @FXML private TableColumn<Reservation, String>  colCheckin;
    @FXML private TableColumn<Reservation, String>  colCheckout;
    @FXML private TableColumn<Reservation, String>  colStatus;
    @FXML private TableColumn<Reservation, Double>  colTotal;

    @FXML private ComboBox<Integer> cmbRoomNumber;
    @FXML private ComboBox<String> cmbPayment;

    @FXML private Button btnCheckOut;
    @FXML private Button btnBack;

    private Guest guest;
    private Stage stage;

    private Reservation selectedReservation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colID.setCellValueFactory(c ->
                new SimpleIntegerProperty(c.getValue().getID()).asObject());

        colRoom.setCellValueFactory(c ->
                new SimpleIntegerProperty(c.getValue().getRoom().getRoomNumber()).asObject());

        colCheckin.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getCheckin().toString()));

        colCheckout.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getCheckout().toString()));

        colStatus.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getStatus().name()));

        colTotal.setCellValueFactory(c ->
                new SimpleDoubleProperty(c.getValue().getInvoice().calculateTotal()).asObject());

        cmbPayment.getItems().addAll("CASH", "CREDIT_CARD", "ONLINE");

        cmbRoomNumber.setOnAction(e -> autoCalculate());


    }


    public void setGuest (Guest guest) {
        this.guest = guest;
        refreshUI();
    }



    private void refreshUI() {
        if (guest == null) return;

        lblUsername.setText(guest.getUsername());
        lblBalance.setText(String.format("$ %.2f", guest.getBalance()));

        cmbRoomNumber.getItems().clear();
        selectedReservation = null;
        lblRoomTotal.setText("$ --");
        lblAmenities.setText("$ --");
        lblTotalDue.setText("$ --");
        statusLabel.setText("");


        ObservableList<Reservation> checkedInOnly = FXCollections.observableArrayList();

        for (Reservation r : guest.getReservations()) {
            if (r.getStatus() == ReservationStatus.CHECKED_IN) {
                checkedInOnly.add(r);
                if (r.getRoom() != null) {
                    cmbRoomNumber.getItems()
                            .add(r.getRoom().getRoomNumber());
                }
            }
        }
        reservationsTable.setItems(checkedInOnly);

        if (checkedInOnly.isEmpty()) {
            statusLabel.setText("No checked-in reservations found.");
        }

    }


    private void autoCalculate() {

        if (cmbRoomNumber.getValue() == null) {
            statusLabel.setText("Please select a room number.");
            return;
        }

        int roomNum = cmbRoomNumber.getValue();
        selectedReservation = null;

        for (Reservation r : guest.getReservations()) {
            if (r.getRoom() != null && r.getRoom().getRoomNumber() == roomNum) {
                selectedReservation = r;
                break;
            }
        }



        double roomTotal = selectedReservation.getRoom().getPrice()
                * selectedReservation.getDuration();


        double amenitiesTotal = 0;
        for (Amenity a : selectedReservation.getRequired_amenities()) {
            amenitiesTotal += a.getPrice();
        }

        double totalDue = selectedReservation.getInvoice().calculateTotal()
                + amenitiesTotal;


        lblRoomTotal.setText(String.format("$ %.2f", roomTotal));
        lblAmenities.setText(String.format("$ %.2f", amenitiesTotal));
        lblTotalDue.setText(String.format("$ %.2f", totalDue));

        statusLabel.setText("");
    }




    @FXML
    public void handleCheckOut() {

        if (cmbRoomNumber.getValue() == null) {
            statusLabel.setText("Please select a room first");
            return;
        }

        if (selectedReservation == null) {
            statusLabel.setText("Please select a valid room");
            return;
        }


        if (cmbPayment.getValue() == null) {
            statusLabel.setText("Please select a payment method");
            return;
        }

        int roomNum = cmbRoomNumber.getValue();
        PaymentMethod method = PaymentMethod.valueOf(cmbPayment.getValue());

        try {

            guest.checkOut(roomNum, method);

            statusLabel.setText("Checkout successful! Thank you \n Please proceed to the front desk");


            lblBalance.setText(String.format("$ %.2f", guest.getBalance()));


           cmbRoomNumber.setValue(null);
           selectedReservation = null;

            refreshUI();
            statusLabel.setText("Checkout successful! Thank you \n Please proceed to the front desk");


        } catch (InvalidPaymentException e) {

            statusLabel.setText("Payment failed: " + e.getMessage());
        }
    }


    @FXML
    public void handleBack() throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/Resources/fxml/GuestDashboard.fxml")
        );
        Parent root = loader.load();

        Stage stage = (Stage) statusLabel.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

}


