package home;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main Class that runs the application
 */
public class Main extends Application {

    private double x, y;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
        primaryStage.setTitle("Library System");
        primaryStage.setScene(new Scene(root));
        primaryStage.resizableProperty().setValue(false);

        //drag it here
        root.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });
        root.setOnMouseDragged(mouseEvent -> {
            primaryStage.setX(mouseEvent.getScreenX()-x);
            primaryStage.setY(mouseEvent.getScreenY()-y);
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
