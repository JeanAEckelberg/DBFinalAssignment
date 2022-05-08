/**
 * A class representing the Test entity in the Discrete Mathematics 
 * study tool application
 * @author Gregory Beaucalir, Carson Bring
 * @version May 7 2022
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Test {
    
    private int testID;
    private int creatorID;
    private String testName;
    private ArrayList<Question> questions;
    private ArrayList<Topic> topics;
    private Connection c;
    
    /**
     * Constructor for the Test class that queries the database and instantiates an 
     * object with the result set
     * @param c Connection to the database
     * @param testID The unique identifier for each test in the database as an integer
     * @throws java.sql.SQLException
     */
    public Test(Connection c, int testID) throws SQLException {
        
        questions = new ArrayList<>();
        topics = new ArrayList<>();
        
        String quereyString = "Select * from test where testID = ?;";
        PreparedStatement quereyStatement;
        ResultSet set;
        
        try{
            quereyStatement = c.prepareStatement(quereyString);
        }
        catch (SQLException e){
            throw new SQLException("Can't prep statement in Test class.");
        }
        
        try{
            quereyStatement.setInt(1, testID);
        } catch (SQLException e) {
            throw new SQLException("Can't set testID");
        }
        
        try{
            set = quereyStatement.executeQuery();
            if (!set.next()) throw new SQLException();
            this.testID = set.getInt(1);
            this.creatorID = set.getInt(3);
            this.testName = set.getString(4);
            set.close();
        }
        catch (SQLException e){
            throw new SQLException("Can't execute query in Test class constructor.");
        }
        
        this.c = c;
        questions = pullQuestions();
        topics = pullTopics();
    }
    
    /**
    * Private helper method to check and see if the user has permissions to modify/remove a test
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
    private ArrayList<Question> pullQuestions() throws SQLException {
        String quereyString = "Select questionID from questionInTest where testID = ?";
        PreparedStatement quereyStmt;
        ResultSet set;
        ArrayList<Question> toReturn = new ArrayList<>();
        
        try{
            quereyStmt = c.prepareStatement(quereyString);
        }
        catch (SQLException e){
            throw new SQLException("Can't prep statement in pullQuestions of Test class.");
        }
        
        try{
            quereyStmt.setInt(1, testID);
        } 
        catch (SQLException e) {
            throw new SQLException("Can't set testID in pullQuestions of Test class");
        }
        
        try{
            set = quereyStmt.executeQuery();
            while(set.next()){
                toReturn.add(new Question(c, set.getInt(1) ) );
            }
            set.close();
        } 
        catch (SQLException e){
            throw new SQLException("Can't execute query in pullQuestions of Test class");
        }
        
        return toReturn;
    }
    
    /**
     * Private helper method to populate the topics associated with this test object
     * @param c Connection to the database data is pulled from
     * @return ArrayList of topics that are associated with this test
     */
    private ArrayList<Topic> pullTopics() throws SQLException {
        String quereyString = "Select topicID from topicInTest where testID = ?";
        PreparedStatement quereyStmt;
        ResultSet set;
        ArrayList<Topic> toReturn = new ArrayList<>();
        
        try{
            quereyStmt = c.prepareStatement(quereyString);
        }
        catch (SQLException e){
            throw new SQLException("Can't prep statement in pullTopics of Test class.");
        }
        
        try{
            quereyStmt.setInt(1, testID);
        } 
        catch (SQLException e) {
            throw new SQLException("Can't set testID in pullTopics of Test class");
        }
        
        try{
            set = quereyStmt.executeQuery();
            while(set.next()){
                toReturn.add(new Topic(c, set.getInt(1) ) );
            }
            set.close();
        } 
        catch (SQLException e){
            throw new SQLException("Can't execute query in pullTopics of Test class");
        }
        
        return toReturn;
    }
    
    /**
    * Method to get the number of questions associated with this test object
    * @return number of questions
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
     * @throws java.sql.SQLException
     */
    public static int getNumQuestions(Connection c, int testID) throws SQLException {
        String quereyString = "Select count(*) from questionInTest where testID = ?";
        PreparedStatement quereyStmt;
        ResultSet set;
        
        try{
            quereyStmt = c.prepareStatement(quereyString);
        }
        catch (SQLException e){
            throw new SQLException("Can't prep statement in getNumQuestions of Test class.");
        }
        
        try{
            quereyStmt.setInt(1, testID);
        } 
        catch (SQLException e) {
            throw new SQLException("Can't set testID in getNumQuestions of Test class");
        }
        
        try{
            set = quereyStmt.executeQuery();
            if (!set.next()) throw new SQLException();
            int toReturn = set.getInt(1);
            set.close();
            return toReturn;
            
        }
        catch (SQLException e){
            throw new SQLException("Can't execute query in getNumQuestions of Test class");
        }
    }
    
    /**
    * Method to get the number of topics associated with this test object
    * @return number of topics
    */
    public int getNumTopics(){
        return topics.size();
    }
    
    /**
     * Static method that takes a connection and a testID and returns the number of 
     * topics in that test
     * @param c Connection to the database
     * @param testID identifier for the test in the db
     * @return number of topics in the test passed
     * @throws java.sql.SQLException
     */
    public static int getNumTopics(Connection c, int testID) throws SQLException{
        String quereyString = "Select count(*) from topicInTest where testID = ?";
        PreparedStatement quereyStmt;
        ResultSet set;
        
        try{
            quereyStmt = c.prepareStatement(quereyString);
        }
        catch (SQLException e){
            throw new SQLException("Can't prep statement in getNumTopics of Test class.");
        }
        
        try{
            quereyStmt.setInt(1, testID);
        } 
        catch (SQLException e) {
            throw new SQLException("Can't set testID in getNumTopics of Test class");
        }
        
        try{
            set = quereyStmt.executeQuery();
            if (!set.next()) throw new SQLException();
            int toReturn = set.getInt(1);
            set.close();
            return toReturn;
            
        }
        catch (SQLException e){
            throw new SQLException("Can't execute query in getNumTopics of Test class");
        }
    }
    
    /**
     * Method to get the name of this test
     * @return name of the test
     */
    public String getTestName(){
        return testName;
    }
    
    /**
     * A static method to insert a Test and it's questions into the database that 
     * uses a transaction to ensure that there are no orphaned questions or 
     * other problems
     * @param c Connection to the database
     * @param questions An array of questions already in the database
     * @param creatorID identifier of the user who made the test
     * @param testName name of the test
     * @throws java.sql.SQLException
     */
    public static void createTest(Connection c, ArrayList<Question> questions, 
            int creatorID, String testName) throws SQLException {
        
        // Begin Transaction
        try{
            c.setAutoCommit(false); // set to false in order to make a transaction
        
            // get the next testID for the linking table inserts
            String nextIDString = "select count(*) from test";
            PreparedStatement nextIDStmt;
            int nextID  = -1;
            ResultSet set;

            try{
                nextIDStmt = c.prepareStatement(nextIDString);
            }
            catch (SQLException e){
                throw new SQLException("Can't prep statemtent to get "
                        + "the next testID in createTest of Test class");
            }

            try{
                set = nextIDStmt.executeQuery();
                if (!set.next()) throw new SQLException();
                nextID = set.getInt(1);
                set.close();
            }
            catch(SQLException e){
                throw new SQLException("Can't execute statemtent to get "
                        + "the next testID in createTest of Test class");
            }

            // the test insertion
            String insertTestString = "insert into test (numberOfQuestions, "
                    + "creator, testName) values (?, ?, ?)";
            PreparedStatement insertTestStmt;

            try{
                insertTestStmt = c.prepareStatement(insertTestString);
                insertTestStmt.setInt(1, questions.size());
                insertTestStmt.setInt(2, creatorID);
                insertTestStmt.setString(3, testName);
            }
            catch (SQLException e){
                throw new SQLException("Can't prep test insert "
                        + "statement in createTest of Test class.");
            }

            try{
                insertTestStmt.executeUpdate();
                insertTestStmt.close();
            }
            catch (SQLException e){
                throw new SQLException("Can't execute test insert "
                        + "statement in createTest of Test class.");
            }
            
            // the question insertion into the linking table
            String insertQuestionsString = "insert into questionInTest "
                    + "(questionID, testID) values (?, " + nextID + " )";
            PreparedStatement insertQuestionsStmt;
            
            for (Question q : questions){
                try{
                    insertQuestionsStmt = c.prepareStatement(insertQuestionsString);
                    insertQuestionsStmt.setInt(1, q.getID());
                    insertQuestionsStmt.executeUpdate();
                }
                catch (SQLException e){
                    throw new SQLException("Problem adding Questions "
                            + "to the Test in createTest of Test class");
                }
            }
            
            c.commit();
        
        } // end transaction
        
        catch (SQLException exc){
            try{
                c.rollback(); // rollback on failure
            }
            catch (SQLException roll){
                throw new SQLException("Problem rolling back "
                        + "transaction from error: " + exc.getMessage());
            }
            
        }
        finally{ // always do this
            c.setAutoCommit(true); // set back to true to prevent problems elsewhere in application
        }
        
    }
    
    /**
     * Method to add a single question to an existing test
     * @param question question to be added
     * @param userID id of user attempting to modify the test
     * @throws SQLException 
     */
    public void addQuestionToTest(Question question, int userID) throws SQLException{
        
        if (!validatePerms(c, userID)) return;
        
        String insertString = "insert into questionInTest (questionID, "
                + "testID) values (?, " + testID + " )";
        PreparedStatement insertStmt;
        
        
        try {
            insertStmt = c.prepareStatement(insertString);
        }
        catch (SQLException e){
            throw new SQLException("Can't prep statement in "
                    + "addQuestionToTest in Test class");
        }
        
        try{
            insertStmt.setInt(1, question.getID());
        }
        catch (SQLException e) {
            throw new SQLException("Can't set questionID in "
                    + "addQuestionToTest in Test class");
        }
        
        try{
            insertStmt.executeUpdate();
        }
        catch (SQLException e){
            throw new SQLException("Can't execute update in "
                    + "addQuestionToTest in Test class");
        }
        
        questions.add(question);
        
        String updateString = "update test set numberOfQuestions = "
                + "? where testID = " + testID;
        PreparedStatement updateStmt;
        
        try{
            updateStmt = c.prepareStatement(updateString);
        }
        catch (SQLException e){
            throw new SQLException("Can't prep update statement "
                    + "in addQuestionToTest in Test class");
        }
        
        try{
            updateStmt.setInt(1, questions.size());
        }
        catch (SQLException e) {
            throw new SQLException("Can't set numberOfQuestions "
                    + "in addQuestionToTest in Test class");
        }
        
        try{
            updateStmt.executeUpdate();
        }
        catch (SQLException e){
            throw new SQLException("Can't execute update of numberOfQuestions"
                    + " in addQuestionToTest in Test class");
        }
    }
    
    /**
     * Method to add a single Topic to an existing test
     * @param topic topic to be added
     * @param userID id of user attempting to modify the test
     * @throws SQLException 
     */
    public void addTopicToTest (Topic topic, int userID) throws SQLException {
        
        if (!validatePerms(c, userID)) return;
        
        String insertString = "insert into topicInTest (topicID, testID) values (?, " + testID + " )";
        PreparedStatement insertStmt;
        
        
        try {
            insertStmt = c.prepareStatement(insertString);
        }
        catch (SQLException e){
            throw new SQLException("Can't prep statement in addTopicToTest in Test class");
        }
        
        try{
            insertStmt.setInt(1, topic.getID());
        }
        catch (SQLException e) {
            throw new SQLException("Can't set questionID in addTopicToTest in Test class");
        }
        
        try{
            insertStmt.executeUpdate();
        }
        catch (SQLException e){
            throw new SQLException("Can't execute update in addTopicToTest in Test class");
        }
        
        topics.add(topic);
    }
    
    /**
     * Method to remove a question from a test
     * @param questionID identifier for the question being removed from the test
     * @param userID identifier of the user attempting to modify the test
     * @throws java.sql.SQLException
     */
    public void removeQuestionFromTest(int questionID, int userID) throws SQLException{
        if (!validatePerms(c, userID)) return;
        
        // find the question in question
        Question temp = null;
        for (Question q : questions){
            if (q.getID() == questionID) temp = q;
        }
        if (temp == null) return;
        
        // remove from linking table
        String deleteString = "delete from questionInTest where questionID = " + questionID
                + " and testID = " + testID;
        PreparedStatement deleteStmt = c.prepareStatement(deleteString);
        deleteStmt.executeUpdate();
        
        questions.remove(temp);
        
        // update numberOfQuestions
        String updateString = "update test set numberOfQuestions = "
                + "? where testID = " + testID;
        PreparedStatement updateStmt;
        
        try{
            updateStmt = c.prepareStatement(updateString);
        }
        catch (SQLException e){
            throw new SQLException("Can't prep update statement "
                    + "in removeQuestionFromTest in Test class");
        }
        
        try{
            updateStmt.setInt(1, questions.size());
        }
        catch (SQLException e) {
            throw new SQLException("Can't set numberOfQuestions "
                    + "in removeQuestionFromTest in Test class");
        }
        
        try{
            updateStmt.executeUpdate();
        }
        catch (SQLException e){
            throw new SQLException("Can't execute update of numberOfQuestions"
                    + " in removeQuestionFromTest in Test class");
        }
    }
    
    
    /**
     * Method to remove a topic from a test
     * @param topicID identifier of the topic being removed from the test
     * @param userID identifier of the user attempting to modify the test
     * @throws java.sql.SQLException
     */
    public void removeTopic(int topicID, int userID) throws SQLException{
        if (!validatePerms(c, userID)) return;
        
        // find the question in question
        Topic temp = null;
        for (Topic t : topics){
            if (t.getID() == topicID) temp = t;
        }
        if (temp == null) return;
        
        // remove from linking table
        String deleteString = "delete from topicInTest where topicID = " + topicID
                + " and testID = " + testID;
        PreparedStatement deleteStmt = c.prepareStatement(deleteString);
        deleteStmt.executeUpdate();
        
        topics.remove(temp);
        
    }
    
    /**
    * Method to remove a test from the database using a transaction to ensure 
    * there are not orphan records in linking tables or leaderboard
     * @param c connection to the database
     * @param test Test object of the test to be removed from the database
     * @param userID identifier of the user attempting to remove the test
     * @throws java.sql.SQLException
    */
    public void removeTest(Connection c, Test test, int userID) throws SQLException{
        if (!test.validatePerms(c, userID)) return;
        
        // prep work
        String deleteQsStr = "delete from questionInTest where testID = " + test.getTestID();
        String deleteTsStr = "delete from topicInTest where testID = " + test.getTestID();
        String deleteLeaderboardsStr = "delete from leaderboard where testID = " + test.getTestID();
        String deleteTestStr = "delete from test where testID = " + test.getTestID();
        PreparedStatement deleteQsStmt, deleteTsStmt, deleteLeadStmt, deleteTestStmt;
        
        // begin transaction
        try{
            c.setAutoCommit(false);
            
            try{
                deleteQsStmt = c.prepareStatement(deleteQsStr);
                deleteTsStmt = c.prepareStatement(deleteTsStr);
                deleteLeadStmt = c.prepareStatement(deleteLeaderboardsStr);
                deleteTestStmt = c.prepareStatement(deleteTestStr);
            }
            catch (SQLException e){
                throw new SQLException("Can't prep the deletion statements in "
                        + "removeTest in Test class");
            }
            
            try{
                deleteQsStmt.executeUpdate();
                deleteTsStmt.executeUpdate();
                deleteLeadStmt.executeUpdate();
                deleteTestStmt.executeUpdate();
            }
            catch (SQLException e){
                throw new SQLException("Can't execute deletion statements in "
                        + "removeTest in Test class");
            }
            
            c.commit();
            // end of transaction
        }
        catch (SQLException exc){
            try{
                c.rollback(); // rollback on failure
            }
            catch (SQLException roll){
                throw new SQLException("Problem rolling back "
                        + "transaction from error: " + exc.getMessage());
            }
            
        }
        finally{ // always do this
            c.setAutoCommit(true); // set back to true to prevent problems elsewhere in application
        }
    }
    
    /**
     * Getter method for the testID
     * @return testID
     */
    public int getTestID(){
        return testID;
    }
    
    /**
     * Getter method for the creatorID
     * @return creatorID
     */
    public int getCreatorID(){
        return creatorID;
    }
    
    
    // int returning createTest method
    /*
        public static int createTest(Connection c,
            int creatorID) throws SQLException {
        
        // Begin Transaction
        try{
            c.setAutoCommit(false); // set to false in order to make a transaction
        
            // get the next testID for the linking table inserts
            String nextIDString = "select count(*) from test";
            PreparedStatement nextIDStmt;
            int nextID  = -1;
            ResultSet set;

            try{
                nextIDStmt = c.prepareStatement(nextIDString);
            }
            catch (SQLException e){
                throw new SQLException("Can't prep statemtent to get the next testID in createTest of Test class");
            }

            try{
                set = nextIDStmt.executeQuery();
                if (!set.next()) throw new SQLException();
                nextID = set.getInt(1);
                set.close();
            }
            catch(SQLException e){
                throw new SQLException("Can't execute statemtent to get the next testID in createTest of Test class");
            }

            // the test insertion
            String insertTestString = "insert into test (numberOfQuestions, creator) values (?, ?)";
            PreparedStatement insertTestStmt;

            try{
                insertTestStmt = c.prepareStatement(insertTestString);
                insertTestStmt.setInt(1, 0);
                insertTestStmt.setInt(2, creatorID);
            }
            catch (SQLException e){
                throw new SQLException("Can't prep test insert statement in createTest of Test class.");
            }

            try{
                insertTestStmt.executeUpdate();
                insertTestStmt.close();
            }
            catch (SQLException e){
                throw new SQLException("Can't execute test insert statement in createTest of Test class.");
            }
            
           
            
            
            c.commit();
            return nextID;
        
        } // end transaction
        
        catch (SQLException exc){
            try{
                c.rollback(); // rollback on failure
            }
            catch (SQLException roll){
                throw new SQLException("Problem rolling back transaction from error: " + exc.getMessage());
            }
            
        }
        finally{ // always do this
            c.setAutoCommit(true); // set back to true to prevent problems elsewhere in application
            return -1;
        }
        
    }
    
    
    */
}
