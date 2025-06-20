package com.example.demo2;

import java.util.Optional;
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
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import java.time.format.DateTimeFormatter;

public class eventController {
    @FXML
    private ListView<String> eventsListView;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void initialize() {
        this.loadEvents();
    }

    private void loadEvents() {
        ObservableList<String> events = FXCollections.observableArrayList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Events event : Database.events) {
            String eventInfo = String.format("%s | Date: %s | Price: $%.2f | Seats: %d/%d | Room: %s",
                    event.getTitle(), event.getDateTime().format(formatter), event.getTicketPrice(),
                    event.getAvailableSeats(), event.getCapacity(), event.getRoom().getName());
            events.add(eventInfo);
        }

        this.eventsListView.setItems(events);
    }

    @FXML
    private void deleteEvent() {
        int selectedIndex = this.eventsListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < Database.events.size()) {
            Events selectedEvent = Database.events.get(selectedIndex);
            Alert confirm = new Alert(AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Deletion");
            confirm.setHeaderText("Delete Event");
            confirm.setContentText("Are you sure you want to delete the event: " + selectedEvent.getTitle() + "?");
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Remove event from Database.events
                Database.events.remove(selectedEvent);
                // Remove event from organizer's event list
                if (selectedEvent.getOrganizer() != null) {
                    selectedEvent.getOrganizer().getEvents().remove(selectedEvent);
                }
                // Remove event's category from attendees' interests
                for (User user : Database.users) {
                    if (user instanceof Attendee attendee && selectedEvent.getCategory() != null) {
                        attendee.getInterests().remove(selectedEvent.getCategory());
                    }
                }
                // Remove event from its category's event list
                for (Category category : Database.Categories) {
                    if (category.getName().equals(selectedEvent.getCategory())) {
                        category.getCategoryEvents().remove(selectedEvent);
                    }
                }
                this.loadEvents();
            }
        } else {
            this.showAlert("No Selection", "Please select an event to delete");
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
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}