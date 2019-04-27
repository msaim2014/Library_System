package utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connect to the local database
 * databaseName, url with the JDBC connector, username and password are required
 */
public class ConnectDB {
    Connection connection = null;
    static String dbName = "library_system";
    static String url = "jdbc:mysql://localhost:3306/"+dbName;
    static String username = "root";
    static String password = "Library2019";
//    static String password = "";

    /**
     * After the information is put, the connection is tested
     * @return true if the connection can be established
     * otherwise @return false if there is no connection
     */
    public static Connection conDB(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, password);
            return conn;
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            return null;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
