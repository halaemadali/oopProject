package com.hotel.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class WelcomeController {

    @FXML private Button btnGuestLogin;
    @FXML private Button btnStaffLogin;



    @FXML
    public void handleGuestLogin() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Resources/fxml/LoginScreen.fxml"));
        Stage stage = (Stage) btnGuestLogin.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    public void handleStaffLogin() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Resources/fxml/StaffLoginScreen.fxml"));
        Stage stage = (Stage) btnStaffLogin.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}


