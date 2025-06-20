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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.Optional;

public class attController {
    @FXML
    private ListView<String> attendeeListView;
    @FXML
    private TextField attendeeNameField;
    @FXML
    private TextField attendeeEmailField;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private ObservableList<String> attendees = FXCollections.observableArrayList();

    public void initialize() {
        this.loadAttendees();
        this.attendeeListView.setItems(this.attendees);
    }

    private void loadAttendees() {
        this.attendees.clear();
        for (User user : Database.users) {
            if (user instanceof Attendee attendee) {
                this.attendees.add(attendee.getUsername() + " - " + attendee.getAddress());
            }
        }
    }

    @FXML
    private void deleteAttendee() {
        int selectedIndex = this.attendeeListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            User selectedUser = Database.users.stream()
                    .filter(u -> u instanceof Attendee)
                    .skip(selectedIndex)
                    .findFirst()
                    .orElse(null);
            if (selectedUser != null) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Confirm Deletion");
                confirm.setHeaderText("Delete Attendee");
                confirm.setContentText("Are you sure you want to delete attendee: " + selectedUser.getUsername() + "?");
                Optional<ButtonType> result = confirm.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Remove attendee from all events' attendee lists
                    for (Events event : Database.events) {
                        event.GetAttendees().remove(selectedUser);
                    }
                    // Remove attendee from Database.users
                    Database.users.remove(selectedUser);
                    this.loadAttendees();
                }
            } else {
                this.showAlert("No Selection", "Please select an attendee to delete");
            }
        } else {
            this.showAlert("No Selection", "Please select an attendee to delete");
        }
    }

    public void goBack(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Admin-DashBoard.fxml"));
        this.root = (Parent)loader.load();
        AdminDashBoardController controller = (AdminDashBoardController)loader.getController();
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(this.root);
        this.stage.setScene(this.scene);
        this.stage.setTitle("Admin DashBoard");
        this.stage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}