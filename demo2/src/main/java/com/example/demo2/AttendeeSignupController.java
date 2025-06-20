package com.example.demo2;

import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class AttendeeSignupController {
    @FXML
    TextField UserNameField;
    @FXML
    PasswordField passwordfield;
    @FXML
    DatePicker BirthDate;
    @FXML
    RadioButton MaleR;
    @FXML
    RadioButton FemaleR;
    @FXML
    RadioButton RR;
    @FXML
    TextField AddressField;
    @FXML
    private Label InvalidMessage;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    private static final int MAX_LENGTH = 18;
    private static final int PASSWORD_MIN_LENGTH = 10;

    public Gender getGender() {
        if (MaleR.isSelected()) {
            return Gender.Male;
        } else if (FemaleR.isSelected()) {
            return Gender.Female;
        } else {
            return Gender.Other;
        }
    }

    public void SignUp(ActionEvent event) throws IOException {
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


        // Simulate DB check (or just add directly for now)
        for(User u: Database.users) {
            if (u.getUsername().equals(UserNameField.getText().trim())) {
                InvalidMessage.setText("Account already created");
                return;
            }
        }
        Attendee atd = new Attendee(username, password, getGender(), BirthDate.getValue(), AddressField.getText());
        Database.users.add(atd);


        root = FXMLLoader.load(getClass().getResource("Welcome-page.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

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
    public void initialize() {
        InvalidMessage.setVisible(false);
        UserNameField.setPromptText("Max 18 characters");
        passwordfield.setPromptText("10–18 chars, mix of letters & digits");

        applyLengthLimit(UserNameField, MAX_LENGTH);
        applyLengthLimit(passwordfield, MAX_LENGTH);
    }
}
