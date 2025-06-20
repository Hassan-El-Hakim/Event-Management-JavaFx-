package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;

public class WelcomePageController {

    @FXML
    private Button LoginButton, SignUpButton;
    @FXML
    private AnchorPane leftAnchorPane;
    @FXML
    private ImageView BackgroundImage, Logo;
    @FXML
    private StackPane LeftStack;
    @FXML
    private BorderPane rootBorderPane;
    @FXML
    private TextField UserNameField;
    @FXML
    private PasswordField passwordfield;
    @FXML
    private Label InvalidMessage;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private static final int MAX_LENGTH = 18;
    private static final int PASSWORD_MIN_LENGTH = 10;

    @FXML
    public void initialize() {
        InvalidMessage.setVisible(false);
        UserNameField.setPromptText("Max 18 characters");
        passwordfield.setPromptText("10–18 chars, mix of letters & digits");

        applyLengthLimit(UserNameField, MAX_LENGTH);
        applyLengthLimit(passwordfield, MAX_LENGTH);
    }

@FXML
    private void applyLengthLimit(TextInputControl field, int maxLength) {
        field.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.getControlNewText().length() <= maxLength) {
                return change;
            } else {
                InvalidMessage.setVisible(true);
                InvalidMessage.setText("Max characters");
                return null;
            }
        }));
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{10,18}$");
    }

    @FXML
    public void Login(ActionEvent event) throws IOException {
        InvalidMessage.setVisible(true);

        String username = UserNameField.getText().trim();
        String password = passwordfield.getText().trim();

        if (password.length() < PASSWORD_MIN_LENGTH) {
            InvalidMessage.setText("Password must be more than 10 characters");
            return;
        }

        if (!isValidPassword(password)) {
            InvalidMessage.setText("Password must contain letters and numbers");
            return;
        }

        for (User user : Database.users) {
            if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                if (user instanceof Organizer) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Organizer-DashBoard.fxml"));
                    root = loader.load();
                    OrganizerDashBoardController controller = loader.getController();
                    controller.setOrganizer((Organizer) user);
                } else if (user instanceof Admin) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin-DashBoard.fxml"));
                    root = loader.load();
                    AdminDashBoardController controller = loader.getController();
                    controller.setAdmin((Admin) user);
                } else if (user instanceof Attendee) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("AttendeeDashboard.fxml"));
                    root = loader.load();
                    AttendeeDashboardController controller = loader.getController();
                    controller.setAttendee((Attendee) user);
                }

                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                return;
            }
        }

        InvalidMessage.setText("Invalid Details!");
    }

    @FXML
    public void SwitchToChoosetUser(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Choose-User-Type.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}