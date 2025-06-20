package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminDashBoardController {

    @FXML private Button eventsBtn;
    @FXML private Button organizersBtn;
    @FXML private Button attendeesBtn;
    @FXML private Button settingsBtn;
    @FXML private Button addAdminBtn;
    @FXML private ListView<String> roomsListView;
    private Stage stage;
    private Scene scene;
    private Parent root;


    private Admin admin;

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    @FXML
    public void viewOrganizers(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("org.fxml"));
        root = loader.load();
        orgController controller = loader.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Organizers Management");
        stage.show();
        setActiveButton(organizersBtn);
    }

    @FXML
    public void viewAttendees(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("att.fxml"));
        root = loader.load();
        attController controller = loader.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Attendees Management");
        stage.show();
        setActiveButton(attendeesBtn);
    }


    private void displayRoomsInListView() {
        roomsListView.getItems().clear();
        for (Room room : Database.rooms) {
            String roomInfo = String.format("%s (Capacity: %d, Available: %s)",
                    room.getName(),
                    room.getCapacity(),
                    room.isAvailable() ? "Yes" : "No");
            roomsListView.getItems().add(roomInfo);
        }
    }
    @FXML
    private void viewAdmins(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view-admin.fxml"));
        root = loader.load();
        ViewAdminController controller = loader.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("View Admins");
        stage.show();
    }

    @FXML
    private void viewCategories(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("categoryy.fxml"));
        root = loader.load();
        CategoryController controller = loader.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("View Admins");
        stage.show();
    }
    @FXML
    private void viewEvents(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("viewEvents.fxml"));
        root = loader.load();
        eventController controller = loader.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Events Management");
        stage.show();
    }
    @FXML
    public void viewAdminProfile(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin-profile.fxml"));
        root = loader.load();
        AdminProfileController controller = loader.getController();
        controller.setAdmin(admin);
        controller.loadAdmin();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Admin Profile");
        stage.show();

    }

    @FXML
    private void viewRooms(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("rooms.fxml"));
        root = loader.load();
        RoomsController controller = loader.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Rooms Management");
        stage.show();
    }

    @FXML
    public void viewSettings(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("settings.fxml"));
        root = loader.load();
        SettingsController controller = loader.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("System Settings");
        stage.show();
        setActiveButton(settingsBtn);
    }

    @FXML
    public void addAdmin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("add-admin.fxml"));
        root = loader.load();
        AddAdminController controller = loader.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Add New Admin");
        stage.show();
        setActiveButton(addAdminBtn);
    }

    @FXML
    public void logOut(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("welcome-page.fxml"));
        root = loader.load();
        WelcomePageController controller = loader.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    /*private void navigateToScene(String fxmlFile, String title, boolean addToHistory) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        root = loader.load();
        AdminDashBoardController controller = loader.getController();
        controller.setAdmin(admin);
    }*/

    private void setActiveButton(Button activeButton) {
        Button[] buttons = {eventsBtn, organizersBtn, attendeesBtn, settingsBtn, addAdminBtn};
        for (Button btn : buttons) {
            if (btn != null) {
                btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #2c3e50;");
            }
        }
        if (activeButton != null) {
            activeButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        }
    }

    private void showErrorAlert(String title, String message, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(message);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        TextArea textArea = new TextArea(sw.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);

        alert.getDialogPane().setExpandableContent(new VBox(new Label("Stack Trace:"), textArea));
        alert.showAndWait();
    }
}