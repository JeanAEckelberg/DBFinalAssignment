/**
 *
 * @author Greg
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Test {
    
    private int testID;
    private int creatorID;
    private ArrayList<Question> questions;
    private ArrayList<Topic> topics;
    private Connection c;
    
    /**
     * Constructor for the Test class that queries the database and instantiates an 
     * object with the result set
     * @param c Connection to the database
     * @param testID The unique identifier for each test in the database as an integer
     */
    public Test(Connection c, int testID) {
        
        questions = new ArrayList<>();
        topics = new ArrayList<>();
        
        String quereyString = "Select * from test where testID = ?;";
        PreparedStatement quereyStatement;
        ResultSet set;
        
        try{
            quereyStatement = c.prepareStatement(quereyString);
        }
        catch (SQLException e){
            System.err.println("Can't prep statement in Test class.");
            System.exit(1);
            return;
        }
        
        try{
            quereyStatement.setInt(1, testID);
        } catch (SQLException e) {
            System.err.println("Can't set testID");
            System.exit(1);
            return;
        }
        
        try{
            set = quereyStatement.executeQuery();
            if (!set.next()) throw new SQLException();
            this.testID = set.getInt(1);
            this.creatorID = set.getInt(3);
            set.close();
        }
        catch (SQLException e){
            System.err.println("Can't execute query in Test class constructor.");
            System.exit(1);
            return;
        }
        
        questions = pullQuestions(c);
        topics = pullTopics(c);
        
    }
    
    /**
     * Private helper method to populate the list of questions associated with this 
     * test object
     * @param c Connection to database information is pulled from
     * @return The arrayList of questions associated with the test
     */
    private ArrayList<Question> pullQuestions(Connection c){
        String quereyString = "Select questionID from questionInTest where testID = ?";
        PreparedStatement quereyStmt;
        ResultSet set;
        ArrayList<Question> toReturn = new ArrayList<>();
        
        try{
            quereyStmt = c.prepareStatement(quereyString);
        }
        catch (SQLException e){
            System.err.println("Can't prep statement in pullQuestions of Test class.");
            System.exit(1);
            return null;
        }
        
        try{
            quereyStmt.setInt(1, testID);
        } 
        catch (SQLException e) {
            System.err.println("Can't set testID in pullQuestions of Test class");
            System.exit(1);
            return null;
        }
        
        try{
            set = quereyStmt.executeQuery();
            while(set.next()){
                toReturn.add(new Question(c, set.getInt(1) ) );
            }
            set.close();
        } 
        catch (SQLException e){
            System.err.println("Can't execute query in pullQuestions of Test class");
            System.exit(1);
            return null;
        }
        
        return toReturn;
    }
    
    /**
     * Private helper method to populate the topics associated with this test object
     * @param c Connection to the database data is pulled from
     * @return ArrayList of topics that are associated with this test
     */
    private ArrayList<Topic> pullTopics(Connection c){
        String quereyString = "Select topicID from topicInTest where testID = ?";
        PreparedStatement quereyStmt;
        ResultSet set;
        ArrayList<Topic> toReturn = new ArrayList<>();
        
        try{
            quereyStmt = c.prepareStatement(quereyString);
        }
        catch (SQLException e){
            System.err.println("Can't prep statement in pullTopics of Test class.");
            System.exit(1);
            return null;
        }
        
        try{
            quereyStmt.setInt(1, testID);
        } 
        catch (SQLException e) {
            System.err.println("Can't set testID in pullTopics of Test class");
            System.exit(1);
            return null;
        }
        
        try{
            set = quereyStmt.executeQuery();
            while(set.next()){
                toReturn.add(new Topic(c, set.getInt(1) ) );
            }
            set.close();
        } 
        catch (SQLException e){
            System.err.println("Can't execute query in pullTopics of Test class");
            System.exit(1);
            return null;
        }
        
        return toReturn;
    }
    
    
}
