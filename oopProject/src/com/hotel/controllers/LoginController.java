package com.hotel.controllers;

import com.hotel.database.HotelDatabase;
import com.hotel.models.Admin;
import com.hotel.models.Guest;
import com.hotel.models.Receptionist;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML private TextField     loginUsernameField;
    @FXML private PasswordField loginPasswordField;
    @FXML private Label         loginStatusLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // nothing to initialize on the login screen
    }

    @FXML
    private void handleLogin() {
        String username = loginUsernameField.getText().trim();
        String password = loginPasswordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter username and password.");
            return;
        }

        // Check Guests
        Guest guest = Guest.login(username, password);
        if (guest != null) {
            goToReservation(guest);
            return;
        }

        // Check Admins
        for (Admin a : HotelDatabase.admins) {
            if (a.getUsername().equals(username) && a.getPassword().equals(password)) {
                showSuccess("Welcome Admin: " + username);
                // TODO: goToAdminDashboard(a);
                return;
            }
        }

        // Check Receptionists
        for (Receptionist r : HotelDatabase.receptionists) {
            if (r.getUsername().equals(username) && r.getPassword().equals(password)) {
                showSuccess("Welcome Receptionist: " + username);
                // TODO: goToReceptionistDashboard(r);
                return;
            }
        }

        showError("Invalid username or password.");
    }

    @FXML
    private void handleGoToRegister() {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/Resources/fxml/RegisterScreen.fxml")
            );
            Stage stage = (Stage) loginUsernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Hotel – Register");
        } catch (Exception e) {
            showError("Navigation error: " + e.getMessage());
        }
    }

    private void goToReservation(Guest guest) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/Resources/fxml/ReservationScreen.fxml")
            );
            Parent root = loader.load();
            ReservationController ctrl = loader.getController();
            ctrl.setGuest(guest);

            Stage stage = (Stage) loginUsernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Hotel – Reservations");
        } catch (Exception e) {
            showError("Navigation error: " + e.getMessage());
        }
    }

    private void showError(String message) {
        loginStatusLabel.setStyle("-fx-text-fill: #e76c3c; -fx-font-size: 13px;");
        loginStatusLabel.setText(message);
    }

    private void showSuccess(String message) {
        loginStatusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 13px;");
        loginStatusLabel.setText(message);
    }
}