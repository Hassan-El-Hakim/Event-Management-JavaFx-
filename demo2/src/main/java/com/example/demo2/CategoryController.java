package com.example.demo2;

import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CategoryController {
    @FXML
    private ListView<Category> categoriesListView;
    private ObservableList<Category> categories = FXCollections.observableArrayList();
    private Stage stage;
    private Scene scene;
    private Parent root;

    public CategoryController() {
    }

    @FXML
    public void initialize() {
        this.categories.setAll(Database.Categories);
        this.categoriesListView.setItems(this.categories);
        this.categoriesListView.setCellFactory((param) -> new ListCell<Category>() {
            protected void updateItem(Category category, boolean empty) {
                super.updateItem(category, empty);
                this.setText(!empty && category != null ? category.toString() : null);
            }
        });
    }

    @FXML
    private void showAddCategoryDialog(ActionEvent event) {
        Dialog<Category> dialog = new Dialog();
        dialog.setTitle("Add New Category");
        ButtonType addButton = new ButtonType("Add", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(new ButtonType[]{addButton, ButtonType.CANCEL});
        GridPane grid = new GridPane();
        grid.setHgap(10.0);
        grid.setVgap(10.0);
        grid.setPadding(new Insets(20.0));
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField idField = new TextField();
        idField.setPromptText("ID");
        TextField descField = new TextField();
        descField.setPromptText("Description");
        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("ID:"), 0, 1);
        grid.add(idField, 1, 1);
        grid.add(new Label("Description:"), 0, 2);
        grid.add(descField, 1, 2);
        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter((button) -> {
            if (button == addButton) {
                try {
                    return new Category(nameField.getText().trim(), Integer.parseInt(idField.getText().trim()), descField.getText().trim());
                } catch (NumberFormatException var7) {
                    this.showAlert("Invalid ID", "ID must be a number");
                    return null;
                }
            } else {
                return null;
            }
        });
        Optional<Category> result = dialog.showAndWait();
        result.ifPresent((category) -> {
            Database.Categories.add(category);
            this.categories.setAll(Database.Categories);
        });
    }

    @FXML
    private void deleteCategory(ActionEvent event) {
        Category selected = this.categoriesListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirm = new Alert(AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Delete");
            confirm.setHeaderText("Delete Category");
            confirm.setContentText("Delete " + selected.getName() + "? All associated events will have their category set to null.");
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Remove category from all events
                for (Events e : Database.events) {
                    if (e.getCategory() != null && e.getCategory().equals(selected.getName())) {
                        e.setCategory(null);
                    }
                }
                // Remove category from Database.Categories
                Database.Categories.remove(selected);
                this.categories.setAll(Database.Categories);
            }
        } else {
            this.showAlert("No Selection", "Please select a category to delete");
        }
    }

    @FXML
    private void goBack(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Admin-DashBoard.fxml"));
        this.root = (Parent)loader.load();
        AdminDashBoardController controller = (AdminDashBoardController)loader.getController();
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(this.root);
        this.stage.setScene(this.scene);
        this.stage.setTitle("Admin DashBoard");
        this.stage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}