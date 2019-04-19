package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.ConnectDB;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML private VBox prItems = null;

    //My Account
    @FXML private Button editButton;
    @FXML private Text myAccountUsername;
    @FXML private Text myAccountPassword;
    @FXML private TextField textUsername;
    @FXML private TextField textPassword;
    @FXML private Label accountChangeStatus;

    //overview
    @FXML private Label name;
    @FXML private Button checkOutButton;
    @FXML private Button returnButton;
    @FXML private Label additionLabel;
    @FXML private Button overViewButton;
    @FXML private Button myAccountButton;
    @FXML private Button manageBooksButton;
    @FXML private Pane overViewPane;
    @FXML private Pane myAccountPane;
    @FXML private TableView<ModelTable> table;
    @FXML private TableColumn<ModelTable, Integer> col_isbn;
    @FXML private TableColumn<ModelTable, String> col_title;
    @FXML private TableColumn<ModelTable, String> col_author;
    @FXML private TableColumn<ModelTable, String> col_genre;
    @FXML private TableColumn<ModelTable, Integer> col_numAvailable;

    //admin manage books
    @FXML private Pane manageBooksPane;
    @FXML private TextField addISBN;
    @FXML private TextField addTitle;
    @FXML private TextField addAuthor;
    @FXML private TextField addGenre;
    @FXML private TextField addNumAvailable;
    @FXML private TextField removeISBN;
    @FXML private TableView<ModelTable> manageTable;
    @FXML private TableColumn<ModelTable, Integer> col_isbn1;
    @FXML private TableColumn<ModelTable, String> col_title1;
    @FXML private TableColumn<ModelTable, String> col_author1;
    @FXML private TableColumn<ModelTable, String> col_genre1;
    @FXML private TableColumn<ModelTable, Integer> col_numAvailable1;
    @FXML private Button addBookButton;
    @FXML private Button removeBookButton;
    @FXML private Label bookStatus;

    private String userName;
    private String userPass;
    private Book book;
    Connection conn = null;
    ObservableList<ModelTable> observableList = FXCollections.observableArrayList();
    PreparedStatement statement = null;
    ResultSet res = null;

    private String isbn;
    private String title;
    private String author;
    private String genre;
    private String checkOut;
    private String checkIn;
    private String numAvailable;

    public HomeController(){
        conn = ConnectDB.conDB();
    }

    public void editAccount(){
        String name = textUsername.getText();
        String pass = textPassword.getText();
        if("".equals(name) || "".equals(pass)){
            accountChangeStatus.setText("Please Enter name and password");
            accountChangeStatus.setTextFill(Color.RED);
        }else{
            //name is the new username and userName is the old one
            String changeUserSql = "UPDATE userdb.users SET email='"+name+"', password='"+pass+ "' WHERE email='"+userName+"'";
            String changeTableSql = "RENAME TABLE userdb." + userName +" TO userdb." + name;
            try {
                statement=conn.prepareStatement(changeUserSql);
                statement.executeUpdate();
                statement=conn.prepareStatement(changeTableSql);
                statement.executeUpdate();
                accountChangeStatus.setText("Success!");
                accountChangeStatus.setTextFill(Color.GREEN);
                this.userName = name;
                this.userPass = pass;
                myAccountUsername.setText(userName);
                myAccountPassword.setText(userPass);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                accountChangeStatus.setText("Opps... password/username taken");
                accountChangeStatus.setTextFill(Color.RED);
            }

        }

    }

    public void addBook(){
        this.isbn = addISBN.getText();
        this.title = addTitle.getText();
        this.author = addAuthor.getText();
        this.genre = addGenre.getText();
        this.numAvailable = addNumAvailable.getText();

        String addBookSql = "Insert INTO userdb.books (isbn, title, author, genre, numAvailable) VALUES (?,?,?,?,?)";
        try {
            statement = conn.prepareStatement(addBookSql);
            statement.setString(1, isbn);
            statement.setString(2, title);
            statement.setString(3, author);
            statement.setString(4, genre);
            statement.setString(5, numAvailable);
            statement.executeUpdate();

            bookStatus.setText(title + " Added");
            bookStatus.setTextFill(Color.GREEN);
            updateBookAvailability();

        } catch (SQLException e) {
            bookStatus.setText("Missing Fields or Duplicate ISBN");
            bookStatus.setTextFill(Color.RED);
        }
        //manageTable.getItems().add(new ModelTable(isbn, title, author, genre, numAvailable));
        refreshTables();
    }

    public void removeBook(){
        this.isbn = removeISBN.getText();
        String removeBookSql = "DELETE FROM userdb.books WHERE isbn = " + isbn;
        try {
            statement = conn.prepareStatement(removeBookSql);
            int n = statement.executeUpdate();
            if(n==1){
                bookStatus.setText("Removed: " + isbn);
                bookStatus.setTextFill(Color.GREEN);
            }else{
                bookStatus.setText("Incorrect ISBN");
                bookStatus.setTextFill(Color.RED);
            }
        } catch (SQLException e) {
            bookStatus.setText("Incorrect ISBN");
            bookStatus.setTextFill(Color.RED);
        }
        refreshTables();
    }

    public void refreshTables(){
        String getBooksSql = "SELECT * FROM userdb.books";
        observableList.clear();
        try {
            ResultSet rs = conn.createStatement().executeQuery(getBooksSql);
            while(rs.next()){
                observableList.add(new ModelTable(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("genre"),
                        rs.getString("numAvailable")));

                col_isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
                col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
                col_author.setCellValueFactory(new PropertyValueFactory<>("author"));
                col_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
                col_numAvailable.setCellValueFactory(new PropertyValueFactory<>("numAvailable"));

                col_isbn1.setCellValueFactory(new PropertyValueFactory<>("isbn"));
                col_title1.setCellValueFactory(new PropertyValueFactory<>("title"));
                col_author1.setCellValueFactory(new PropertyValueFactory<>("author"));
                col_genre1.setCellValueFactory(new PropertyValueFactory<>("genre"));
                col_numAvailable1.setCellValueFactory(new PropertyValueFactory<>("numAvailable"));


                //calls all the getters and setters in ModelTable
                table.setItems(observableList);
                manageTable.setItems(observableList);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (IllegalStateException e){
            System.err.println(e.getMessage());
        }
    }


    public void checkOutBook(){
        String addBookSql = "Insert INTO userdb." + userName + "(isbn, title, author, genre, checkOut, checkIn) VALUES (?,?,?,?,?,?)";
        try {
            statement = conn.prepareStatement(addBookSql);
            statement.setString(1, isbn);
            statement.setString(2, title);
            statement.setString(3, author);
            statement.setString(4, genre);
            statement.setString(5, checkOut);
            statement.setString(6, checkIn);
            statement.executeUpdate();

            additionLabel.setText("Checked Out: " + title);
            additionLabel.setTextFill(Color.GREEN);
            updateBookAvailability();

        } catch (SQLException e) {
            additionLabel.setText("Already Checked Out: " + title);
            additionLabel.setTextFill(Color.RED);
        }
    }

    public void returnBook(){
        String returnBookSql = "DELETE FROM userdb." +userName+ " WHERE title='" +title+"'";
        try {
            statement = conn.prepareStatement(returnBookSql);
            statement.executeUpdate();
            additionLabel.setText("Returned: " + title);
            additionLabel.setTextFill(Color.GREEN);
        } catch (SQLException e) {
            additionLabel.setText("Oops... Something went WRONG");
            additionLabel.setTextFill(Color.RED);
        }
    }

    public boolean hasBook(){return true;}
    public void updateBookAvailability(){ }

    public void getSelected(MouseEvent event){
        ModelTable res = null;
        if(event.getSource()==table){
            res = table.getSelectionModel().getSelectedItem();
        }else if(event.getSource()==manageTable){
            res = manageTable.getSelectionModel().getSelectedItem();
        }

        //not working
        //book.setIsbn(res.getIsbn());
        //book.setTitle(res.getTitle());
        //book.setAuthor(res.getAuthor().toString());
        //book.setGenre(res.getGenre());
        //book.setNumAvailable(res.getNumAvailable());

        this.isbn = res.getIsbn();
        this.title = res.getTitle();
        this.author = res.getAuthor();
        this.genre = res.getGenre();

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,30);
        Date newDate = calendar.getTime();
        this.checkOut = sdf.format(date);
        this.checkIn = sdf.format(newDate);
        System.out.println(isbn);
        System.out.println(title);
        System.out.println(author);
        System.out.println(genre);
        System.out.println(checkOut);
        System.out.println(checkIn);
    }

    public void setUserInfo(String userName, String userPass){
        this.userName = userName;
        this.userPass = userPass;
        name.setText(userName);
        myAccountUsername.setText(userName);
        myAccountPassword.setText(userPass);
    }

    public void changePanel(MouseEvent event){
        if(event.getSource()==overViewButton){
            overViewPane.toFront();
        }
        if(event.getSource()==myAccountButton){
            myAccountPane.toFront();
        }
        if(event.getSource()==manageBooksButton){
            manageBooksPane.toFront();
        }
    }

    public void createTables(){
        String getBooksSql = "SELECT * FROM userdb.books";
        try {
            ResultSet rs = conn.createStatement().executeQuery(getBooksSql);
            while(rs.next()){
                observableList.add(new ModelTable(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("genre"),
                        rs.getString("numAvailable")));

                col_isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
                col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
                col_author.setCellValueFactory(new PropertyValueFactory<>("author"));
                col_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
                col_numAvailable.setCellValueFactory(new PropertyValueFactory<>("numAvailable"));

                col_isbn1.setCellValueFactory(new PropertyValueFactory<>("isbn"));
                col_title1.setCellValueFactory(new PropertyValueFactory<>("title"));
                col_author1.setCellValueFactory(new PropertyValueFactory<>("author"));
                col_genre1.setCellValueFactory(new PropertyValueFactory<>("genre"));
                col_numAvailable1.setCellValueFactory(new PropertyValueFactory<>("numAvailable"));


                //calls all the getters and setters in ModelTable
                table.setItems(observableList);
                manageTable.setItems(observableList);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (IllegalStateException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        overViewPane.toFront();
        createTables();
    }
}
