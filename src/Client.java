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
            Window w = new Window("Discrete DB", 825, 480, connection); //connection,
            //w.LogIn();
            //w.SearchPage(new User(connection, "Milksoplimit", "p455word"));
            Test t = new Test(connection, 1);
            System.out.print(t.pullTopTen()[0][1]);
            //w.SignUp();
            /* Testing calls for methods
            //User.createUser(connection, "Jim", "1234");
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
            q1.removeTopicLinks(connection, 4, 4);
            test = q1.getTopics(connection, 4, 0);
            for (Topic item : test) System.out.println(item.getDescription());
            */
            
        } catch (SQLException e){
            System.err.println("Connection Failed");
        }
        
    }
    
}
