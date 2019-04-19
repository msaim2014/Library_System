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
    @FXML private TextField userUsername;
    @FXML private TextField userPassword;
    @FXML private Button userSignin;
    @FXML private Button userRegister;
    @FXML private Label userLoginError;

    private String name;
    private String password;

    Connection conn = null;
    PreparedStatement check = null;
    PreparedStatement insert = null;
    PreparedStatement insertTable = null;
    ResultSet resultSet = null;

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
                    homeController.setUserInfo(name, password);

                    Stage stage1 = new Stage();
                    stage1.setScene(new Scene(root));
                    stage1.show();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }

        if(event.getSource()==userRegister){
            if(createAccount().equals("Success")){
                try{
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.close();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Home.fxml"));
                    Parent root = (Parent) loader.load();
                    HomeController homeController = loader.getController();
                    homeController.setUserInfo(name,password);

                    Stage stage1 = new Stage();
                    stage1.setScene(new Scene(root));
                    stage1.show();
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
        this.password = password;

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

    private String createAccount(){
        String email = userUsername.getText().toString();
        String password = userPassword.getText().toString();
        this.name = email;
        this.password = password;

        String checkSql = "SELECT * FROM userdb.users WHERE email=? OR password=?";
        String registerSql = "INSERT INTO userdb.users (email, password) VALUES (?,?)";
        String createTableSql = "CREATE TABLE userdb."+email+ "" +
                                "( `isbn` INT NOT NULL," +
                                " `title` VARCHAR(45) NULL," +
                                " `author` VARCHAR(45) NULL," +
                                " `genre` VARCHAR(45) NULL," +
                                " `checkOut` VARCHAR(45) NULL," +
                                " `checkIn` VARCHAR(45) NULL," +
                                " PRIMARY KEY (`isbn`))";

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

                insertTable = conn.prepareStatement(createTableSql);
                insertTable.executeUpdate();

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
