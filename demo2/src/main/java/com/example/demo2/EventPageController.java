package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.sound.sampled.*;

import java.io.File;
import java.io.IOException;

public class EventPageController {
    private Attendee attendee;
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;
    String imagePath;

    @FXML
    private Label nameOfEvent;
    @FXML
    private Label eventDate;
    @FXML
    private Label eventID;
    @FXML
    private Label eventRoom;
    @FXML
    private Label eventCategory;
    @FXML
    private ImageView eventImage;
    private Clip clip;

    private Events event;
    public void updateEvent(Events event) {
        this.event = event;
        nameOfEvent.setText("Name: " + event.getTitle());
        eventDate.setText("Date" + event.getDateTime().toString());
        eventID.setText("Event ID" + String.valueOf(event.getEventID()));
        eventCategory.setText("Category: " + event.getCategory());
        eventRoom.setText(event.getRoom().toString());
        String category = event.getCategory().toLowerCase();


        File file = new File(getClass().getResource("sunsetz.wav").getFile());
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }
        catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }


        switch (category) {
            case "music":
                imagePath = "Images/music.icon.jpeg";
                break;
            case "sport":
                imagePath = "Images/sports.icon.jpeg";
                break;
            case "technology":
                imagePath = "Images/tech.icon.jpeg";
                break;
            case "art":
                imagePath = "Images/art.icon.jpeg";
        }
        eventImage.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream(imagePath)));
    }
    @FXML
    public void backToViewRecommendedEvents(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewRecommendedEvents.fxml"));
        Parent root = loader.load();
        ViewRecommendedEventsController controller = loader.getController();
        controller.setAttendee(this.attendee);
        controller.setEvent(this.event);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        clip.close();
    }
    public void setAttendee(Attendee attendee) {
        this.attendee = attendee;
    }
    public void setEvent(Events event){
        this.event = event;
    }
    public void openBuyTicketPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BuyTicket.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.setAttendee(this.attendee);
        controller.setEvent(this.event);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        clip.close();
    }

}

