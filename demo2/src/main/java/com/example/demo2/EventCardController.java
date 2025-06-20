// EventCardController.java
package com.example.demo2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

public class EventCardController {

    @FXML private Label eventTitle;
    @FXML private Label eventDate;
    @FXML private Label eventCategory;
    @FXML private AnchorPane cardPane;

    private Events event;

    public void setData(Events event, Consumer<Events> onClick) {
        this.event = event;
        eventTitle.setText(event.getTitle());

        // Format the datetime cleanly
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        eventDate.setText(event.getDateTime().format(formatter));
        eventCategory.setText(event.getCategory());

        // Click listener for the whole card
        cardPane.setOnMouseClicked(e -> {
            onClick.accept(event);
                    });
        }
}