package com.example.demo2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDate;

public class ShowBalanceController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label showbalancelabel;
    private Attendee attendee;
    private Events event;
    String shownBalance;
    //    Attendee attendee=new Attendee("hnhfuefj","jndwudni", Gender.Female, LocalDate.parse("2011-01-01"),"jfmjnf");
//    Event event=new Event("lol",LocalDate.parse("2011-01-01"),70,300,"formal",new Room("kmi",800),new Organizer("jndjn","njdccici",LocalDate.parse("2011-01-01")));
//
    @FXML
    public void showBalance(){
        showbalancelabel.setText(""+attendee.getBalance());
    }
    @FXML
    public void backToAttendeeDashBoard(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo2/AttendeeDashboard.fxml"));
        Parent root = loader.load();
        AttendeeDashboardController controller = loader.getController();
        controller.setAttendee(this.attendee);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void setAttendee(Attendee attendee){
        this.attendee=attendee;
    }
    public void setEvent(Events event){
        this.event=event;
    }
}
