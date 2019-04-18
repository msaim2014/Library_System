package utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectDB {
    Connection connection = null;
    static String dbName = "library_system";
    static String url = "jdbc:mysql://localhost:3306/"+dbName;
    static String username = "root";
//    static String password = "Library2019";
    static String password = "";

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
