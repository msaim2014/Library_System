package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.ConnectDB;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML private VBox prItems = null;
    @FXML private Label name;
    @FXML private TableView<ModelTable> table;
    @FXML private TableColumn<ModelTable, Integer> col_isbn;
    @FXML private TableColumn<ModelTable, String> col_title;
    @FXML private TableColumn<ModelTable, String> col_author;
    @FXML private TableColumn<ModelTable, String> col_genre;
    @FXML private TableColumn<ModelTable, Integer> col_numAvailable;

    private String userName;
    private ArrayList books;
    Connection conn = null;
    ObservableList<ModelTable> observableList = FXCollections.observableArrayList();

    public HomeController(){
        conn = ConnectDB.conDB();
        books = new ArrayList();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String getBooks = "SELECT * FROM userdb.books";
        try {
            ResultSet rs = conn.createStatement().executeQuery(getBooks);
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

    public void setName(String userName){
        this.userName = userName;
        name.setText(userName);
    }
}
