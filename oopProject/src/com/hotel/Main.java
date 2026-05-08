package com.hotel;

import com.hotel.database.HotelDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // 1. Initialize your data first so logins can be validated
        HotelDatabase.initializeData();

        try {
            // 2. Load the Login Screen FXML
            // Make sure this path matches your folder structure exactly
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/Resources/fxml/LoginScreen.fxml")
            );
            Parent root = loader.load();

            // 3. Set up the stage
            stage.setScene(new Scene(root));
            stage.setTitle("Hotel Management System - Login");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/Resources/images/Hotel_Logo.png")));

            // Optional: Prevent the window from being too small
            stage.setResizable(false);

            stage.show();

        } catch (Exception e) {
            System.err.println("Failed to load Login Screen:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}