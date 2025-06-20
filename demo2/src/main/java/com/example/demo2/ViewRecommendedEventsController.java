package com.example.demo2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;

public class ViewRecommendedEventsController {
    @FXML
    private ListView<String> recommendedListView;

    private Attendee attendee;
    private Stage stage;
    private Scene scene;
    private Events selectedEvent;
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

    public void setAttendee(Attendee attendee) {
        this.attendee = attendee;
        loadRecommendedEvents();
    }
    public void setEvent(Events event) {
        this.selectedEvent=event;
    }

    private void loadRecommendedEvents() {

        for (Events e : Database.events) {
            for (String interest : attendee.getInterests()) {
                if (e.getCategory().equals(interest)) {
                    recommendedListView.getItems().add(
                            e.getTitle() + " | " + e.getCategory() + " | Price: " +
                                    e.getTicketPrice());
                    recommendedListView.refresh();

                }
            }

        }
    }
    @FXML
    public void initialize() {
        recommendedListView.setOnMouseClicked(event -> {
            String selectedItem = recommendedListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                try {
                    openEventPage(selectedItem);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void openEventPage(String eventString) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EventPage.fxml"));
        Parent root = loader.load();
        EventPageController controller = loader.getController();

        selectedEvent = findEventByString(eventString);
        controller.updateEvent(selectedEvent);
        controller.setEvent(selectedEvent);
        controller.setAttendee(this.attendee);
        Stage stage = (Stage) recommendedListView.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public Events findEventByString(String eventString) {
        for (Events e : Database.events) {
            if (eventString.contains(e.getTitle())) {
                return e;
            }
        }
        return null;
    }

}

