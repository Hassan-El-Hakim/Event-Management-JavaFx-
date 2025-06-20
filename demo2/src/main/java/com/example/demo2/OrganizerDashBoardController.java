// OrganizerDashBoardController.java
package com.example.demo2;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class OrganizerDashBoardController {
    private Organizer organizer;
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    @FXML private GridPane eventsGrid;
    @FXML private TextField eventSearchField;
    @FXML private Label eventIdLabel, eventNameLabel, eventDateLabel;
    @FXML private Label eventPriceLabel, eventCapacityLabel, eventAvailableSeatsLabel;
    @FXML private Label eventCategoryLabel, eventRoomLabel;
    @FXML private ListView<Attendee> attendeesListView;
    @FXML private ListView<Events> eventsListView;
    @FXML private Button deleteEventButton;

    @FXML private VBox createEventForm;
    @FXML private TextField newEventName;
    @FXML private DatePicker newEventDate;
    @FXML private ComboBox<String> newEventStartTime;
    @FXML private TextField newEventPrice;
    @FXML private TextField newEventCapacity;
    @FXML private ComboBox<String> newEventCategory;
    @FXML private ComboBox<Room> newEventRoom;


    @FXML private Button profileButton;
    @FXML private Button LogoutButton;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Events selectedEvent;

    private boolean passwordVisible = false;
    private final StringProperty passwordProperty = new SimpleStringProperty();

    public void setOrganizer(Organizer org) {
        this.organizer = org;
    }

    @FXML
    public void initialize() {
        try {
            setupComboBoxes();

            createEventForm.setVisible(false);
        } catch (Exception e) {
            showAlert("Initialization Error", "Failed to initialize controller: " + e.getMessage());
            e.printStackTrace();
        }
    }




    private void setupComboBoxes() {
        ObservableList<String> timeOptions = FXCollections.observableArrayList();
        for (int hour = 0; hour < 24; hour++) {
            timeOptions.add(LocalTime.of(hour, 0).format(timeFormatter));
        }
        newEventStartTime.setItems(timeOptions);

        newEventCategory.setItems(FXCollections.observableArrayList(
                "Conference", "Workshop", "Seminar", "Social"
        ));

        List<Room> sampleRooms = Database.rooms;
        newEventRoom.setItems(FXCollections.observableArrayList(sampleRooms));

        newEventRoom.setConverter(new StringConverter<Room>() {
            @Override
            public String toString(Room room) {
                return room != null ? room.getName() + " (Capacity: " + room.getCapacity() + ")" : "";
            }

            @Override
            public Room fromString(String string) {
                return sampleRooms.stream()
                        .filter(r -> toString(r).equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }


    private void showEventDetails(Events event) {
        this.selectedEvent = event; // Store the selected event
        if (event == null) {
            clearEventDetails();
            return;
        }

        eventIdLabel.setText(String.valueOf(event.getEventID()));
        eventNameLabel.setText(event.getTitle());
        eventDateLabel.setText(event.getDateTime().toString());
        eventPriceLabel.setText(String.format("$%.2f", event.getTicketPrice()));
        eventCapacityLabel.setText(String.valueOf(event.getAvailableSeats() + event.GetAttendees().size()));
        eventAvailableSeatsLabel.setText(String.valueOf(event.getAvailableSeats()));
        eventCategoryLabel.setText(event.getCategory());

        String eventString = event.toString();
        if (eventString.contains("Room:")) {
            String roomInfo = eventString.split("Room:")[1].split(",")[0].trim();
            eventRoomLabel.setText(roomInfo);
        } else {
            eventRoomLabel.setText("No room");
        }

        attendeesListView.setItems(FXCollections.observableArrayList(event.GetAttendees()));
        deleteEventButton.setDisable(false); // Enable delete button when event is selected

    }

    private void clearEventDetails() {
        this.selectedEvent = null;
        eventIdLabel.setText("");
        eventNameLabel.setText("");
        eventDateLabel.setText("");
        eventPriceLabel.setText("");
        eventCapacityLabel.setText("");
        eventAvailableSeatsLabel.setText("");
        eventCategoryLabel.setText("");
        eventRoomLabel.setText("");
        attendeesListView.getItems().clear();
    }

    @FXML
    private void refreshEvents() {
        eventsGrid.getChildren().clear();
        List<Events> eventList = organizer.getEvents();

        int column = 0;
        int row = 0;
        for (Events event : eventList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EventCard.fxml"));
                AnchorPane card = loader.load();
                EventCardController controller = loader.getController();
                controller.setData(event, this::showEventDetailsPage);

                eventsGrid.add(card, column, row);
                column++;
                if (column == 3) {
                    column = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showEventDetailsPage(Events event) {
        showEventDetails(event);
        createEventForm.setVisible(false);
    }

    @FXML
    private void searchEvents() {
        String searchText = eventSearchField.getText().toLowerCase().trim();
        if (searchText.isEmpty()) {
            refreshEvents();
            return;
        }

        List<Events> filtered = organizer.getEvents().stream()
                .filter(event -> event.getTitle().toLowerCase().contains(searchText) ||
                        event.getCategory().toLowerCase().contains(searchText) ||
                        String.valueOf(event.getEventID()).contains(searchText))
                .collect(Collectors.toList());

        eventsGrid.getChildren().clear();
        int column = 0;
        int row = 0;
        for (Events event : filtered) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/EventCard.fxml"));
                AnchorPane card = loader.load();
                EventCardController controller = loader.getController();
                controller.setData(event, this::showEventDetailsPage);

                eventsGrid.add(card, column, row);
                column++;
                if (column == 3) {
                    column = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void showCreateEventDialog() {
        createEventForm.setVisible(true);
        newEventName.clear();
        newEventDate.setValue(null);
        newEventStartTime.getSelectionModel().clearSelection();
        newEventPrice.clear();
        newEventCapacity.clear();
        newEventCategory.getSelectionModel().clearSelection();
        newEventRoom.getSelectionModel().clearSelection();
    }

    @FXML
    private void cancelCreateEvent() {
        createEventForm.setVisible(false);
    }

    @FXML
    private void handleCreateEvent() {
        try {
            if (newEventName.getText().isEmpty() ||
                    newEventDate.getValue() == null ||
                    newEventPrice.getText().isEmpty() ||
                    newEventCapacity.getText().isEmpty() ||
                    newEventCategory.getValue() == null ||
                    newEventRoom.getValue() == null) {
                showAlert("Error", "Please fill in all fields");
                return;
            }

            double price = Double.parseDouble(newEventPrice.getText());
            int capacity = Integer.parseInt(newEventCapacity.getText());

            organizer.addEvent(newEventName.getText(),
                    newEventDate.getValue(),
                    price,
                    capacity,
                    newEventCategory.getValue(),
                    newEventRoom.getValue(),
                    organizer);

            refreshEvents();
            showAlert("Success", "Event created successfully.");
            createEventForm.setVisible(false);
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter valid numbers for price and capacity");
        } catch (Exception e) {
            showAlert("Error", "Failed to create event: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteEvent() {
        if (selectedEvent != null) {
            selectedEvent.GetAttendees().clear();
            Database.Categories.forEach(c -> c.DeleteEvent(selectedEvent));
            organizer.getEvents().remove(selectedEvent);
            Database.events.remove(selectedEvent);

            refreshEvents();
            clearEventDetails();
            deleteEventButton.setDisable(true);
            showAlert("Success", "Event deleted successfully.");
        } else {
            showAlert("No Selection", "Please select an event to delete.");
        }
    }



    @FXML
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    public void logOut(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("welcome-page.fxml"));
        root = loader.load();
        WelcomePageController controller = loader.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Event Management System");
        stage.show();
    }
    @FXML
    public void openProfileScene(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Organizer-profile.fxml"));
        Parent root = loader.load();

        OrganizerProfileController controller = loader.getController();
        controller.setOrganizer(this.organizer);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Profile settings");
        stage.show();
    }

}
