module Library.System {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires org.junit.jupiter.api;
    opens home;
    opens controllers;
    opens views;
}