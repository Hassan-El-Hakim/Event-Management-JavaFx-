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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.util.Optional;

public class orgController {
    @FXML
    private ListView<Organizer> organizerListView;
    private Stage stage;
    private Scene scene;
    @FXML
    private Parent root;

    @FXML
    public void initialize() {
        ObservableList<Organizer> organizers = FXCollections.observableArrayList();
        for (User user : Database.users) {
            if (user instanceof Organizer organizer) {
                organizers.add(organizer);
            }
        }
        organizerListView.setItems(organizers);
        organizerListView.setCellFactory(param -> new ListCell<Organizer>() {
            @Override
            protected void updateItem(Organizer organizer, boolean empty) {
                super.updateItem(organizer, empty);
                setText(empty || organizer == null ? null : organizer.getUsername());
            }
        });
    }

    @FXML
    private void deleteOrganizer() {
        Organizer selectedOrganizer = organizerListView.getSelectionModel().getSelectedItem();
        if (selectedOrganizer != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Deletion");
            confirm.setHeaderText("Delete Organizer");
            confirm.setContentText("Are you sure you want to delete organizer: " + selectedOrganizer.getUsername() + "? All their events will also be deleted.");
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Collect events to remove
                java.util.List<Events> eventsToRemove = new java.util.ArrayList<>();
                for (Events event : Database.events) {
                    if (event.getOrganizer() != null && event.getOrganizer().equals(selectedOrganizer)) {
                        eventsToRemove.add(event);
                    }
                }
                // Remove events from Database.events and categories
                for (Events event : eventsToRemove) {
                    Database.events.remove(event);
                    for (Category category : Database.Categories) {
                        if (category.getName().equals(event.getCategory())) {
                            category.getCategoryEvents().remove(event);
                        }
                    }
                }
                // Remove organizer from Database.users
                Database.users.remove(selectedOrganizer);
                // Refresh the list view
                ObservableList<Organizer> organizers = FXCollections.observableArrayList();
                for (User user : Database.users) {
                    if (user instanceof Organizer organizer) {
                        organizers.add(organizer);
                    }
                }
                organizerListView.setItems(organizers);
            }
        } else {
            showAlert("No Selection", "Please select an organizer to delete");
        }
    }

    @FXML
    private void goBack(ActionEvent event) throws Exception {
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