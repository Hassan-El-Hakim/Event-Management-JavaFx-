package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class AdminProfileController {

    @FXML private Label usernameLabel;
    @FXML private Label dobLabel;
    @FXML private Label workingHoursLabel;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Admin admin;

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
    @FXML
    public void loadAdmin() {
        usernameLabel.setText(admin.getUsername());
        dobLabel.setText(admin.getDateOfBirth().toString());
        workingHoursLabel.setText(admin.getWorkingHours());
    }

    public void initialize() {

            /*usernameLabel.setText(admin.getUsername());
            dobLabel.setText(admin.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            workingHoursLabel.setText(admin.getWorkingHours());*/
    }

    @FXML
    public void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin-DashBoard.fxml"));
        root = loader.load();
        AdminDashBoardController controller = loader.getController();
        controller.setAdmin(admin);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}