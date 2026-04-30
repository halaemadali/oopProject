package com.hotel.controllers;

import com.hotel.database.HotelDatabase;
import com.hotel.enums.Gender;
import com.hotel.models.Guest;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML private TextField        regUsernameField;
    @FXML private PasswordField    regPasswordField;
    @FXML private PasswordField    regConfirmPasswordField;
    @FXML private DatePicker       regDobPicker;
    @FXML private TextField        regAddressField;
    @FXML private ComboBox<Gender> regGenderCombo;
    @FXML private TextField        regBalanceField;
    @FXML private Label            regStatusLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        regGenderCombo.getItems().addAll(Gender.values());
    }

    @FXML
    private void handleRegister() {
        String username   = regUsernameField.getText().trim();
        String password   = regPasswordField.getText().trim();
        String confirm    = regConfirmPasswordField.getText().trim();
        String address    = regAddressField.getText().trim();
        String balanceTxt = regBalanceField.getText().trim();
        LocalDate dob     = regDobPicker.getValue();
        Gender gender     = regGenderCombo.getValue();

        // All fields must be filled
        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()
                || address.isEmpty() || balanceTxt.isEmpty()
                || dob == null || gender == null) {
            showError("Please fill in all fields.");
            return;
        }

        // Passwords must match
        if (!password.equals(confirm)) {
            showError("Passwords do not match.");
            return;
        }

        // Balance must be a valid positive number
        double balance;
        try {
            balance = Double.parseDouble(balanceTxt);
            if (balance < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showError("Balance must be a positive number.");
            return;
        }

        // Username must not already exist
        for (Guest g : HotelDatabase.guests) {
            if (g.getUsername().equalsIgnoreCase(username)) {
                showError("Username already exists.");
                return;
            }
        }

        // Create and save the new guest
        try {
            Guest newGuest = new Guest(username, password, dob, balance, address, gender);
            HotelDatabase.guests.add(newGuest);
            showSuccess("Registered successfully! Redirecting to login…");

            new Thread(() -> {
                try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
                Platform.runLater(this::goToLogin);
            }).start();

        } catch (Exception e) {
            showError("Registration failed: " + e.getMessage());
        }
    }

    @FXML
    private void handleBackToLogin() {
        goToLogin();
    }

    private void goToLogin() {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/Resources/fxml/LoginScreen.fxml")
            );
            Stage stage = (Stage) regUsernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Hotel – Login");
        } catch (Exception e) {
            showError("Navigation error: " + e.getMessage());
        }
    }

    private void showError(String message) {
        regStatusLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 13px;");
        regStatusLabel.setText(message);
    }

    private void showSuccess(String message) {
        regStatusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 13px;");
        regStatusLabel.setText(message);
    }
}