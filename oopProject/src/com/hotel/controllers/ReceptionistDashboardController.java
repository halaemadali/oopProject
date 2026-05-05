package com.hotel.controllers;

import com.hotel.database.HotelDatabase;
import com.hotel.enums.PaymentMethod;
import com.hotel.models.Guest;
import com.hotel.models.Receptionist;
import com.hotel.models.Reservation;
import com.hotel.models.Room;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ReceptionistDashboardController implements Initializable {

    // ── Header ──────────────────────────────────────────────────────────────
    @FXML private Label lblReceptionist;

    // ── Input fields ────────────────────────────────────────────────────────
    @FXML private TextField         reservationIdField;
    @FXML private TextField         guestUsernameField;
    @FXML private TextField         roomNumberField;
    @FXML private ComboBox<String>  paymentCombo;

    // ── Status label ────────────────────────────────────────────────────────
    @FXML private Label statusLabel;

    // ── Table + columns ─────────────────────────────────────────────────────
    @FXML private TableView<Reservation>              reservationsTable;
    @FXML private TableColumn<Reservation, Integer>   colID;
    @FXML private TableColumn<Reservation, String>    colGuest;
    @FXML private TableColumn<Reservation, Integer>   colRoom;
    @FXML private TableColumn<Reservation, String>    colCheckin;
    @FXML private TableColumn<Reservation, String>    colCheckout;
    @FXML private TableColumn<Reservation, String>    colStatus;
    @FXML private TableColumn<Reservation, Double>    colTotal;

    // ── State ────────────────────────────────────────────────────────────────
    private Receptionist currentReceptionist;

    // ════════════════════════════════════════════════════════════════════════
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Wire table columns
        colID.setCellValueFactory(c ->
                new SimpleIntegerProperty(c.getValue().getID()).asObject());

        colGuest.setCellValueFactory(c -> {
            Guest g = c.getValue().getGuest();
            return new SimpleStringProperty(g != null ? g.getUsername() : "—");
        });

        colRoom.setCellValueFactory(c -> {
            Room r = c.getValue().getRoom();
            return new SimpleIntegerProperty(r != null ? r.getRoomNumber() : 0).asObject();
        });

        colCheckin.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getCheckin() != null
                        ? c.getValue().getCheckin().toString() : "—"));

        colCheckout.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getCheckout() != null
                        ? c.getValue().getCheckout().toString() : "—"));

        colStatus.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getStatus().name()));

        colTotal.setCellValueFactory(c ->
                new SimpleDoubleProperty(c.getValue().getInvoice() != null
                        ? c.getValue().getInvoice().calculateTotal() : 0).asObject());

        // Payment method options
        paymentCombo.getItems().addAll("CASH", "CREDIT_CARD", "ONLINE");

        // Load all reservations on startup
        loadAllReservations();
    }

    // ── Called from login controller after successful login ─────────────────
    public void setReceptionist(Receptionist r) {
        this.currentReceptionist = r;
        lblReceptionist.setText(r.getUsername());
        loadAllReservations();
    }

    // ════════════════════════════════════════════════ SIDEBAR ACTIONS ═══════

    // Show all reservations
    @FXML
    private void handleViewAll() {
        clearStatus();
        loadAllReservations();
        showInfo("Showing all reservations (" + HotelDatabase.reservations.size() + " total).");
    }

    // Show only PENDING reservations
    @FXML
    private void handleViewPending() {
        clearStatus();
        ObservableList<Reservation> pending = FXCollections.observableArrayList();
        for (Reservation r : HotelDatabase.reservations) {
            if (r.getStatus().name().equals("PENDING")) {
                pending.add(r);
            }
        }
        reservationsTable.setItems(pending);
        showInfo("Showing " + pending.size() + " PENDING reservation(s).");
    }

    // Confirm a reservation by ID
    @FXML
    private void handleConfirm() {
        clearStatus();
        int id = parseReservationId();
        if (id < 0) return;

        currentReceptionist.confirmReservation(id);
        loadAllReservations();
        showSuccess("Reservation #" + id + " confirmed.");
    }

    // Cancel a reservation by ID
    @FXML
    private void handleCancel() {
        clearStatus();
        int id = parseReservationId();
        if (id < 0) return;

        currentReceptionist.cancelReservation(id);
        loadAllReservations();
        showSuccess("Reservation #" + id + " cancelled.");
    }

    // Check in a guest
    @FXML
    private void handleCheckIn() {
        clearStatus();

        String username = guestUsernameField.getText().trim();
        int id = parseReservationId();
        if (id < 0) return;

        if (username.isEmpty()) {
            showError("Please enter the guest username.");
            return;
        }

        currentReceptionist.checkIn(username, id);
        loadAllReservations();
        showSuccess("Check-in processed for guest: " + username);
    }

    // Process guest checkout
    @FXML
    private void handleCheckout() {
        clearStatus();

        String username = guestUsernameField.getText().trim();
        int roomNum = parseRoomNumber();
        if (roomNum < 0) return;

        if (username.isEmpty()) {
            showError("Please enter the guest username.");
            return;
        }

        if (paymentCombo.getValue() == null) {
            showError("Please select a payment method.");
            return;
        }

        PaymentMethod method = PaymentMethod.valueOf(paymentCombo.getValue());
        currentReceptionist.processGuestCheckout(username, roomNum, method);
        loadAllReservations();
        showSuccess("Checkout processed for room " + roomNum + ".");
    }

    // View all guests (prints to console — can be extended to a dialog)
    @FXML
    private void handleViewGuests() {
        clearStatus();
        currentReceptionist.viewAllGuests();
        showInfo("Guest list printed to console. (" + HotelDatabase.guests.size() + " guests)");
    }

    // View all rooms
    @FXML
    private void handleViewRooms() {
        clearStatus();
        currentReceptionist.viewAllRooms();
        showInfo("Room list printed to console. (" + HotelDatabase.rooms.size() + " rooms)");
    }

    // Logout — go back to main login screen
    @FXML
    private void handleLogout() {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/Resources/fxml/LoginScreen.fxml")
            );
            Stage stage = (Stage) statusLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Hotel Login");
        } catch (Exception e) {
            showError("Logout error: " + e.getMessage());
        }
    }

    // ════════════════════════════════════════════════════ HELPERS ═══════════

    private void loadAllReservations() {
        ObservableList<Reservation> all =
                FXCollections.observableArrayList(HotelDatabase.reservations);
        reservationsTable.setItems(all);
    }

    // Parse reservation ID field — returns -1 on failure
    private int parseReservationId() {
        try {
            int id = Integer.parseInt(reservationIdField.getText().trim());
            if (id <= 0) throw new NumberFormatException();
            return id;
        } catch (NumberFormatException e) {
            showError("Please enter a valid Reservation ID (positive number).");
            return -1;
        }
    }

    // Parse room number field — returns -1 on failure
    private int parseRoomNumber() {
        try {
            int num = Integer.parseInt(roomNumberField.getText().trim());
            if (num <= 0) throw new NumberFormatException();
            return num;
        } catch (NumberFormatException e) {
            showError("Please enter a valid Room Number (positive number).");
            return -1;
        }
    }

    private void clearStatus() {
        statusLabel.setText("");
    }

    private void showError(String msg) {
        statusLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 13px;");
        statusLabel.setText("❌  " + msg);
    }

    private void showSuccess(String msg) {
        statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 13px;");
        statusLabel.setText("✅  " + msg);
    }

    private void showInfo(String msg) {
        statusLabel.setStyle("-fx-text-fill: #2980b9; -fx-font-size: 13px;");
        statusLabel.setText("ℹ️  " + msg);
    }
}
