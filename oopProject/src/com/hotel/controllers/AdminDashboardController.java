package com.hotel.controllers;

import com.hotel.database.HotelDatabase;
import com.hotel.enums.View;
import com.hotel.models.Admin;
import com.hotel.models.RoomType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {

    // ── Header ──────────────────────────────────────────────────────────────
    @FXML private Label lblAdmin;

    // ── Status ──────────────────────────────────────────────────────────────
    @FXML private Label statusLabel;

    // ── Panels ──────────────────────────────────────────────────────────────
    @FXML private VBox panelAddRoom;
    @FXML private VBox panelAddRoomType;
    @FXML private VBox panelAddAmenity;
    @FXML private VBox panelAddReceptionist;

    // ── Add Room fields ──────────────────────────────────────────────────────
    @FXML private TextField        roomNumberField;
    @FXML private TextField        roomFloorField;
    @FXML private ComboBox<RoomType> roomTypeCombo;
    @FXML private ComboBox<View>   roomViewCombo;

    // ── Add Room Type fields ─────────────────────────────────────────────────
    @FXML private TextField rtCategoryField;
    @FXML private TextField rtCapacityField;
    @FXML private TextField rtPriceField;

    // ── Add Amenity fields ───────────────────────────────────────────────────
    @FXML private TextField amenityNameField;
    @FXML private TextField amenityPriceField;

    // ── Add Receptionist fields ──────────────────────────────────────────────
    @FXML private TextField     recepUsernameField;
    @FXML private PasswordField recepPasswordField;
    @FXML private DatePicker    recepDobPicker;
    @FXML private TextField     recepHoursField;

    // ── State ────────────────────────────────────────────────────────────────
    private Admin currentAdmin;

    // ════════════════════════════════════════════════════════════════════════
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        roomTypeCombo.getItems().addAll(HotelDatabase.roomTypes);
        roomViewCombo.getItems().addAll(View.values());
    }

    /** Called by LoginController after a successful admin login. */
    public void setAdmin(Admin admin) {
        this.currentAdmin = admin;
        lblAdmin.setText(admin.getUsername());
    }

    // ══════════════════════════ SIDEBAR — Panel switchers ════════════════════

    @FXML
    private void handleShowAddRoom() {
        showPanel(panelAddRoom);
        clearStatus();
    }

    @FXML
    private void handleShowAddRoomType() {
        showPanel(panelAddRoomType);
        clearStatus();
    }

    @FXML
    private void handleShowAddAmenity() {
        showPanel(panelAddAmenity);
        clearStatus();
    }

    @FXML
    private void handleShowAddReceptionist() {
        showPanel(panelAddReceptionist);
        clearStatus();
    }

    // ══════════════════════════ FORM SUBMIT handlers ═════════════════════════

    @FXML
    private void handleAddRoom() {
        clearStatus();

        // Validate inputs
        String roomNumText = roomNumberField.getText().trim();
        String floorText   = roomFloorField.getText().trim();

        if (roomNumText.isEmpty() || floorText.isEmpty()
                || roomTypeCombo.getValue() == null
                || roomViewCombo.getValue() == null) {
            showError("Please fill in all room fields.");
            return;
        }

        int roomNumber, floor;
        try {
            roomNumber = Integer.parseInt(roomNumText);
            floor      = Integer.parseInt(floorText);
        } catch (NumberFormatException e) {
            showError("Room number and floor must be valid integers.");
            return;
        }

        try {
            currentAdmin.addRoom(
                    roomNumber,
                    roomTypeCombo.getValue(),
                    true,
                    roomViewCombo.getValue(),
                    floor
            );
            showSuccess("Room " + roomNumber + " added successfully.");
            roomNumberField.clear();
            roomFloorField.clear();
            roomTypeCombo.setValue(null);
            roomViewCombo.setValue(null);

        } catch (Exception e) {
            showError("Failed to add room: " + e.getMessage());
        }
    }

    @FXML
    private void handleAddRoomType() {
        clearStatus();

        String category     = rtCategoryField.getText().trim();
        String capacityText = rtCapacityField.getText().trim();
        String priceText    = rtPriceField.getText().trim();

        if (category.isEmpty() || capacityText.isEmpty() || priceText.isEmpty()) {
            showError("Please fill in all room type fields.");
            return;
        }

        int capacity;
        double price;
        try {
            capacity = Integer.parseInt(capacityText);
            price    = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            showError("Capacity must be an integer and price must be a number.");
            return;
        }

        try {
            currentAdmin.addRoomType(category, capacity, price);
            // Refresh the combo so new type is immediately available in Add Room
            roomTypeCombo.getItems().setAll(HotelDatabase.roomTypes);
            showSuccess("Room type '" + category + "' added successfully.");
            rtCategoryField.clear();
            rtCapacityField.clear();
            rtPriceField.clear();

        } catch (Exception e) {
            showError("Failed to add room type: " + e.getMessage());
        }
    }

    @FXML
    private void handleAddAmenity() {
        clearStatus();

        String name      = amenityNameField.getText().trim();
        String priceText = amenityPriceField.getText().trim();

        if (name.isEmpty() || priceText.isEmpty()) {
            showError("Please fill in all amenity fields.");
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            showError("Price must be a valid number.");
            return;
        }

        try {
            currentAdmin.addAmenity(name, price);
            showSuccess("Amenity '" + name + "' added successfully.");
            amenityNameField.clear();
            amenityPriceField.clear();

        } catch (Exception e) {
            showError("Failed to add amenity: " + e.getMessage());
        }
    }

    @FXML
    private void handleAddReceptionist() {
        clearStatus();

        String username   = recepUsernameField.getText().trim();
        String password   = recepPasswordField.getText().trim();
        LocalDate dob     = recepDobPicker.getValue();
        String hoursText  = recepHoursField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || dob == null || hoursText.isEmpty()) {
            showError("Please fill in all receptionist fields.");
            return;
        }

        int hours;
        try {
            hours = Integer.parseInt(hoursText);
        } catch (NumberFormatException e) {
            showError("Working hours must be a valid integer.");
            return;
        }

        try {
            currentAdmin.addReceptionist(username, password, dob, hours);
            showSuccess("Receptionist '" + username + "' added successfully.");
            recepUsernameField.clear();
            recepPasswordField.clear();
            recepDobPicker.setValue(null);
            recepHoursField.clear();

        } catch (Exception e) {
            showError("Failed to add receptionist: " + e.getMessage());
        }
    }

    // ══════════════════════════ LOGOUT ═══════════════════════════════════════

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

    // ══════════════════════════ HELPERS ══════════════════════════════════════

    /** Show only the selected panel, hide all others. */
    private void showPanel(VBox target) {
        for (VBox panel : new VBox[]{panelAddRoom, panelAddRoomType, panelAddAmenity, panelAddReceptionist}) {
            boolean show = panel == target;
            panel.setVisible(show);
            panel.setManaged(show);
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
}