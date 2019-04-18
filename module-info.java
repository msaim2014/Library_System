module Library.System {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    opens home;
    opens controllers;
    opens views;
}