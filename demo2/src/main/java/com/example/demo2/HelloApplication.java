package com.example.demo2;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.prefs.Preferences;

public class HelloApplication extends Application {
    private static Stage primaryStage;
    private static String currentTheme = "Light";
    private static final Preferences prefs = Preferences.userNodeForPackage(HelloApplication.class);

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        currentTheme = prefs.get("currentTheme", "Light");

        Parent root = FXMLLoader.load(getClass().getResource("Welcome-page.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Event Management System");
        Image icon = new Image(getClass().getResourceAsStream("Images/app logo2.png"));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        //stage.setFullScreen(false);
        //stage.setResizable(false);
        stage.show();
    }

    private static String getCurrentFxmlPath() {
        if (primaryStage.getScene() != null && primaryStage.getScene().getRoot() != null) {
            Object userData = primaryStage.getScene().getRoot().getUserData();
            if (userData instanceof String) {
                return (String) userData;
            }
        }
        return "Welcome-page.fxml";
    }

    private static void applyCurrentTheme(Parent root, String fxmlFile, FXMLLoader loader) {
        String backgroundColor = currentTheme.equals("Dark") ? "#2c3e50" : "#f5f7fa";
        String textColor = currentTheme.equals("Dark") ? "#ecf0f1" : "#2c3e50";
        String listViewBackground = currentTheme.equals("Dark") ? "#34495e" : "#ffffff";
        String borderColor = currentTheme.equals("Dark") ? "#003366" : "#f4c2c2";

        Button backButton = null;
        if (fxmlFile.equals("settings.fxml")) {
            SettingsController controller = loader.getController();
            if (controller != null) {
                backButton = controller.getBackButton();
            }
        }

        applyStylesToNode(root, backgroundColor, textColor, listViewBackground, borderColor, backButton);
    }

    private static void applyStylesToNode(Node node, String backgroundColor, String textColor,
                                          String listViewBackground, String borderColor, Button backButton) {
        if (node instanceof BorderPane) {
            node.setStyle("-fx-background-color: " + backgroundColor + ";");
        } else if (node instanceof VBox || node instanceof HBox) {
            String existingStyle = node.getStyle();
            if (existingStyle != null && existingStyle.contains("-fx-background-color: white")) {
                node.setStyle("-fx-background-color: " + listViewBackground + "; -fx-padding: 15; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");
            } else if (existingStyle != null && existingStyle.contains("-fx-background-color: #2c3e50")) {
                node.setStyle("-fx-background-color: " + backgroundColor + "; -fx-padding: 15;");
            } else {
                node.setStyle("-fx-background-color: " + backgroundColor + "; -fx-padding: 15;");
            }
        } else if (node instanceof Label) {
            node.setStyle("-fx-text-fill: " + textColor + ";");
        } else if (node instanceof ListView) {
            node.setStyle("-fx-background-color: " + listViewBackground + ";");
        } else if (node instanceof TextField) {
            node.setStyle("-fx-background-color: " + listViewBackground + "; -fx-text-fill: " + textColor + "; -fx-border-color: " + borderColor + " transparent " + borderColor + " transparent; -fx-border-width: 4; -fx-border-radius: 4; -fx-border-style: solid outside; -fx-background-insets: 0; -fx-border-insets: 0;");
        } else if (node instanceof ComboBox) {
            node.setStyle("-fx-background-color: " + listViewBackground + "; -fx-text-fill: " + textColor + "; -fx-border-color: " + borderColor + " transparent " + borderColor + " transparent; -fx-border-width: 4; -fx-border-radius: 4; -fx-border-style: solid outside; -fx-background-insets: 0; -fx-border-insets: 0;");
        } else if (node instanceof Button && node == backButton) {
            node.setStyle("-fx-background-color: #3498db; -fx-text-fill: #ffffff; -fx-background-radius: 4; -fx-padding: 8 16 8 16;");
        }

        if (node instanceof Parent) {
            for (Node child : ((Parent) node).getChildrenUnmodifiable()) {
                applyStylesToNode(child, backgroundColor, textColor, listViewBackground, borderColor, backButton);
            }
        }
    }

    public static void setCurrentTheme(String theme) {
        currentTheme = theme;
        prefs.put("currentTheme", theme);

        if (primaryStage.getScene() != null) {
            String currentFxml = getCurrentFxmlPath();
            try {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(currentFxml));
                Parent root = loader.load();
                root.setUserData(currentFxml);
                applyCurrentTheme(root, currentFxml, loader);
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
            } catch (IOException e) {
                System.err.println("Failed to reapply theme: " + e.getMessage());
            }
        }
    }

    public static String getCurrentTheme() {
        return currentTheme;
    }

    public static void main(String[] args) {
        launch();
    }
}