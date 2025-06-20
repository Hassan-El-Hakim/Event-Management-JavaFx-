package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class UserTypeController {
    @FXML
    Button AdminButton;
    @FXML
    Button OrganizerButton;
    @FXML
    Button AttendeeButton;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;


    @FXML
    public void AdminSignup(ActionEvent event)throws IOException{
        root = FXMLLoader.load(getClass().getResource("Admin-Signup.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    @FXML
    public void AttendeeSignup(ActionEvent event)throws IOException{
        root = FXMLLoader.load(getClass().getResource("Attendee-Signup.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void OrganizerSignup(ActionEvent event)throws IOException{
        root = FXMLLoader.load(getClass().getResource("Organizer-Signup.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
