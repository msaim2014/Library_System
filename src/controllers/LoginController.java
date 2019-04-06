package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
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

    private String name;

    Connection conn = null;
    PreparedStatement check = null;
    PreparedStatement insert = null;
    ResultSet resultSet = null;
    ResultSet resultSet2 = null;

    public void handleButtonAction(MouseEvent event){
        if(event.getSource()==userSignin){
            if(login().equals("Success")){
                try{
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.close();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Home.fxml"));
                    Parent root = (Parent) loader.load();
                    HomeController homeController = loader.getController();
                    homeController.setName(name);

                    Stage stage1 = new Stage();
                    stage1.setScene(new Scene(root));
                    stage1.show();

                   /* FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Home.fxml"));
                    Parent root = (Parent) loader.load();
                    HomeController homeController = loader.getController();
                    homeController.setName(name);

                    Scene scene = new Scene(FXMLLoader.load((getClass().getResource("/views/Home.fxml"))));
                    stage.setScene(scene);
                    stage.show();*/
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }

        if(event.getSource()==userRegister){
            if(register().equals("Success")){
                try{
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.close();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Home.fxml"));
                    Parent root = (Parent)loader.load();
                    HomeController homeController = loader.getController();
                    homeController.setName(name);
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
        System.out.println("Connection:"+conn);
    }

    private String login(){
        String email = userUsername.getText().toString();
        String password = userPassword.getText().toString();
        this.name = email;

        String checkSql = "SELECT * FROM userdb.users WHERE email=? and password=?";
        try {
            check = conn.prepareStatement(checkSql);
            check.setString(1,email);
            check.setString(2, password);
            resultSet = check.executeQuery();

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

    private String register(){
        String email = userUsername.getText().toString();
        String password = userPassword.getText().toString();
        this.name = email;

        String checkSql = "SELECT * FROM userdb.users WHERE email=? and password=?";
        String registerSql = "INSERT INTO userdb.users (email, password) VALUES (?,?)";
        try {
            check = conn.prepareStatement(checkSql);
            check.setString(1,email);
            check.setString(2, password);
            resultSet = check.executeQuery();

            if(!resultSet.next()){
                userLoginError.setTextFill(Color.GREEN);
                userLoginError.setText("Success");
                System.err.println("Insert:Success");
                insert = conn.prepareStatement(registerSql);
                insert.setString(1,email);
                insert.setString(2, password);
                insert.executeUpdate();
                return "Success";
            }else{
                userLoginError.setTextFill(Color.RED);
                userLoginError.setText("Email/Password Exists");
                System.err.println("Email/Password Exists");
                return "Fail";
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return "Exception";
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }
}
