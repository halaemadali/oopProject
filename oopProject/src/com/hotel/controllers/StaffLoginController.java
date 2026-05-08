package com.hotel.controllers;

import com.hotel.database.HotelDatabase;
import com.hotel.models.Admin;
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

public class StaffLoginController implements Initializable {

    @FXML private TextField     usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label         statusLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }


    @FXML
    private void handleLogin() {

        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter username and password.");
            return;
        }


        for (Receptionist r : HotelDatabase.receptionists) {
            if (r.getUsername().equals(username)
                    && r.getPassword().equals(password)) {
                goToReceptionistDashboard(r);
                return;
            }
        }


        for (Admin a : HotelDatabase.admins) {
            if (a.getUsername().equals(username)
                    && a.getPassword().equals(password)) {
                goToAdminDashboard(a);
                return;
            }
        }

        showError("Invalid username or password.");
    }


    @FXML
    private void handleBack() {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/Resources/fxml/WelcomeScreen.fxml")
            );
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Hotel – Welcome");
        } catch (Exception e) {
            showError("Navigation error: " + e.getMessage());
        }
    }



    private void goToReceptionistDashboard(Receptionist receptionist) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/Resources/fxml/ReceptionistDashboard.fxml")
            );
            Parent root = loader.load();


            ReceptionistDashboardController ctrl = loader.getController();
            ctrl.setReceptionist(receptionist);

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Receptionist Dashboard");

        } catch (Exception e) {
            showError("Navigation error: " + e.getMessage());
        }
    }



    private void goToAdminDashboard(Admin admin) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/Resources/fxml/AdminDashboard.fxml")
            );
            Parent root = loader.load();


            //AdminDashboardController ctrl = loader.getController();
            //ctrl.setAdmin(admin);

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Dashboard");

        } catch (Exception e) {

            showSuccess("Welcome Admin: " + admin.getUsername());
        }
    }


    private void showError(String message) {
        statusLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 13px;");
        statusLabel.setText(message);
    }

    private void showSuccess(String message) {
        statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 13px;");
        statusLabel.setText(message);
    }
}

