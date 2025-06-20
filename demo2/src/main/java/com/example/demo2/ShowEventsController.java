package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ShowEventsController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Attendee attendee;
    @FXML
    private ListView<String> eventsListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Events e : Database.events) {
            String eventDetails = "Name: " + e.getTitle() + ", Category: " + e.getCategory();
            eventsListView.getItems().add(eventDetails);
        }

        // Handle single click on an event item
        eventsListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // single click
                int selectedIndex = eventsListView.getSelectionModel().getSelectedIndex();
                if (selectedIndex >= 0) {
                    Events selectedEvent = Database.events.get(selectedIndex);
                    try {
                        openEventPage(selectedEvent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private void openEventPage(Events event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EventPage.fxml"));
        Parent root = loader.load();

        EventPageController controller = loader.getController();
        controller.setAttendee(attendee);
        controller.updateEvent(event);

        stage = (Stage) eventsListView.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void setAttendee(Attendee attendee) {
        this.attendee = attendee;
    }
    @FXML
    public void backToAttendeeDashBoard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AttendeeDashboard.fxml"));
        Parent root = loader.load();
        AttendeeDashboardController controller = loader.getController();
        controller.setAttendee(this.attendee);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
