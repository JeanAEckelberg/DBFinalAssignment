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
        String username = "postgres";
        String password = "jgfpu2cq";
        Connection connection;
        try {
            connection = DriverManager.getConnection(jdbcURL,username,password);
            Window w = new Window("Discrete DB", 1280, 960, connection); //connection,
            
              User user1 = new User(connection, "Jim", "1234");
            w.AddTest1(user1);
            /* Testing calls for methods
            User.createUser(connection, "Jim", "1234");
            User user1 = new User(connection, "Jim", "1234");
            System.out.println(user1.getUserName());
            //Topic.createTopic(connection, "Test", "Test Description", 8);
            Topic t1 = new Topic(connection, 4);
            System.out.println(t1.getDescription());
            
            Question q1 = new Question(connection, 1);
            System.out.println(q1.getText());
            q1.addTopic(connection, 4, 4);
            Topic[] test = q1.getTopics(connection, 4, 0);
            for (Topic item : test) System.out.println(item.getDescription());
            q1.removeTopic(connection, 4, 4);
            test = q1.getTopics(connection, 4, 0);
            for (Topic item : test) System.out.println(item.getDescription());
            */
            
        } catch (SQLException e){
            System.err.println("Connection Failed");
        }
        
    }
    
}
