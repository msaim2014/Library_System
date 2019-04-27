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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * HomeController has control over everything that goes on within the
 * main application page.
 */
public class HomeController implements Initializable {
    @FXML private VBox prItems = null;

    //My Account
    @FXML private Button editButton;
    @FXML private Text myAccountUsername;
    @FXML private Text myAccountPassword;
    @FXML private TextField textUsername;
    @FXML private TextField textPassword;
    @FXML private Label accountChangeStatus;
    
    //Search
    @FXML private Button searchBookButton;
    @FXML private TextField searchBookTextfield;

    //overview
    @FXML private Label name;
    @FXML private Button checkOutButton;
    @FXML private Button returnButton;
    @FXML private Label additionLabel;
    @FXML private Button overViewButton;
    @FXML private Button myAccountButton;
    @FXML private Button manageBooksButton;
    @FXML private Button searchBooksButton;
    @FXML private Button signoutButton;
    @FXML private Pane overViewPane;
    @FXML private Pane myAccountPane;
    @FXML private Pane searchBooksPane;
    @FXML private Label countCheckout;
    @FXML private Label nextDueDate;
    
    @FXML private TableView<ModelTable> table;
    @FXML private TableColumn<ModelTable, String> col_isbn;
    @FXML private TableColumn<ModelTable, String> col_title;
    @FXML private TableColumn<ModelTable, String> col_author;
    @FXML private TableColumn<ModelTable, String> col_genre;
    @FXML private TableColumn<ModelTable, String> col_availability;
    
    @FXML private TableView<ModelTable> table2;
    @FXML private TableColumn<ModelTable, String> col_isbn2;
    @FXML private TableColumn<ModelTable, String> col_title2;
    @FXML private TableColumn<ModelTable, String> col_author2;
    @FXML private TableColumn<ModelTable, String> col_genre2;
    @FXML private TableColumn<ModelTable, String> col_availability2;
    
    @FXML private TableView<ModelTableUser> table3;
    @FXML private TableColumn<ModelTableUser, String> col_usernameU;
    @FXML private TableColumn<ModelTableUser, String> col_ISBNU;
    @FXML private TableColumn<ModelTableUser, String> col_checkoutDateU;
    @FXML private TableColumn<ModelTableUser, String> col_returnDateU;

    //admin manage books
    @FXML private Pane manageBooksPane;
    @FXML private TextField addISBN;
    @FXML private TextField addTitle;
    @FXML private TextField addAuthor;
    @FXML private TextField addGenre;
    @FXML private TextField addAvailability;
    @FXML private TextField removeISBN;
    @FXML private TextField editISBN;
    @FXML private TextField editTitle;
    @FXML private TextField editAuthor;
    @FXML private TextField editGenre;
    @FXML private TextField editAvailability;
    @FXML private TableView<ModelTable> manageTable;
    @FXML private TableColumn<ModelTable, Integer> col_isbn1;
    @FXML private TableColumn<ModelTable, String> col_title1;
    @FXML private TableColumn<ModelTable, String> col_author1;
    @FXML private TableColumn<ModelTable, String> col_genre1;
    @FXML private TableColumn<ModelTable, Integer> col_availability1;
    @FXML private Button addBookButton;
    @FXML private Button removeBookButton;
    @FXML private Button showAllBooksButton;
    @FXML private Label bookStatus;

    private Book book;
    Connection conn = null;
    ObservableList<ModelTable> observableList = FXCollections.observableArrayList();
    ObservableList<ModelTableUser> observableListUser = FXCollections.observableArrayList();
    PreparedStatement statement = null;
    ResultSet res = null;
    Account account = Account.getInstance();

    private String isbn;
    private String title;
    private String author;
    private String genre;
    private String checkOut;
    private String checkIn;
    private String availability;

    /**
     * Database connection
     */
    public HomeController(){
        conn = ConnectDB.conDB();
    }

    /**
     * Function that allows a user to change the username and/or password
     */
    public void editAccount(){
        //name is the new username and userName is the old one
        String name = textUsername.getText();
        String pass = textPassword.getText();
        String userName =account.getUsername();

        if("".equals(name) || "".equals(pass)){
            accountChangeStatus.setText("Please Enter name and password");
            accountChangeStatus.setTextFill(Color.RED);
        }else{
            String changeUserSql = "UPDATE users SET username='"+name+"', password='"+pass+ "' WHERE username='"+userName+"'";
            try {
                statement=conn.prepareStatement(changeUserSql);
                statement.executeUpdate();
                accountChangeStatus.setText("Success!");
                accountChangeStatus.setTextFill(Color.GREEN);
                account.setUsername(name);
                account.setPass(pass);
                myAccountUsername.setText(account.getUsername());
                myAccountPassword.setText(account.getPass());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                accountChangeStatus.setText("Opps... password/username taken");
                accountChangeStatus.setTextFill(Color.RED);
            }

        }

    }
    
    /**
     * Function that will search whether a book with a given title exists or not.
     * The information is displayed in a table
     */
    public void searchBookFunction() {
    	String bookSearched = searchBookTextfield.getText();
    	String seachBookSQL = "SELECT * FROM books WHERE title LIKE '%" + bookSearched + "%'";
    	observableList.clear();
    	try {
    		ResultSet rs = conn.createStatement().executeQuery(seachBookSQL);
            while(rs.next()){
                observableList.add(new ModelTable(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("genre"),
                        rs.getString("availability")));

                col_isbn2.setCellValueFactory(new PropertyValueFactory<>("isbn"));
                col_title2.setCellValueFactory(new PropertyValueFactory<>("title"));
                col_author2.setCellValueFactory(new PropertyValueFactory<>("author"));
                col_genre2.setCellValueFactory(new PropertyValueFactory<>("genre"));
                col_availability2.setCellValueFactory(new PropertyValueFactory<>("availability"));

                //calls all the getters and setters in ModelTable
                table2.setItems(observableList);
                manageTable.setItems(observableList);
            }
    	} catch (SQLException e) {
    		System.out.println(e.getMessage());
    	}
    }

    /**
     * Adds a book to the database
     * The information given is entered into the system,
     * the values are captured and entered into the database
     */
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

        } catch (SQLException e) {
            bookStatus.setText("Missing Fields or Duplicate ISBN");
            bookStatus.setTextFill(Color.RED);
        }
        refreshTables();
    }

    /**
     * Deletes a book from the database
     * looks for an ISBN. If ISBN exists, it will delete that book
     * will return an error if the ISBN does not exit
     */
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
    
    /**
     * edit book function that will update the information of a book
     * the function looks for the primary key (ISBN) and then it will run an update query to update the values
     * will return an error is the ISBN does not exist
     */
    public void editBook() {
    	this.isbn = editISBN.getText();
        this.title = editTitle.getText();
        this.author = editAuthor.getText();
        this.genre = editGenre.getText();
        this.availability = editAvailability.getText();
        
        String editBookSql = "UPDATE books SET title = ?, author = ?, genre = ?, availability = ? WHERE ISBN = ?";
        try {
            statement = conn.prepareStatement(editBookSql);
            statement.setString(1, title);
            statement.setString(2, author);
            statement.setString(3, genre);
            statement.setString(4, availability);
            statement.setString(5, isbn);
            statement.executeUpdate();

            bookStatus.setText(title + " Modified");
            bookStatus.setTextFill(Color.GREEN);

        } catch (SQLException e) {
            bookStatus.setText("Missing Fields or ISBN not found");
            bookStatus.setTextFill(Color.RED);
        }
        refreshTables();
    }

    /**
     * refresh tables function that will display all books in our database
     */
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
                
                col_isbn2.setCellValueFactory(new PropertyValueFactory<>("isbn"));
                col_title2.setCellValueFactory(new PropertyValueFactory<>("title"));
                col_author2.setCellValueFactory(new PropertyValueFactory<>("author"));
                col_genre2.setCellValueFactory(new PropertyValueFactory<>("genre"));
                col_availability2.setCellValueFactory(new PropertyValueFactory<>("availability"));

                //calls all the getters and setters in ModelTable
                table.setItems(observableList);
                table2.setItems(observableList);
                manageTable.setItems(observableList);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (IllegalStateException e){
            System.err.println(e.getMessage());
        }
    }
    
    /**
     * function that will insert a new line on the checkout table on
     * the database which will indicate that a book has been checked
     * out by the user the checkout and return date are auto generated,
     * with return date being 21 days after the checkout date the quantity
     * is decreased by 1
     */
    public void checkOutBook() {
        String userName = account.getUsername();

        String addBookSql = "Insert INTO checkout (username, ISBN, checkout_date, return_date) VALUES (?,?,?,?)";
        java.sql.Date todayDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        String today = todayDate.toString();
        String returnBook = today;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
			c.setTime(sdf.parse(returnBook));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
        c.add(Calendar.DATE, 21);
        returnBook = sdf.format(c.getTime()); 
        try {
            statement = conn.prepareStatement(addBookSql);
            statement.setString(1, userName);
            statement.setString(2, isbn);
            statement.setString(3, today);
            statement.setString(4, returnBook);
            statement.executeUpdate();

            additionLabel.setText("Checked Out: " + title);
            additionLabel.setTextFill(Color.GREEN);
            updateBookAvailability("decrease");

        } catch (SQLException e) {
            additionLabel.setText("Error code: 1 = Already Checked Out: " + title);
            additionLabel.setTextFill(Color.RED);
        }
    }

    /**
     * return book function. This process will delete the entry from the checkout table
     * which will result in a context of a book being returned. Also this will increase
     * the quantity of that specific book by 1
     */
    public void returnBook(){
        String userName = account.getUsername();
        String returnBookSql = "DELETE FROM checkout WHERE ISBN = '" + isbn + "'" + "AND username = '" + userName + "'";
        try {
            statement = conn.prepareStatement(returnBookSql);
            statement.executeUpdate();
            additionLabel.setText("Returned: " + title);
            additionLabel.setTextFill(Color.GREEN);
            updateBookAvailability("increase");
        } catch (SQLException e) {
            additionLabel.setText("Oops... Something went WRONG");
            additionLabel.setTextFill(Color.RED);
        }
    }
    
    /**
     * This functionality will update the quantity of each book in the library
     * @param value is either increase, or decrease, which will indicate which query to process
     */
    public void updateBookAvailability(String value) {
    	String getAvailability = "SELECT availability FROM books WHERE ISBN = '" + isbn + "'";
    	int avail = 0;
    	String availString;
        try {
        	ResultSet rs = conn.createStatement().executeQuery(getAvailability);
        	while(rs.next()) {
        		availString = rs.getString(availability);
        		avail = Integer.parseInt(availString);
        	}
         
        } catch(SQLException e) {
        	additionLabel.setText("Oops... Something went WRONG");
            additionLabel.setTextFill(Color.RED);
        }
        
        if(value == "increase") {
        	avail++;
        	availString = Integer.toString(avail);
        	String updateAvailability = "UPDATE books SET availability = ? WHERE ISBN = '" + isbn + "'";
        	try {
                statement = conn.prepareStatement(updateAvailability);
                statement.setString(1, availString);
                statement.executeUpdate();
            } catch (SQLException e) {
                additionLabel.setText("Oops... Something went WRONG");
                additionLabel.setTextFill(Color.RED);
            }
        } else {
        	avail--;
        	availString = Integer.toString(avail);
        	String updateAvailability = "UPDATE books SET availability = ? WHERE ISBN = '" + isbn + "'";
        	try {
                statement = conn.prepareStatement(updateAvailability);
                statement.setString(1, availString);
                statement.executeUpdate();
            } catch (SQLException e) {
                additionLabel.setText("Oops... Something went WRONG");
                additionLabel.setTextFill(Color.RED);
            }
        }
    }
    
    /**
     * Display the books that have been checked out by a specific user
     * It checks for the username on the database, and it will display all the checkouts in a table
     */
    public void displayMyCheckouts() {
    	String userQuery = "SELECT * FROM checkout WHERE username = '" + account.getUsername() + "'";
    	observableListUser.clear();
    	try {
    		ResultSet rs = conn.createStatement().executeQuery(userQuery);
            while(rs.next()){
            	observableListUser.add(new ModelTableUser(
                        rs.getString("username"),
                        rs.getString("ISBN"),
                        rs.getString("checkout_date"),
                        rs.getString("return_date")));

                col_usernameU.setCellValueFactory(new PropertyValueFactory<>("username"));
                col_ISBNU.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
                col_checkoutDateU.setCellValueFactory(new PropertyValueFactory<>("checkout_date"));
                col_returnDateU.setCellValueFactory(new PropertyValueFactory<>("return_date"));

                //calls all the getters and setters in ModelTable
                table3.setItems(observableListUser);
            }
    	} catch (SQLException e) {
    		System.out.println(e.getMessage());
    	}
    }

    /**
     * Select the book on a table
     * @param event listens to a MouseEvent that will get triggered when a book is selected
     */
    public void getSelected(MouseEvent event){
        ModelTable res = null;
        if(event.getSource()==table){
            res = table.getSelectionModel().getSelectedItem();
        }else if(event.getSource()==manageTable){
            res = manageTable.getSelectionModel().getSelectedItem();
        }

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

    /**
     * Get user account information
     * @param account get the account information from Class Account
     */
    public void setUserInfo(Account account){
        name.setText(account.getUsername());
        myAccountUsername.setText(account.getUsername());
        myAccountPassword.setText(account.getPass());
    }

    /**
     * Change the view of the system when a specific button is clicked
     * @param event MouseEvent that changes function when a button is clicked
     */
    public void changePanel(MouseEvent event){
        if(event.getSource()==overViewButton){
            overViewPane.toFront();
            refreshTables();
        }
        if(event.getSource()==myAccountButton){
            myAccountPane.toFront();
            displayMyCheckouts();
        }
        if(event.getSource()==manageBooksButton && account.getIsAdmin() == 1){
            manageBooksPane.toFront();
        }
        if(event.getSource()==searchBooksButton) {
        	searchBooksPane.toFront();
        }
    }
    
    /**
     * Logout function that will redirect the user to the login page
     * @param event MouseEvent that waits for user input and will act upon it
     */
    public void logoutFunction(MouseEvent event) {
    	if(event.getSource()==signoutButton) {
    		try{
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
                Parent root = (Parent) loader.load();

                Stage stage1 = new Stage();
                stage1.setScene(new Scene(root));
                stage1.show();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
    	}
    }

    /**
     * Function that will auto-populate the tables with the information in the books table of the database
     */
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
                
                col_isbn2.setCellValueFactory(new PropertyValueFactory<>("isbn"));
                col_title2.setCellValueFactory(new PropertyValueFactory<>("title"));
                col_author2.setCellValueFactory(new PropertyValueFactory<>("author"));
                col_genre2.setCellValueFactory(new PropertyValueFactory<>("genre"));
                col_availability2.setCellValueFactory(new PropertyValueFactory<>("availability"));

                //calls all the getters and setters in ModelTable
                table.setItems(observableList);
                table2.setItems(observableList);
                manageTable.setItems(observableList);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (IllegalStateException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * initialize the application
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        overViewPane.toFront();
        createTables();
    }
}
