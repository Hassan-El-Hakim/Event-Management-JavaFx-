package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SettingsController {

    @FXML private ComboBox<String> themeComboBox;
    @FXML private Button backButton;
    @FXML private TextField testField;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void initialize() {
        themeComboBox.getItems().addAll("Light", "Dark");
        themeComboBox.setValue(HelloApplication.getCurrentTheme());
        if (testField != null) {
            testField.setPromptText("Test Input");
        }
        applyTheme(HelloApplication.getCurrentTheme());
    }

    @FXML
    public void goBack(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin-DashBoard.fxml"));
        root = loader.load();
        AdminDashBoardController controller = loader.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Admin DashBoard");
        stage.show();
    }

    @FXML
    public void saveSettings(ActionEvent event) {
        try {
            String theme = themeComboBox.getValue();
            HelloApplication.setCurrentTheme(theme);
            applyTheme(theme);
            showAlert("Settings Saved", "Theme changed to: " + theme);
        } catch (Exception e) {
            showAlert("Save Failed", e.getMessage());
        }
    }

    private void applyTheme(String theme) {
        Scene currentScene = themeComboBox.getScene();
        if (currentScene != null) {
            Parent root = currentScene.getRoot();
            applyInlineStyles(root, theme);

            String borderColor = theme.equals("Dark") ? "#003366" : "#f4c2c2";
            if (themeComboBox != null) {
                themeComboBox.setStyle("-fx-background-color: " + (theme.equals("Dark") ? "#34495e" : "#ffffff") + "; -fx-text-fill: " + (theme.equals("Dark") ? "#ecf0f1" : "#2c3e50") + "; -fx-border-color: " + borderColor + " transparent " + borderColor + " transparent; -fx-border-width: 4; -fx-border-radius: 4; -fx-border-style: solid outside; -fx-background-insets: 0; -fx-border-insets: 0;");
            }
            if (testField != null) {
                testField.setStyle("-fx-background-color: " + (theme.equals("Dark") ? "#34495e" : "#ffffff") + "; -fx-text-fill: " + (theme.equals("Dark") ? "#ecf0f1" : "#2c3e50") + "; -fx-border-color: " + borderColor + " transparent " + borderColor + " transparent; -fx-border-width: 4; -fx-border-radius: 4; -fx-border-style: solid outside; -fx-background-insets: 0; -fx-border-insets: 0;");
            }
        }
    }

    private void applyInlineStyles(Parent root, String theme) {

        String backgroundColor = theme.equals("Dark") ? "#2c3e50" : "#f5f7fa";
        String textColor = theme.equals("Dark") ? "#ecf0f1" : "#2c3e50";
        String listViewBackground = theme.equals("Dark") ? "#34495e" : "#ffffff";
        String borderColor = theme.equals("Dark") ? "#003366" : "#f4c2c2";


        applyStylesToNode(root, theme, backgroundColor, textColor, listViewBackground, borderColor);
    }

    private void applyStylesToNode(Node node, String theme, String backgroundColor, String textColor,
                                   String listViewBackground, String borderColor) {

        if (node instanceof BorderPane) {
            node.setStyle("-fx-background-color: " + backgroundColor + ";");
        } else if (node instanceof VBox || node instanceof HBox) {
            String existingStyle = node.getStyle();
            if (existingStyle != null && existingStyle.contains("-fx-background-color: white")) {
                node.setStyle("-fx-background-color: " + listViewBackground + "; -fx-padding: 20; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");
            } else if (existingStyle != null && existingStyle.contains("-fx-background-color: #2c3e50")) {
                node.setStyle("-fx-background-color: " + backgroundColor + "; -fx-padding: 15;");
            } else {
                node.setStyle("-fx-background-color: " + backgroundColor + "; -fx-padding: 15;");
            }
        } else if (node instanceof Label) {
            node.setStyle("-fx-text-fill: " + textColor + ";");
        } else if (node instanceof ListView) {
            node.setStyle("-fx-background-color: " + listViewBackground + ";");
        } else if (node instanceof TextField && node != testField) {
            node.setStyle("-fx-background-color: " + listViewBackground + "; -fx-text-fill: " + textColor + "; -fx-border-color: " + borderColor + " transparent " + borderColor + " transparent; -fx-border-width: 4; -fx-border-radius: 4; -fx-border-style: solid outside; -fx-background-insets: 0; -fx-border-insets: 0;");
        } else if (node instanceof ComboBox && node != themeComboBox) {
            node.setStyle("-fx-background-color: " + listViewBackground + "; -fx-text-fill: " + textColor + "; -fx-border-color: " + borderColor + " transparent " + borderColor + " transparent; -fx-border-width: 4; -fx-border-radius: 4; -fx-border-style: solid outside; -fx-background-insets: 0; -fx-border-insets: 0;");
        } else if (node instanceof Button && node == backButton) {
            node.setStyle("-fx-background-color: #3498db; -fx-text-fill: #ffffff; -fx-background-radius: 4; -fx-padding: 8 16 8 16;");
        }


        if (node instanceof Parent) {
            for (Node child : ((Parent) node).getChildrenUnmodifiable()) {
                applyStylesToNode(child, theme, backgroundColor, textColor, listViewBackground, borderColor);
            }
        }
    }

    public Button getBackButton() {
        return backButton;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}