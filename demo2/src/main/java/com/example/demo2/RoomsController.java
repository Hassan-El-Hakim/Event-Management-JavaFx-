package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Optional;

public class RoomsController {
    @FXML private ListView<String> roomsListView;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void initialize() {
        loadRooms();
    }

    private void loadRooms() {
        roomsListView.getItems().clear();
        for (Room room : Database.rooms) {
            String roomInfo = String.format("%s | Capacity: %d | Available: %s",
                    room.getName(),
                    room.getCapacity(),
                    room.isAvailable() ? "Yes" : "No");
            roomsListView.getItems().add(roomInfo);
        }
    }

    @FXML
    private void showAddRoomDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add New Room");
        dialog.setHeaderText("Enter room details");


        TextField nameField = new TextField();
        nameField.setPromptText("Room Name");
        TextField capacityField = new TextField();
        capacityField.setPromptText("Capacity");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Capacity:"), 0, 1);
        grid.add(capacityField, 1, 1);
        dialog.getDialogPane().setContent(grid);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                String name = nameField.getText().trim();
                int capacity = Integer.parseInt(capacityField.getText().trim());

                if (name.isEmpty()) {
                    throw new IllegalArgumentException("Room name cannot be empty");
                }
                if (capacity <= 0) {
                    throw new IllegalArgumentException("Capacity must be positive");
                }

                // Add to database
                Database.rooms.add(new Room(name, capacity));
                loadRooms(); // Refresh the list
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter a valid number for capacity");
            } catch (IllegalArgumentException e) {
                showAlert("Invalid Input", e.getMessage());
            }
        }
    }

    @FXML
    private void deleteRoom() {
        int selectedIndex = roomsListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < Database.rooms.size()) {

            Alert confirmation = new Alert(AlertType.CONFIRMATION);
            confirmation.setTitle("Confirm Deletion");
            confirmation.setHeaderText("Delete Room");
            confirmation.setContentText("Are you sure you want to delete this room?");

            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Database.rooms.remove(selectedIndex);
                loadRooms();
            }
        } else {
            showAlert("No Selection", "Please select a room to delete");
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
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}