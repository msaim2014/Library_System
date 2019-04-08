package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML private Label name;
    @FXML private Button addBookButton;
<<<<<<< HEAD
    @FXML private Button returnBookButton;
=======
>>>>>>> 4d3334ebe1bed2c25cc1764984840f2396358ef8
    @FXML private Label additionLabel;

    @FXML private TableView<ModelTable> table;
    @FXML private TableColumn<ModelTable, Integer> col_isbn;
    @FXML private TableColumn<ModelTable, String> col_title;
    @FXML private TableColumn<ModelTable, String> col_author;
    @FXML private TableColumn<ModelTable, String> col_genre;
    @FXML private TableColumn<ModelTable, Integer> col_numAvailable;

    private String userName;
    private Book book;
    Connection conn = null;
    ObservableList<ModelTable> observableList = FXCollections.observableArrayList();
    PreparedStatement addBook = null;
<<<<<<< HEAD
    PreparedStatement returnBook = null;
    PreparedStatement checkBook = null;
    ResultSet res = null;
=======
    PreparedStatement checkBook = null;
>>>>>>> 4d3334ebe1bed2c25cc1764984840f2396358ef8

    private String isbn;
    private String title;
    private String author;
    private String genre;
    private String checkOut;
    private String checkIn;

    public HomeController(){
        conn = ConnectDB.conDB();
    }

    public void addBook(){
        String addBookSql = "Insert INTO userdb." + userName + "(isbn, title, author, genre, checkOut, checkIn) VALUES (?,?,?,?,?,?)";
        try {
            addBook = conn.prepareStatement(addBookSql);
            addBook.setString(1, isbn);
            addBook.setString(2, title);
            addBook.setString(3, author);
            addBook.setString(4, genre);
            addBook.setString(5, checkOut);
            addBook.setString(6, checkIn);
<<<<<<< HEAD
            int num = addBook.executeUpdate();

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
            returnBook = conn.prepareStatement(returnBookSql);
            returnBook.executeUpdate();
            additionLabel.setText("Returned: " + title);
            additionLabel.setTextFill(Color.GREEN);
        } catch (SQLException e) {
            additionLabel.setText("Oops... Something went WRONG");
            additionLabel.setTextFill(Color.RED);
        }
    }

    public boolean hasBook(){return true;}
=======
            addBook.executeUpdate();
            additionLabel.setText("Added:" + title);
            updateBookAvailability();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //public boolean hasBook(){}

>>>>>>> 4d3334ebe1bed2c25cc1764984840f2396358ef8
    public void updateBookAvailability(){ }

    public void getSelected(){
        ModelTable res = table.getSelectionModel().getSelectedItem();

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
<<<<<<< HEAD
    }

    public void setName(String userName){
        this.userName = userName;
        name.setText(userName);
=======
>>>>>>> 4d3334ebe1bed2c25cc1764984840f2396358ef8
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

                //calls all the getters and setters in ModelTable
                table.setItems(observableList);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (IllegalStateException e){
            System.err.println(e.getMessage());
        }
    }
}
