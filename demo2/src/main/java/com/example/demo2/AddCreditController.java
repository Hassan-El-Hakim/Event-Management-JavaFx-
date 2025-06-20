package com.example.demo2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.awt.*;
import java.awt.event.ActionEvent;
import javafx.scene.control.Button;
import java.io.IOException;
import java.time.LocalDate;
import javafx.scene.control.TextField;

public class AddCreditController {
    @FXML
    private TextField AmountDataField ;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label CreditLabel;
    @FXML
    private Button BackToBuyTicket ;
    double newAmount;
    private Attendee attendee;
    private Events event;

    public void setAttendee(Attendee attendee){
        this.attendee=attendee;
    }
    public void setEvent(Events event){
        this.event=event;
    }
    public void SwitchToBuyPageButton(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BuyTicket.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.setAttendee(attendee);
        controller.setEvent(event);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


    public void AddAmount(javafx.event.ActionEvent actionEvent) {
        try {
            if(Double.parseDouble(AmountDataField.getText())>=0) {
                newAmount = attendee.getBalance() + Double.parseDouble(AmountDataField.getText());
                attendee.setBalance(newAmount);
                CreditLabel.setText("New Balance :" + attendee.getBalance());
            }
            else
                CreditLabel.setText("Amount cant be negative");
        } catch (NumberFormatException e) {
            CreditLabel.setText("Please enter numbers only");


        }
    }

}
