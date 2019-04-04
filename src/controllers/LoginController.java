package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utils.ConnectDB;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField userUsername;
    @FXML
    private TextField userPassword;
    @FXML
    private Button userSignin;
    @FXML
    private Button userRegister;
    @FXML
    private Label userLoginError;

    Connection conn = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public void handleButtonAction(MouseEvent event){
        if(event.getSource()==userSignin){
            if(login().equals("Success")){
                try{
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.close();

                    Scene scene = new Scene(FXMLLoader.load((getClass().getResource("/views/Home.fxml"))));
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    public LoginController(){
        conn = ConnectDB.conDB();
        System.out.println(conn);
    }

    private String login(){
        String email = userUsername.getText().toString();
        String password = userPassword.getText().toString();

        String sql = "SELECT * FROM userdb.users WHERE email=? and password=?";

        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            //
            if(!resultSet.next()){
                userLoginError.setTextFill(Color.RED);
                userLoginError.setText("Enter Correct Email/Password");
                System.err.println("Incorrect views");
                return "Fail";
            }else{
                userLoginError.setTextFill(Color.GREEN);
                userLoginError.setText("Success!");
                System.err.println("Successful views");
                return "Success";
            }


        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return "Exception";
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
