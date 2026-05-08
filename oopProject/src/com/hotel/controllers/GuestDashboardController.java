package com.hotel.controllers;

import com.hotel.models.Guest;
import com.hotel.models.Reservation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class GuestDashboardController {

    @FXML private Label welcomeLabel;
    @FXML private Label balanceLabel;
    @FXML private Label memberSinceLabel;

    @FXML private Label profileNameLabel;
    @FXML private Label profileGenderLabel;
    @FXML private Label profileDobLabel;
    @FXML private Label profileAddressLabel;
    @FXML private Label profileGenderDetailLabel;
    @FXML private Label profileBalanceLabel;

    @FXML private Label totalResLabel;
    @FXML private Label pendingResLabel;
    @FXML private Label confirmedResLabel;
    @FXML private Label cancelledResLabel;
    @FXML private Label resCountLabel;
    @FXML private Label noReservationsLabel;
    @FXML private VBox reservationRowsContainer;

    private Guest currentGuest;


    public void setGuest(Guest guest) {
        this.currentGuest = guest;
        updateUI();
    }

    private void updateUI() {
        if (currentGuest == null) return;

        // Header
        welcomeLabel.setText("Welcome, " + currentGuest.getUsername());
        balanceLabel.setText(String.format("%.2f", currentGuest.getBalance()));
        memberSinceLabel.setText("HMS Member");

        // Profile card
        profileNameLabel.setText(currentGuest.getUsername());
        profileGenderLabel.setText(currentGuest.getGender().toString());
        profileDobLabel.setText(currentGuest.getDateOfBirth().toString());
        profileAddressLabel.setText(currentGuest.getAddress());
        profileGenderDetailLabel.setText(currentGuest.getGender().toString());
        profileBalanceLabel.setText(String.format("%.2f", currentGuest.getBalance()));

        // Stats
        int total = 0, pending = 0, confirmed = 0, cancelled = 0;
        for (Reservation r : currentGuest.getReservations()) {
            total++;
            switch (r.getStatus()) {
                case PENDING   -> pending++;
                case CONFIRMED -> confirmed++;
                case CANCELLED -> cancelled++;
                default        -> {}
            }
        }
        totalResLabel.setText(String.valueOf(total));
        pendingResLabel.setText(String.valueOf(pending));
        confirmedResLabel.setText(String.valueOf(confirmed));
        cancelledResLabel.setText(String.valueOf(cancelled));
        resCountLabel.setText(total + " records");

        // Build reservation rows
        reservationRowsContainer.getChildren().clear();
        boolean hasActive = false;
        for (Reservation r : currentGuest.getReservations()) {
            if (r.getStatus() == com.hotel.enums.ReservationStatus.CANCELLED ||
                    r.getStatus() == com.hotel.enums.ReservationStatus.COMPLETED) continue;
            hasActive = true;
            reservationRowsContainer.getChildren().add(buildRow(r));
        }

        noReservationsLabel.setVisible(!hasActive);
        noReservationsLabel.setManaged(!hasActive);
    }

    private javafx.scene.layout.HBox buildRow(Reservation r) {
        javafx.scene.layout.HBox row = new javafx.scene.layout.HBox();
        row.setSpacing(0);
        row.setStyle("-fx-padding: 8 4 8 4; -fx-background-radius: 4;");

        String statusColor = switch (r.getStatus()) {
            case PENDING    -> "#f39c12";
            case CONFIRMED  -> "#27ae60";
            case CHECKED_IN -> "#2980b9";
            default         -> "#95a5a6";
        };

        row.getChildren().addAll(
                cell(String.valueOf(r.getID()), 50),
                cell(String.valueOf(r.getRoom() != null ? r.getRoom().getRoomNumber() : "—"), 70),
                cell(r.getRoom() != null ? r.getRoom().getType().getCategory() : "—", 80),
                cell(r.getCheckin()  != null ? r.getCheckin().toString()  : "—", 110),
                cell(r.getCheckout() != null ? r.getCheckout().toString() : "—", 110),
                badge(r.getStatus().toString(), statusColor, 100),
                cell(r.getInvoice() != null ? String.format("$%.2f", r.getInvoice().calculateTotal()) : "—", -1)
        );
        return row;
    }

    private Label cell(String text, double width) {
        Label lbl = new Label(text);
        lbl.setStyle("-fx-font-size: 12px; -fx-text-fill: #2c3e50;");
        if (width > 0) { lbl.setPrefWidth(width); lbl.setMinWidth(width); }
        else javafx.scene.layout.HBox.setHgrow(lbl, javafx.scene.layout.Priority.ALWAYS);
        return lbl;
    }

    private javafx.scene.layout.HBox badge(String text, String color, double width) {
        Label lbl = new Label(text);
        lbl.setStyle("-fx-text-fill: white; -fx-font-size: 10px; -fx-font-weight: bold;" +
                "-fx-background-color: " + color + "; -fx-background-radius: 20; -fx-padding: 3 8 3 8;");
        javafx.scene.layout.HBox box = new javafx.scene.layout.HBox(lbl);
        box.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        box.setPrefWidth(width);
        box.setMinWidth(width);
        return box;
    }

    @FXML
    private void handleLogout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Resources/fxml/WelcomeScreen.fxml"));
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("HMS Hotel- Welcome");
        } catch (Exception e) {
            System.out.println("Logout error: " + e.getMessage());
        }
    }

    @FXML
    private void handleMakeReservation() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/Resources/fxml/ManageReservationScreen.fxml")
            );
            Parent root = loader.load();
            GuestManageReservationController ctrl = loader.getController();
            ctrl.setGuest(currentGuest);

            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Hotel – Make a Reservation");
        } catch (Exception e) {
            System.out.println("Navigation error: " + e.getMessage());
        }
    }
}