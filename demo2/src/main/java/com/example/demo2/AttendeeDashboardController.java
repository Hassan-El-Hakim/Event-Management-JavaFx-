package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AttendeeDashboardController implements Initializable {
    @FXML
    private Button logOutBt;
    @FXML
    private ListView<String> interestsListView;

    private List<String> selectedInterst;

    @FXML
    private TextField firstInterest;
    private Attendee attendee;
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    public void openRecommendedEvents(javafx.event.ActionEvent event) throws IOException {
        List<String> selectedInterests = interestsListView.getSelectionModel().getSelectedItems();
        for (String interest : selectedInterests) {
            if (!attendee.getInterests().contains(interest.toLowerCase())) {
                attendee.getInterests().add(interest.toLowerCase());
            }
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewRecommendedEvents.fxml"));
        Parent root = loader.load();
        ViewRecommendedEventsController controller = loader.getController();
        controller.setAttendee(this.attendee);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        interestsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        for (Category c : Database.Categories) {
            interestsListView.getItems().add(c.getName());
        }
        // Listen to clicks on the ListView
        interestsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                String interest = newVal.toLowerCase();
                if (!attendee.getInterests().contains(interest)) {
                    attendee.getInterests().add(interest);
                }
            }
        });
    }
    public void setAttendee(Attendee attendee) {
        this.attendee = attendee;
    }
    public void openWalletBalance(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowBalance.fxml"));
        Parent root = loader.load();
        ShowBalanceController controller = loader.getController();
        controller.setAttendee(this.attendee);
        controller.showBalance();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    public void openHomePage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HelloView.fxml"));
        Parent root = loader.load();
        HelloController controller = loader.getController();
        controller.setAttendee(this.attendee);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void logOut(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Welcome-page.fxml"));
        Parent root = loader.load();
        WelcomePageController controller = loader.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void openShowEvents(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowEvents.fxml"));
        Parent root = loader.load();

        ShowEventsController controller = loader.getController();
        controller.setAttendee(this.attendee);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
