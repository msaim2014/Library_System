package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Displays information for the system
 *
 */
public class ItemsController implements Initializable {
    @FXML Label title;
    private String newTitle;

    public void setTitle(String title){
        this.newTitle = title;
        System.out.println(newTitle);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
