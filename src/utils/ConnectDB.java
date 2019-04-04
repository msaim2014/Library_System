package utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectDB {
    Connection connection = null;
    static String dbName = "";
    static String url = "jdbc:mysql://localhost:3306/"+dbName;
    static String username = "root";
    static String password = "Library2019";

    public static Connection conDB(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, password);
            //PreparedStatement ps = conn.prepareStatement("INSERT INTO userdb.users (name) VALUES ('M Saim');");
            //int status = ps.executeUpdate();
            //if(status!=0){System.out.println("Successful!");}
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
