package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AddAdminController {

    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private DatePicker dateOfBirthPicker;
    @FXML private TextField workingHoursField;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private void goBack(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin-DashBoard.fxml"));
        root = loader.load();
        AdminDashBoardController controller = loader.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Admin DashBoard");
        stage.show();
    }

    @FXML
    public void saveAdmin(ActionEvent event) {
        try {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            LocalDate dateOfBirth = dateOfBirthPicker.getValue();
            String workingHours = workingHoursField.getText().trim();

            // Validate inputs
            if (username.isEmpty() || password.isEmpty() || dateOfBirth == null || workingHours.isEmpty()) {
                showAlert("Invalid Input", "Please fill in all fields.");
                return;
            }

            // Check for duplicate username in admins list
            for (User user: Database.users) {
                if (user.getUsername().equals(username)) {
                    showAlert("Duplicate Username", "An admin with this username already exists.");
                    return;
                }
            }


            Admin newAdmin = new Admin(username, password, dateOfBirth, workingHours);
            Database.users.add(newAdmin);


            showAlert("Success", "New admin added to admins list: " + username + ". Note: This admin will not persist after the application restarts.");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin-DashBoard.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Admin DashBoard");
            stage.show();

        } catch (Exception e) {
            showAlert("Error", "Failed to add admin: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
