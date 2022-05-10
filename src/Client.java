/**
 *
 * @author Jean, Greg, Carson
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Client {


    public static void main(String[] args) {
        
        
        // Open simple connection to DB
        String jdbcURL = "jdbc:postgresql://localhost:5432/FinalDB";
        String username = "user";
        String password = "resu";
        Connection connection;
        try {
            connection = DriverManager.getConnection(jdbcURL,username,password);

            
            Window w = new Window("Discrete DB Application", 1280, 800, connection); 
            
            w.LogIn();
            
        } catch (SQLException e){
            System.err.println("Fatal Error: Possible problem with connection");
        }
        
    }
    
}
