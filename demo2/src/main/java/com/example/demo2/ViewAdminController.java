package com.example.demo2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.Optional;


public class ViewAdminController {
    @FXML private ListView<String> adminsListView;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void initialize() {
        loadAdmins();
    }

    private void loadAdmins() {
        ObservableList<String> admins = FXCollections.observableArrayList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (User user : Database.users) {
            if(user instanceof Admin){
                String adminInfo = String.format("%s | Working Hours: %s | DOB: %s",
                    user.getUsername(),
                    ((Admin)user).getWorkingHours(),
                    user.getDateOfBirth().format(formatter));
                admins.add(adminInfo);
            }

            adminsListView.setItems(admins);
        }
    }

    @FXML
    private void deleteAdmin() {
        if (Database.users.size() <= 1) {
            showAlert("Cannot Delete", "You must maintain at least one admin account");
            return;
        }
        int selectedIndex = adminsListView.getSelectionModel().getSelectedIndex();
        String addname = adminsListView.getSelectionModel().getSelectedItem();
        Admin add=null;
        for(User user:Database.users){
            if (user.getUsername().equals(addname)){
                add=(Admin)user;
                break;
            }
        }
        if (selectedIndex >= 0) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirm Deletion");
            confirmation.setHeaderText("Delete Admin Account");
            confirmation.setContentText("Are you sure you want to delete this admin?");

            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Database.users.remove(add);
                loadAdmins(); // Refresh the list
            }
        } else {
            showAlert("No Selection", "Please select an admin to delete");
        }
    }
    @FXML
    public void goBack(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin-DashBoard.fxml"));
        root = loader.load();
        AdminDashBoardController controller = loader.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Admin DashBoard");
        stage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
