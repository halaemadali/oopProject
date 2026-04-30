package com.hotel;

import com.hotel.controllers.ReservationController;
import com.hotel.database.HotelDatabase;
import com.hotel.models.Guest;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // 1. Initialize the database — rooms, guests, amenities etc.
        HotelDatabase.initializeData();

        // 2. Grab an existing guest from the database to test with
        Guest testGuest = HotelDatabase.guests.get(0);
        System.out.println("Testing with guest: " + testGuest.getUsername());

        // 3. Load the reservation screen directly (no login needed yet)
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/Resources/fxml/ReservationScreen.fxml")
        );
        Parent root = loader.load();

        // 4. Inject the guest into the controller
        ReservationController ctrl = loader.getController();
        ctrl.setGuest(testGuest);

        // ── NEW: Load Login screen as the entry point ──────────────────────
        Parent loginRoot = FXMLLoader.load(
                getClass().getResource("/Resources/fxml/LoginScreen.fxml")
        );

        // 5. Show the window
        stage.setScene(new Scene(loginRoot));
        stage.setTitle("Hotel Reservation System");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}