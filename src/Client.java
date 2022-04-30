/**
 *
 * @author Jean, Greg, Carson
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        // Open simple connection to DB
        String jdbcURL = "jdbc:postgresql://localhost:5432/FinalDB";
        String username = "user";
        String password = "resu";
        Connection connection;
        try {
            connection = DriverManager.getConnection(jdbcURL,username,password);
        } catch (SQLException e){
            System.err.println("Connection Failed");
        }
        Window w = new Window("Discrete DB", 1280, 960); //connection,
        w.LogIn();
        
    }
    
}
