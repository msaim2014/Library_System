package home;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private VBox prItems = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Node [] nodes = new Node[20];
        for(int i = 0; i<nodes.length; i++){
            try {
                nodes[i] = FXMLLoader.load(getClass().getResource("Items.fxml"));
                prItems.getChildren().add(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
