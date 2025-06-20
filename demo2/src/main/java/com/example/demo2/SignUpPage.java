package com.example.demo2;

import com.dlsc.formsfx.model.structure.PasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SignUpPage implements Initializable {
    @FXML
    private TextField UserNameField;

    @FXML
    private Stage satge;

    @FXML
    private PasswordField passwordfield;

    @FXML
    private DatePicker BirthDate;

    @FXML
    private BorderPane rootBorderPane;

    @FXML
    private AnchorPane leftAnchorPane;

    @FXML
    private StackPane LeftStack;

    @FXML
    private ImageView BackgroundImage;

    @FXML
    private ImageView Logo; // If you also want to control the small logo



    private Stage stage;
    private Scene scene;
    private Parent root;

    public void SignUp(ActionEvent event) throws IOException {
        String name= UserNameField.getText();
        String password = passwordfield.toString();
        LocalDate date = BirthDate.getValue();

    }

    public void setStage(Stage stage) {
        Scene scene = new Scene(rootBorderPane); // root is your BorderPane
        stage.setScene(scene);
    }


    /*@FXML
    public void initialize() {
        // Bind background image size to the pane
        BackgroundImage.fitWidthProperty().bind(leftAnchorPane.widthProperty());
        BackgroundImage.fitHeightProperty().bind(leftAnchorPane.heightProperty());
        //Logo.fitWidthProperty().bind(leftAnchorPane.widthProperty());
        //Logo.fitHeightProperty().bind(leftAnchorPane.heightProperty());
        Logo.layoutXProperty().bind(leftAnchorPane.widthProperty().subtract(Logo.fitWidthProperty()).divide(2));
        Logo.layoutYProperty().bind(leftAnchorPane.heightProperty().subtract(Logo.fitHeightProperty()).divide(2));

        // Center the logo within the StackPane
        Logo.layoutXProperty().bind(LeftStack.widthProperty().subtract(Logo.fitWidthProperty()).divide(2));
        Logo.layoutYProperty().bind(LeftStack.heightProperty().subtract(Logo.fitHeightProperty()).divide(2));



    }*/

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Bind background image size to the pane
        BackgroundImage.fitWidthProperty().bind(leftAnchorPane.widthProperty());
        BackgroundImage.fitHeightProperty().bind(leftAnchorPane.heightProperty());
        //Logo.fitWidthProperty().bind(leftAnchorPane.widthProperty());
        //Logo.fitHeightProperty().bind(leftAnchorPane.heightProperty());
        Logo.layoutXProperty().bind(leftAnchorPane.widthProperty().subtract(Logo.fitWidthProperty()).divide(2));
        Logo.layoutYProperty().bind(leftAnchorPane.heightProperty().subtract(Logo.fitHeightProperty()).divide(2));

        // Center the logo within the StackPane
        Logo.layoutXProperty().bind(LeftStack.widthProperty().subtract(Logo.fitWidthProperty()).divide(2));
        Logo.layoutYProperty().bind(LeftStack.heightProperty().subtract(Logo.fitHeightProperty()).divide(2));

        //rootBorderPane.
    }
}
