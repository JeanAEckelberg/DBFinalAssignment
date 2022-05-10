/**
 * A class representing the Test entity in the Discrete Mathematics 
 * study tool application
 * @author Gregory Beaucalir
 * @version May 8 2022
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
            this.creatorID = set.getInt(2);
            testName = set.getString(3);
            if(set.wasNull()) testName = "";
            set.close();
        }
        catch (SQLException e){
            throw new SQLException("Can't execute query in Test class constructor.");
        }
        
        this.c = c;
        questions = pullQuestions();
        topics = pullTopics();
    }
    
     public Test(Connection c, String testName) throws SQLException {
        
        questions = new ArrayList<>();
        topics = new ArrayList<>();
        
        String quereyString = "Select * from test where testName = ?;";
        PreparedStatement quereyStatement;
        ResultSet set;
        
        try{
            quereyStatement = c.prepareStatement(quereyString);
        }
        catch (SQLException e){
            throw new SQLException("Can't prep statement in Test class.");
        }
        
        try{
            quereyStatement.setString(1, testName);
        } catch (SQLException e) {
            throw new SQLException("Can't set testName");
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
     * @param creatorID identifier of the user who made the test
     * @param testName name of the test
     * @throws java.sql.SQLException
     */
    public static int createTest(Connection c, 
            int creatorID, String testName) throws SQLException {
        
            String insertTestString = "insert into test ( "
                    + "creator, testName) values (?, ?)";
            PreparedStatement insertTestStmt;

            try{
                insertTestStmt = c.prepareStatement(insertTestString);
                insertTestStmt.setInt(1, creatorID);
                insertTestStmt.setString(2, testName);
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
                        
            ResultSet r = c.prepareStatement("select testID from test where testName = '" + testName + "'").executeQuery();
            r.next();
            return r.getInt(1);
        
    }
    
    /**
     * Method to add a single question to an existing test
     * @param questionID question to be added
     * @param userID id of user attempting to modify the test
     * @throws SQLException 
     */
    public void addQuestionToTest(int questionID, int userID) throws SQLException, IllegalArgumentException{
        
        if (!validatePerms(c, userID)) throw new IllegalArgumentException("addQuestionToTest : Test : perms");
        
        String insertString = "insert into questionInTest values(?, ?) ";
        PreparedStatement insertStmt;
        
        
        try {
            insertStmt = c.prepareStatement(insertString);
        }
        catch (SQLException e){
            throw new SQLException("Can't prep statement in "
                    + "addQuestionToTest in Test class");
        }
        
        try{
            insertStmt.setInt(1, questionID);
            insertStmt.setInt(2, testID);
        }
        catch (SQLException e) {
            throw new SQLException("Can't set questionID in "
                    + "addQuestionToTest in Test class");
        }
        
        try{
            insertStmt.executeUpdate();
            insertStmt.close();
        }
        catch (SQLException e){
            throw new SQLException("Can't execute update in "
                    + "addQuestionToTest in Test class");
        }
        
        questions.add(new Question(c, questionID) );
        
    }
    
    /**
     * Method to add a single Topic to an existing test
     * @param topicID topic to be added
     * @param userID id of user attempting to modify the test
     * @throws SQLException 
     */
    public void addTopicToTest (int topicID, int userID) throws SQLException, IllegalArgumentException {
        
        if (!validatePerms(c, userID)) throw new IllegalArgumentException("addTopicToTest : Test : perms");
        
        String insertString = "insert into topicInTest (topicID, testID) values (?, ?)";
        PreparedStatement insertStmt;
        
        
        try {
            insertStmt = c.prepareStatement(insertString);
        }
        catch (SQLException e){
            throw new SQLException("Can't prep statement in addTopicToTest in Test class");
        }
        
        try{
            insertStmt.setInt(1, topicID);
            insertStmt.setInt(2, testID);
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
        
        topics.add(new Topic(c, topicID) );
    }
    
     public void addTopicToTest (Topic topic) throws SQLException, IllegalArgumentException {
        
       
        
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
     * Method to remove a question from a test linking table
     * @param questionID identifier for the question being removed from the test
     * @param userID identifier of the user attempting to modify the test
     * @throws java.sql.SQLException
     */
    public void removeQuestion(int questionID, int userID) throws SQLException, IllegalArgumentException{
        if (!validatePerms(c, userID)) throw new IllegalArgumentException("removeQuestionLink : Test : perms");
        
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
        
    }
    
    
    /**
     * Method to remove a topic from a test linking table
     * @param topicID identifier of the topic being removed from the test
     * @param userID identifier of the user attempting to modify the test
     * @throws java.sql.SQLException
     */
    public void removeTopic(int topicID, int userID) throws SQLException, IllegalArgumentException{
        if (!validatePerms(c, userID)) throw new IllegalArgumentException("removeTopicLinks : Test : perms");
        
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
    * Method to remove this test from the database using a transaction to ensure 
    * there are not orphan records in linking tables or leaderboard
     * @param c connection to the database
     * @param userID identifier of the user attempting to remove the test
     * @throws java.sql.SQLException
    */
    public void removeTest(Connection c, int userID) throws SQLException, IllegalArgumentException{
        if (!validatePerms(c, userID)) throw new IllegalArgumentException("removeTest : Test: perms");
        
        // prep work
        String deleteQsStr = "delete from questionInTest where testID = " + getTestID();
        String deleteTsStr = "delete from topicInTest where testID = " + getTestID();
        String deleteLeaderboardsStr = "delete from leaderboard where testID = " + getTestID();
        String deleteTestStr = "delete from test where testID = " + getTestID();
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
    
    /**
     * Method to change the name of a test
     * @param name name to update the testName to
     * @param userID identifier of the user attempting to make the change
     * @throws java.sql.SQLException
     * @throws IllegalArgumentException
     */
    public void setTestName(String name, int userID) throws SQLException, IllegalArgumentException{
        if (!validatePerms(c, userID)) throw new IllegalArgumentException("setTestName : Test : perms");
        
        String updateString = "update test set testName = ? where testID = ? ";
        PreparedStatement updateStmt;
        
        try{
            updateStmt = c.prepareStatement(updateString);
        }
        catch (SQLException e){
            throw new SQLException("unable to prep statement in "
                    + "setTestName in Test class");
        }
        
        try{
            updateStmt.setString(1, name);
            updateStmt.setInt(2, testID);
        }
        catch (SQLException e){
            throw new SQLException("can't set the testName in "
                    + "setTestName of Test Class");
        }
        
        try{
            updateStmt.executeUpdate();
        }
        catch (SQLException e){
            throw new SQLException("Can't execute the update "
                    + "statement in setTestName in Test class");
        }
        
        testName = name;
    }
    
    /**
     * Method to add a record to the leaderboard for this test
     * @param userID user taking the test
     * @param timeTaken long value of time taken in milliseconds
     */
    public void addToLeaderboard(int userID, long timeTaken)throws SQLException{
        
        double seconds = (double) timeTaken / 1000 / 60 ;
        int minutes = (int) ((timeTaken / (1000*60)) % 60);
        int hours   = (int) ((timeTaken / (1000*60*60)) % 24);
        
        String intervalString = String.format("%02d:%02d,%02.2f", hours, minutes, seconds);
        
        String insertStr = "insert into leaderboard (userID, testID, score, timeElapsed) values(?,?,?,?)";
        PreparedStatement insertStmt;
        
        try{
            insertStmt = c.prepareStatement(insertStr);
        }
        catch(SQLException e){
            throw new SQLException("can't prep stmt in addToLeaderboard in test");
        }
        
        try{
            insertStmt.setInt(1, userID);
            insertStmt.setInt(2, getTestID());
            insertStmt.setFloat(3, (float) 100.0);
            insertStmt.setString(4, intervalString);
        }
        catch (SQLException e){
            throw new SQLException("can't set values in addToLeaderboard in test");
        }
        
        try{
            insertStmt.executeUpdate();
        }
        catch(SQLException e){
            throw new SQLException("can't execute in addToLeaderboard in test");
        }
    }
    
    /**
     * Search method that returns the top 10 times on this test and userNames that achieved them
     * @return 2 dimensional array of strings containing the top 10 times
     * @throws SQLException 
     */
    public String[][] pullTopTen() throws SQLException{
        String[][] toReturn = new String[10][2];
        String searchStr = "select userName, timeElapsed from leaderboard, "
                + "userTable where testID = " + getTestID() + " and leaderboard.userID = userTable.userID";
        PreparedStatement searchStmt;
        ResultSet set;
        
        try{
            searchStmt = c.prepareStatement(searchStr);
        }
        catch (SQLException e){
            throw new SQLException("can't prep stmt in pullTopTen of Test");
        }
        
        try{
            set = searchStmt.executeQuery();
            if (!set.next()) throw new SQLException();
            for (int i = 0; i < 10; i++){
                toReturn[i][0] = set.getString(1);
                toReturn[i][1] = set.getString(2);
            }
        }
        catch (SQLException e){
            throw new SQLException("problem executing stmt or with processing results in "
                    + "pullTopTen in Test");
        }
        
        return toReturn;
    }
    
    /**
     * getter for the topics in this test
     */
    public Topic[] getTopics(){
        return (Topic[]) topics.toArray();
    }
    
    /**
     * getter for the questions in this test
     */
    public Question[] getQuestions(){
        return (Question[]) questions.toArray();
    }
    
    /**
     * Method to remove all topics from a test
     */
    public void removeAllTopics(int userID)throws SQLException, IllegalArgumentException{
        if (!validatePerms(c, userID)) 
            throw new IllegalArgumentException("removeAllTopics : test : perms");
        for(Topic t : topics){
            removeTopic(userID, t.getID());
        }
    }
    
    /**
     * Method to remove all questions from a test
     */
    public void removeAllQuestions(int userID) throws SQLException, IllegalArgumentException{
        if(!validatePerms(c, userID))
            throw new IllegalArgumentException("removeAllQuestions : test : perms");
        for(Question q : questions){
            removeQuestion(userID, q.getID());
        }
    }
}
