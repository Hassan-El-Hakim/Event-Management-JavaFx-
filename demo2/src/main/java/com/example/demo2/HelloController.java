package com.example.demo2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class HelloController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField addressField;
    @FXML
    private DatePicker dobPicker;
    @FXML
    private ComboBox<String> genderComboBox;
    @FXML
    private Label balanceLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField addCreditField;

    private Attendee attendee;
    @FXML
    public void initialize() {
        genderComboBox.getItems().addAll("Male", "Female", "Other");
    }

    public void setAttendee(Attendee attendee) {
        this.attendee = attendee;

        try {
            usernameField.setText(attendee.getUsername());
            passwordField.setText(attendee.getPassword());
            addressField.setText(attendee.getAddress());
            dobPicker.setValue(attendee.getDateOfBirth());
            genderComboBox.setValue(attendee.getgender().toString());
            showBalance();
        } catch (Exception e) {
            System.err.println("Error setting attendee fields: " + e.getMessage());
        }
    }

    public void showBalance() {
        if (balanceLabel != null && attendee != null) {
            balanceLabel.setText(" $" + String.format("%.2f", attendee.getBalance()));
        } else {
            System.err.println("Error: balanceLabel or currentAttendee is null");
        }
    }

    @FXML
    public void updateAttendee(ActionEvent event) {
        try {
            errorLabel.setText("");
            errorLabel.setTextFill(Color.RED);

            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            String address = addressField.getText().trim();
            LocalDate dob = dobPicker.getValue();
            String gender = genderComboBox.getValue();
            String creditInput = addCreditField.getText().trim();

            // Username validation
            if (username.isEmpty()) {
                throw new IllegalArgumentException("Username cannot be empty.");
            }
            if (!username.matches("^[A-Za-z][A-Za-z0-9_]{2,15}$")) {
                throw new IllegalArgumentException("Username must start with a letter and be 3–16 characters long, using only letters, digits, or underscores.");
            }

            // Password validation
            if (password.isEmpty()) {
                throw new IllegalArgumentException("Password cannot be empty.");
            }
            if (password.length() < 6) {
                throw new IllegalArgumentException("Password must be at least 6 characters long.");
            }
            if (password.matches("\\d+")) {
                throw new IllegalArgumentException("Password cannot be numbers only. It must include letters or symbols.");
            }

            // Address validation
            if (address.isEmpty()) {
                throw new IllegalArgumentException("Address cannot be empty.");
            }
            if (!address.matches("^[\\w\\s,.-]{5,}$")) {
                throw new IllegalArgumentException("Address must be at least 5 characters and contain only letters, digits, spaces, commas, dots, or dashes.");
            }

            // DOB and Gender validation
            if (dob == null) {
                throw new IllegalArgumentException("Date of birth must be selected.");
            }
            if (gender == null || gender.isEmpty()) {
                throw new IllegalArgumentException("Gender must be selected.");
            }

            // Credit validation and addition
            if (!creditInput.isEmpty()) {
                if (!creditInput.matches("\\d+(\\.\\d{1,2})?")) {
                    throw new IllegalArgumentException("Credit must be a valid number (e.g., 10 or 15.75).");
                }

                double creditValue = Double.parseDouble(creditInput);
                if (creditValue < 0) {
                    throw new IllegalArgumentException("Credit cannot be negative.");
                }

                attendee.getWallet().AddCredit(creditValue);
                addCreditField.clear();
            }

            // Update attendee profile
            attendee.setUsername(username);
            attendee.setPassword(password);
            attendee.setAddress(address);
            attendee.setDateOfBirth(dob);
            attendee.setGender(Gender.valueOf(gender));

            showBalance();

            errorLabel.setText("Profile updated successfully!");
            errorLabel.setTextFill(Color.GREEN);

        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
            errorLabel.setTextFill(Color.RED);
        } catch (Exception e) {
            errorLabel.setText("Unexpected error: " + e.getMessage());
            errorLabel.setTextFill(Color.RED);
            e.printStackTrace();

        }

    }
    @FXML
    public void logOut(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo2/Welcome-page.fxml"));
        Parent root = loader.load();
        WelcomePageController controller = loader.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void backToAttendeeDashBoard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo2/AttendeeDashboard.fxml"));
        Parent root = loader.load();
        AttendeeDashboardController controller = loader.getController();
        controller.setAttendee(this.attendee);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}