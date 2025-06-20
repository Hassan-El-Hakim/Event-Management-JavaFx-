module com.example.demo2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.desktop;
    requires java.prefs;

    opens com.example.demo2 to javafx.fxml;
    exports com.example.demo2;
}