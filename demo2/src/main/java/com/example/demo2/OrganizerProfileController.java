package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class OrganizerProfileController {

    @FXML private TextField organizerUsernameField;
    @FXML private PasswordField organizerPasswordField;
    @FXML private TextField organizerVisiblePasswordField;
    @FXML private DatePicker organizerDobPicker;
    @FXML private TextField organizerWalletBalanceField;
    @FXML private Button togglePasswordButton;
    @FXML private ImageView passwordEyeIcon;
    @FXML private Label InvalidMessage;

    private Organizer currentOrganizer;
    private boolean passwordVisible = false;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private static final int MAX_LENGTH = 18;
    private static final int PASSWORD_MIN_LENGTH = 10;

    public void setOrganizer(Organizer organizer) {
        this.currentOrganizer = organizer;
        loadOrganizerData();
    }

    @FXML
    private void initialize() {
        organizerVisiblePasswordField.managedProperty().bind(organizerVisiblePasswordField.visibleProperty());
        organizerPasswordField.managedProperty().bind(organizerPasswordField.visibleProperty());
        organizerWalletBalanceField.setDisable(true);// Make balance read-only
        InvalidMessage.setVisible(false);
        organizerUsernameField.setPromptText("Max 18 characters");
        organizerPasswordField.setPromptText("10–18 chars, mix of letters & digits");

        applyLengthLimit(organizerUsernameField, MAX_LENGTH);
        applyLengthLimit(organizerPasswordField, MAX_LENGTH);
        applyLengthLimit(organizerVisiblePasswordField, MAX_LENGTH); // Added for visible field
    }

    @FXML
    private void applyLengthLimit(TextInputControl field, int maxLength) {
        field.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.getControlNewText().length() <= maxLength) {
                return change;
            } else {
                InvalidMessage.setVisible(true);
                InvalidMessage.setText("Max characters");
                return null;
            }
        }));
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{10,18}$");
    }

    private void loadOrganizerData() {
        if (currentOrganizer != null) {
            organizerUsernameField.setText(currentOrganizer.getUsername());
            organizerPasswordField.setText(currentOrganizer.getPassword());
            organizerVisiblePasswordField.setText(currentOrganizer.getPassword());
            organizerDobPicker.setValue(currentOrganizer.getDateOfBirth());
            organizerWalletBalanceField.setText(String.format("%.2f", currentOrganizer.getBalance()));
        }
    }

    @FXML
    private void togglePasswordVisibility() {
        passwordVisible = !passwordVisible;
        if (passwordVisible) {
            organizerVisiblePasswordField.setVisible(true);
            organizerPasswordField.setVisible(false);
            organizerVisiblePasswordField.setText(organizerPasswordField.getText());
            validatePassword(organizerVisiblePasswordField.getText()); // Validate visible password
        } else {
            organizerVisiblePasswordField.setVisible(false);
            organizerPasswordField.setVisible(true);
            organizerPasswordField.setText(organizerVisiblePasswordField.getText());
            validatePassword(organizerPasswordField.getText()); // Validate hidden password
        }
    }

    // Added helper method for password validation
    private void validatePassword(String password) {
        if (password.length() < PASSWORD_MIN_LENGTH) {
            InvalidMessage.setVisible(true);
            InvalidMessage.setText("Password must be more than 10 characters");
        } else if (!isValidPassword(password)) {
            InvalidMessage.setVisible(true);
            InvalidMessage.setText("Password must contain letters and numbers");
        } else {
            InvalidMessage.setVisible(false);
        }
    }

    @FXML
    private void saveOrganizerDetails() {
        InvalidMessage.setVisible(true);
        try {
            if (currentOrganizer == null) {
                showAlert("Error", "No organizer profile loaded.");
                return;
            }

            // Get password from the currently visible field
            String password = passwordVisible ?
                    organizerVisiblePasswordField.getText().trim() :
                    organizerPasswordField.getText().trim();

            String username = organizerUsernameField.getText().trim();

            if (password.length() < PASSWORD_MIN_LENGTH) {
                InvalidMessage.setText("Password must be more than 10 characters");
                return;
            }

            if (!isValidPassword(password)) {
                InvalidMessage.setText("Password must contain letters and numbers");
                return;
            }

            currentOrganizer.setUsername(username);
            currentOrganizer.setPassword(password);
            currentOrganizer.setDateOfBirth(organizerDobPicker.getValue());

            showAlert("Success", "Profile updated successfully.");
            InvalidMessage.setVisible(false);

        } catch (Exception e) {
            showAlert("Error", "Failed to update profile: " + e.getMessage());
        }
    }

    @FXML
    private void backToMainScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Organizer-DashBoard.fxml"));
        Parent root = loader.load();

        // Pass the updated organizer back
        OrganizerDashBoardController controller = loader.getController();
        controller.setOrganizer(currentOrganizer);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Organizer DashBoard");
        stage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}