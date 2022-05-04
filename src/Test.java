/**
 * A class representing the Test entity in the Discrete Mathematics 
 * study tool application
 * @author Greg
 * @version May 4 2022
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
    * Private helper method to check and see if the has permissions to modify/remove a test
    */
    private boolean validatePerms(Connection c, int id) throws SQLException {
        int permLvl = User.getPermissionLevel(c, id);
        return permLvl > 1 || id == creatorID;
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
    
    /**
    * Method to get the number of questions associated with this test object
    */
    public int getNumQuestions(){
        return questions.size();
    }
    
    /**
     * Static method that takes a connection and a testID and returns the number of 
     * questions in that test
     * @param c Connection to the database
     * @param testID identifier for the test in the db
     * @return number of questions in the test passed
     */
    public static int getNumQuestions(Connection c, int testID){
        String quereyString = "Select count(*) from questionInTest where testID = ?";
        PreparedStatement quereyStmt;
        ResultSet set;
        
        try{
            quereyStmt = c.prepareStatement(quereyString);
        }
        catch (SQLException e){
            System.err.println("Can't prep statement in getNumQuestions of Test class.");
            System.exit(1);
            return -1;
        }
        
        try{
            quereyStmt.setInt(1, testID);
        } 
        catch (SQLException e) {
            System.err.println("Can't set testID in getNumQuestions of Test class");
            System.exit(1);
            return -1;
        }
        
        try{
            set = quereyStmt.executeQuery();
            if (!set.next()) throw new SQLException();
            int toReturn = set.getInt(1);
            set.close();
            return toReturn;
            
        }
        catch (SQLException e){
            System.err.println("Can't execute query in getNumQuestions of Test class");
            System.exit(1);
            return -1;
        }
    }
    
    /**
    * Method to get the number of topics associated with this test object
    */
    public int getNumTopics(){
        return topics.size();
    }
    
    /**
     * Static method that takes a connection and a testID and returns the number of 
     * topics in that test
     * @param c Connection to the database
     * @param topicID identifier for the test in the db
     * @return number of topics in the test passed
     */
    public static int getNumTopics(Connection c, int topicID){
        String quereyString = "Select count(*) from topicInTest where testID = ?";
        PreparedStatement quereyStmt;
        ResultSet set;
        
        try{
            quereyStmt = c.prepareStatement(quereyString);
        }
        catch (SQLException e){
            System.err.println("Can't prep statement in getNumTopics of Test class.");
            System.exit(1);
            return -1;
        }
        
        try{
            quereyStmt.setInt(1, topicID);
        } 
        catch (SQLException e) {
            System.err.println("Can't set testID in getNumTopics of Test class");
            System.exit(1);
            return -1;
        }
        
        try{
            set = quereyStmt.executeQuery();
            if (!set.next()) throw new SQLException();
            int toReturn = set.getInt(1);
            set.close();
            return toReturn;
            
        }
        catch (SQLException e){
            System.err.println("Can't execute query in getNumTopics of Test class");
            System.exit(1);
            return -1;
        }
    }
    
    /**
     * A static method to insert a Test into the database
     * @param c Connection to the database
     * @param questions An array of questions already in the database
     * @param creatorID identifier of the user who made the test
     */
    public static void createTest(Connection c, Question[] questions, int creatorID){
        // Under construction
    }
}
