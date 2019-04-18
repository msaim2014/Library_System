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
    @FXML private TableColumn<ModelTable, Integer> col_availability;

    //admin manage books
    @FXML private Pane manageBooksPane;
    @FXML private TextField addISBN;
    @FXML private TextField addTitle;
    @FXML private TextField addAuthor;
    @FXML private TextField addGenre;
    @FXML private TextField addAvailability;
    @FXML private TextField removeISBN;
    @FXML private TableView<ModelTable> manageTable;
    @FXML private TableColumn<ModelTable, Integer> col_isbn1;
    @FXML private TableColumn<ModelTable, String> col_title1;
    @FXML private TableColumn<ModelTable, String> col_author1;
    @FXML private TableColumn<ModelTable, String> col_genre1;
    @FXML private TableColumn<ModelTable, Integer> col_availability1;
    @FXML private Button addBookButton;
    @FXML private Button removeBookButton;
    @FXML private Label bookStatus;

    private String userName;
    private String userID;
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
    private String availability;

    public HomeController(){
        conn = ConnectDB.conDB();
    }

    public void addBook(){
        this.isbn = addISBN.getText();
        this.title = addTitle.getText();
        this.author = addAuthor.getText();
        this.genre = addGenre.getText();
        this.availability = addAvailability.getText();

        String addBookSql = "Insert INTO books (isbn, title, author, genre, availability) VALUES (?,?,?,?,?)";
        try {
            statement = conn.prepareStatement(addBookSql);
            statement.setString(1, isbn);
            statement.setString(2, title);
            statement.setString(3, author);
            statement.setString(4, genre);
            statement.setString(5, availability);
            statement.executeUpdate();

            bookStatus.setText(title + " Added");
            bookStatus.setTextFill(Color.GREEN);
            updateBookAvailability("increase");

        } catch (SQLException e) {
            bookStatus.setText("Missing Fields or Duplicate ISBN");
            bookStatus.setTextFill(Color.RED);
        }
        //manageTable.getItems().add(new ModelTable(isbn, title, author, genre, availability));
        refreshTables();
    }

    public void removeBook(){
        this.isbn = removeISBN.getText();
        String removeBookSql = "DELETE FROM books WHERE isbn = " + isbn;
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
        String getBooksSql = "SELECT * FROM books";
        observableList.clear();
        try {
            ResultSet rs = conn.createStatement().executeQuery(getBooksSql);
            while(rs.next()){
                observableList.add(new ModelTable(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("genre"),
                        rs.getString("availability")));

                col_isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
                col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
                col_author.setCellValueFactory(new PropertyValueFactory<>("author"));
                col_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
                col_availability.setCellValueFactory(new PropertyValueFactory<>("availability"));

                col_isbn1.setCellValueFactory(new PropertyValueFactory<>("isbn"));
                col_title1.setCellValueFactory(new PropertyValueFactory<>("title"));
                col_author1.setCellValueFactory(new PropertyValueFactory<>("author"));
                col_genre1.setCellValueFactory(new PropertyValueFactory<>("genre"));
                col_availability1.setCellValueFactory(new PropertyValueFactory<>("availability"));


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
        String addBookSql = "Insert INTO checkout (checkout_id, user_id, ISBN) VALUES (DEFAULT,?,?)";
        try {
            statement = conn.prepareStatement(addBookSql);
            statement.setString(2, userID);
            statement.setString(3, isbn);
            statement.executeUpdate();

            additionLabel.setText("Checked Out: " + title);
            additionLabel.setTextFill(Color.GREEN);
            updateBookAvailability("decrease");

        } catch (SQLException e) {
            additionLabel.setText("Already Checked Out: " + title);
            additionLabel.setTextFill(Color.RED);
        }
    }

    public void returnBook(){
        String returnBookSql = "DELETE FROM checkout WHERE ISBN = '" +isbn+"'";
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
    public void updateBookAvailability(String value) {
    	String getAvailability = "SELECT availability FROM books WHERE ISBN  '" +isbn+"'";
    	int avail = 0;
        try {
        	ResultSet rs = conn.createStatement().executeQuery(getAvailability);
        	while(rs.next()) {
        		avail = rs.getInt(availability);
        	}
         
        } catch(SQLException e) {
        	additionLabel.setText("Oops... Something went WRONG");
            additionLabel.setTextFill(Color.RED);
        }
        
        if(value == "increase") {
        	avail++;
        	String updateAvailability = "UPDATE books SET availability = ?";
        	try {
                statement = conn.prepareStatement(updateAvailability);
                statement.setInt(1, avail);
                statement.executeUpdate();
            } catch (SQLException e) {
                additionLabel.setText("Oops... Something went WRONG");
                additionLabel.setTextFill(Color.RED);
            }
        } else {
        	avail--;
        	String updateAvailability = "UPDATE books SET availability = ?";
        	try {
                statement = conn.prepareStatement(updateAvailability);
                statement.setInt(1, avail);
                statement.executeUpdate();
            } catch (SQLException e) {
                additionLabel.setText("Oops... Something went WRONG");
                additionLabel.setTextFill(Color.RED);
            }
        	
        }
    	
    }

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
        //book.setavailability(res.getavailability());

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

    public void setName(String userName){
        this.userName = userName;
        name.setText(userName);
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
        String getBooksSql = "SELECT * FROM books";
        try {
            ResultSet rs = conn.createStatement().executeQuery(getBooksSql);
            while(rs.next()){
                observableList.add(new ModelTable(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("genre"),
                        rs.getString("availability")));

                col_isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
                col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
                col_author.setCellValueFactory(new PropertyValueFactory<>("author"));
                col_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
                col_availability.setCellValueFactory(new PropertyValueFactory<>("availability"));

                col_isbn1.setCellValueFactory(new PropertyValueFactory<>("isbn"));
                col_title1.setCellValueFactory(new PropertyValueFactory<>("title"));
                col_author1.setCellValueFactory(new PropertyValueFactory<>("author"));
                col_genre1.setCellValueFactory(new PropertyValueFactory<>("genre"));
                col_availability1.setCellValueFactory(new PropertyValueFactory<>("availability"));


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
