package com.example.demo2;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
//import javafx.scene.control.ComboBox;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;


import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    //        @FXML private ComboBox<String> ticketTypeBox;
    @FXML
    private Spinner<Integer> quantitySpinner;
    @FXML
    private Label messageLabel;
    @FXML
    private Label addCreditMassege;
    @FXML
    private Button AddCreditButton;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Attendee attendee;
    private Events event;

//    Attendee attendee=new Attendee("hnhfuefj","jndwudni",Gender.Female, LocalDate.parse("2011-01-01"),"jfmjnf");
//    Event event=new Event("lol",LocalDate.parse("2011-01-01"),70,300,"formal",new Room("kmi",800),new Organizer("jndjn","njdccici",LocalDate.parse("2011-01-01")));

    //        public void initialize() {
//            ticketTypeBox.getItems().addAll("Standard - $10", "VIP - $20", "Student - $7");
//
//        }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1));
    }

    @FXML
    public void handleBuyButton() {

//            String ticketType = ticketTypeBox.getValue();
        Integer quantity = quantitySpinner.getValue();

//            if (quantity==0 || ticketType == null) {
//                messageLabel.setText("Please complete all fields.");
//            } else {
        for (int i = 0; i < quantity; i++) {
            if (!attendee.BuyTickets(event)) {
                messageLabel.setText("Insufficient funds. Ticket: " + event.getTicketPrice() + "   Your balance: " + attendee.getBalance());
                addCreditMassege.setText("Would you like to add to yor balance");
                AddCreditButton.setOpacity(1);


            } else
                messageLabel.setText("Thank you, " + "! You bought " + quantity + " ticket(s).");
        }


    }
    public void setAttendee(Attendee attendee) {
        this.attendee = attendee;
    }
    public void setEvent(Events event) {
        this.event = event;
    }
    @FXML
    public void backToEventPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EventPage.fxml"));
        Parent root = loader.load();
        EventPageController controller = loader.getController();
        controller.setAttendee(this.attendee);
        controller.setEvent(this.event);
        controller.updateEvent(this.event);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void SwitchToAddCredit(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddCredit.fxml"));
        Parent root = loader.load();
        AddCreditController controller = loader.getController();
        controller.setAttendee(this.attendee);
        controller.setEvent(this.event);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


}
