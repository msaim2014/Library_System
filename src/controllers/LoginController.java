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

/**
 * Login Controller that handles the login function
 * If a user is part of the database, they will be redirected to the home page
 * If the user is not, an error message will show up
 * A user can register as long as the username is unique
 */
public class LoginController implements Initializable {

    @FXML private TextField userUsername;
    @FXML private TextField adminUsername;
    @FXML private TextField userPassword;
    @FXML private TextField adminPassword;
    @FXML private Button userSignin;
    @FXML private Button adminSignIn;
    @FXML private Button userRegister;
    @FXML private Button adminRegister;
    @FXML private Label userLoginError;

    Connection conn = null;
    PreparedStatement check = null;
    PreparedStatement insert = null;
    ResultSet resultSet = null;
    private Account account = Account.getInstance();

    /**
     * sending the correct account information to the view
     * if the login/register is correct
     * @param event the button that is pressed
     */
    public void handleButtonAction(MouseEvent event){
        if(event.getSource()==userSignin){
            if(userLogin().equals("Success")){
                try{
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.close();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Home.fxml"));
                    Parent root = (Parent) loader.load();
                    HomeController homeController = loader.getController();
                    homeController.setUserInfo(account);

                    Stage stage1 = new Stage();
                    stage1.setScene(new Scene(root));
                    stage1.resizableProperty().setValue(false);
                    stage1.show();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        
        if(event.getSource()==adminSignIn){
            if(adminLogin().equals("Success")){
                try{
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.close();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Home.fxml"));
                    Parent root = (Parent) loader.load();
                    HomeController homeController = loader.getController();
                    homeController.setUserInfo(account);

                    Stage stage1 = new Stage();
                    stage1.setScene(new Scene(root));
                    stage1.resizableProperty().setValue(false);
                    stage1.show();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }

        if(event.getSource()==userRegister){
            if(userRegister().equals("Success")){
                try{
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.close();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Home.fxml"));
                    Parent root = (Parent) loader.load();
                    HomeController homeController = loader.getController();
                    homeController.setUserInfo(account);

                    Stage stage1 = new Stage();
                    stage1.setScene(new Scene(root));
                    stage1.resizableProperty().setValue(false);
                    stage1.show();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        
        if(event.getSource()==adminRegister){
            if(adminRegister().equals("Success")){
                try{
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.close();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Home.fxml"));
                    Parent root = (Parent) loader.load();
                    HomeController homeController = loader.getController();
                    homeController.setUserInfo(account);

                    Stage stage1 = new Stage();
                    stage1.setScene(new Scene(root));
                    stage1.resizableProperty().setValue(false);
                    stage1.show();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    /**
     * connects to the database
     */
    public LoginController(){
        conn = ConnectDB.conDB();
        System.out.println("Connection:"+conn);
    }

    /**
     * checks if the user logging in is a real user
     * @return the status of the login
     */
    private String userLogin(){
        String username = userUsername.getText();
        String password = userPassword.getText();
        account.setUsername(username);
        account.setPass(password);
        account.setIsAdmin(0);

        String checkSql = "SELECT * FROM users WHERE username=? and password=?";
        try {
            check = conn.prepareStatement(checkSql);
            check.setString(1, username);
            check.setString(2, password);
            resultSet = check.executeQuery();

            if(!resultSet.next()){
                userLoginError.setTextFill(Color.RED);
                userLoginError.setText("Enter Correct Username/Password");
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

    /**
     * checks if the admin logging in is a real admin
     * @return the status of the login
     */
    private String adminLogin(){
        String username = adminUsername.getText();
        String password = adminPassword.getText();
        account.setUsername(username);
        account.setPass(password);
        account.setIsAdmin(1);

        String checkSql = "SELECT * FROM users WHERE username=? and password=?";
        try {
            check = conn.prepareStatement(checkSql);
            check.setString(1, username);
            check.setString(2, password);
            resultSet = check.executeQuery();

            if(!resultSet.next()){
                userLoginError.setTextFill(Color.RED);
                userLoginError.setText("Enter Correct Username/Password");
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

    /**
     * checks if the user registering is unique
     * @return the status of the login
     */
    private String userRegister(){
        String username = userUsername.getText();
        String password = userPassword.getText();
        account.setUsername(username);
        account.setPass(password);
        account.setIsAdmin(0);

        String checkSql = "SELECT * FROM users WHERE username=?";
        String registerSql = "INSERT INTO users (username, password) VALUES (?,?)";

        try {
            check = conn.prepareStatement(checkSql);
            check.setString(1, username);
            resultSet = check.executeQuery();

            if(!resultSet.next()){
                userLoginError.setTextFill(Color.GREEN);
                userLoginError.setText("You were registered successfully");
                System.err.println("Insert:Success");

                insert = conn.prepareStatement(registerSql);
                insert.setString(1, username);
                insert.setString(2, password);
                insert.executeUpdate();

                return "Success";
            }else{
                userLoginError.setTextFill(Color.RED);
                userLoginError.setText("Username already exists. Pleae choose another one");
                System.err.println("Username already exists. Pleae choose another one");
                return "Fail";
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return "Exception";
        }
    }

    /**
     * checks if the admin registering is unique
     * @return the status of the login
     */
    private String adminRegister(){
        String username = adminUsername.getText();
        String password = adminPassword.getText();
        account.setUsername(username);
        account.setPass(password);
        account.setIsAdmin(1);

        String checkSql = "SELECT * FROM users WHERE username=?";
        String registerSql = "INSERT INTO users (username, password, is_admin) VALUES (?,?,1)";

        try {
            check = conn.prepareStatement(checkSql);
            check.setString(1, username);
            resultSet = check.executeQuery();

            if(!resultSet.next()){
                userLoginError.setTextFill(Color.GREEN);
                userLoginError.setText("You were registered successfully");
                System.err.println("Insert:Success");

                insert = conn.prepareStatement(registerSql);
                insert.setString(1, username);
                insert.setString(2, password);
                insert.executeUpdate();

                return "Success";
            }else{
                userLoginError.setTextFill(Color.RED);
                userLoginError.setText("Username already exists. Pleae choose another one");
                System.err.println("Username already exists. Please choose another one");
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
