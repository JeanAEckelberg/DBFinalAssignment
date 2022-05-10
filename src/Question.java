/**
 *
 * @author Jean
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Question {
    private int questionID;
    private String questionText;
    private int userID;
    private Integer correctIndex;
    private Connection c;

    
    private ArrayList<Answer> answers;
    private ArrayList<Topic> topics;
    
    public Question(Connection c, int id){
        correctIndex = null;
        String getQuestion = "Select * from question where questionID = ?";
        PreparedStatement prepStmt;
        ResultSet rs;
        
        try{
             prepStmt = c.prepareStatement(getQuestion);
        } catch (SQLException e){
            System.err.println("Can't prep statement.");
            System.exit(1);
            return;
        }
        
        try{
            prepStmt.setInt(1, id);
        } catch (SQLException e) {
            System.err.println("Can't set ID");
            System.exit(1);
            return;
        }   
        
        
        try{
            rs = prepStmt.executeQuery();
            if(!rs.next()) throw new SQLException();
            questionID = rs.getInt(1);
            questionText = rs.getString(2);
            if(rs.wasNull()) questionText = "";
            userID = rs.getInt(3);
            
            rs.close();
        } catch (SQLException e) {
            System.err.println("Can't execute query.");
            System.exit(1);
            return;
        }
        answers = new ArrayList<>();
        topics = new ArrayList<>();
        this.c = c;
        pullAnswers();
        pullTopics();

    }
    
    private void pullAnswers(){
        String getAnswers = "Select answerID, correct from answerToQuestion where questionID = ?";
        PreparedStatement prepStmt;
        ResultSet rs;
        
        try{
             prepStmt = c.prepareStatement(getAnswers);
        } catch (SQLException e){
            System.err.println("Can't prep statement.");
            System.exit(1);
            return;
        }
        
        try{
            prepStmt.setInt(1, questionID);
        } catch (SQLException e) {
            System.err.println("Can't set ID");
            System.exit(1);
            return;
        }   
        
        
        try{
            rs = prepStmt.executeQuery();
            int i = 0;
            while(rs.next()){
                answers.add(new Answer(c,rs.getInt(1)));
                if(rs.getBoolean(2)) correctIndex = i;
                i++;
            }
            
            rs.close();
        } catch (SQLException e) {
            System.err.println("Can't execute query.");
            System.exit(1);
        }
    }
    
    private void pullTopics(){
        String getTopics = "Select topicID from questionInTopic where questionID = ?";
        PreparedStatement prepStmt;
        ResultSet rs;
        
        try{
             prepStmt = c.prepareStatement(getTopics);
        } catch (SQLException e){
            System.err.println("Can't prep statement.");
            System.exit(1);
            return;
        }
        
        try{
            prepStmt.setInt(1, questionID);
        } catch (SQLException e) {
            System.err.println("Can't set ID");
            System.exit(1);
            return;
        }   
        
        
        try{
            rs = prepStmt.executeQuery();
            while(rs.next()){
                topics.add(new Topic(c,rs.getInt(1)));
            }
            
            rs.close();
        } catch (SQLException e) {
            System.err.println("Can't execute query.");
            System.exit(1);
        }
    }
    
    private boolean validatePerms(int id) throws SQLException{
        int permLvl = User.getPermissionLevel(c, id);
        return permLvl > 0 || id == userID;
    }
    
    public String getText(){
        return questionText;
    }
    
    public int getID(){
        return questionID;
    }
    

    public void setText(int id, String text) throws SQLException, IllegalArgumentException {

        
        if (!validatePerms(id)) throw new IllegalArgumentException("setText : question : perms");
        
        String setText = "update question set questionText = ? where questionID = ?";
        
        PreparedStatement prepStmt;
        
        try{
             prepStmt = c.prepareStatement(setText);
             prepStmt.setString(1, text);
             prepStmt.setInt(2, questionID);
        } catch (SQLException e){
            System.err.println("Can't prep statement.");
            System.exit(1);
            return;
        }
        
        try{
            prepStmt.executeUpdate();
            prepStmt.close();
        } catch (SQLException e){
            System.err.println("Can't execute statement.");
            System.exit(1);
            return;
        }
        
        questionText = text;
    }
    
    public void addTopic(int userID, int topicID) throws SQLException, IllegalArgumentException{
        if (!validatePerms(userID)) throw new IllegalArgumentException("addTopic : question : perms");
        
        for(Topic t : topics){
            if(t.getID() == topicID) return;
        }
        
        Topic temp = new Topic(c,topicID);
        
        //Add to linking table
        String linkUp = "insert into questionInTopic values(?,?)";

        PreparedStatement prepStmt = c.prepareStatement(linkUp);
        prepStmt.setInt(1, questionID);
        prepStmt.setInt(2, topicID);

        prepStmt.executeUpdate();
        
        topics.add(temp);
    }
    
    public void removeTopic(int userID, int topicID) throws SQLException, IllegalArgumentException{
        if (!validatePerms(userID)) throw new IllegalArgumentException("removeTopic : question : perms");
        
        //This may cause issues if removing isn't working
        Topic temp = null;
        for(Topic t : topics){
            if(t.getID() == topicID) temp = t;
        }
        if(temp == null) return;
        
        
        //Delete from linking table
        String decouple = "delete from questionInTopic where questionID = ? and topicID = ?";

        PreparedStatement prepStmt = c.prepareStatement(decouple);
        prepStmt.setInt(1, questionID);
        prepStmt.setInt(2, topicID);

        prepStmt.executeUpdate();
        
        topics.remove(temp);
    }
    
    public Topic[] getTopics() throws SQLException, IllegalArgumentException{
        Topic[] temp = new Topic[0];
        return topics.toArray(temp);
    }
    
    public void addAnswer(int userID, int ansID, boolean correct) throws SQLException, IllegalArgumentException {
        if (!validatePerms(userID)) throw new IllegalArgumentException("addAnswer : question : perms");
        System.out.println(answers.size());
        for(Answer a : answers){
            if(a.getID() == ansID) return;
        }
        
        Answer temp = new Answer(c,ansID);
        
        //Add to linking table
        String linkUp = "insert into answerToQuestion values(?,?,?)";

        PreparedStatement prepStmt = c.prepareStatement(linkUp);
        prepStmt.setInt(1, questionID);
        prepStmt.setInt(2, ansID);
        prepStmt.setBoolean(3, correct);

        prepStmt.executeUpdate();
        if(correct) correctIndex = answers.size();
        System.out.println(correctIndex);
        answers.add(temp);
    }
    
    public void removeAnswer(int userID, int ansID) throws  SQLException, IllegalArgumentException{
        if (!validatePerms(userID)) throw new IllegalArgumentException("removeAnswer : question : perms");
        
        //This may cause issues if removing isn't working
        Answer temp = null;

        for(Answer a : answers){
            if(a.getID() == ansID) temp = a;
        }
        
        if(temp == null) return;
        
        if(correctIndex == null){}
        else if(answers.indexOf(temp) == correctIndex){ correctIndex = null; }
        else if(answers.indexOf(temp) < correctIndex) { correctIndex--; }
        
        //Delete from linking table
        String decouple = "delete from answerToQuestion where questionID = ? and answerID = ?";

        PreparedStatement prepStmt = c.prepareStatement(decouple);
        prepStmt.setInt(1, questionID);
        prepStmt.setInt(2, ansID);

        prepStmt.executeUpdate();
        
        // delete answer itself
        temp.remove(userID);
        
        answers.remove(temp);
    }
    

    public Answer[] getAnswers() throws SQLException {
        Answer[] temp = new Answer[0];
        return answers.toArray(temp);
    }
    
    public Integer getCorrectAns(){
        if(correctIndex == null || correctIndex >= answers.size()) return null;
        return answers.get(correctIndex).getID();
    }
    
    
    public static int createQuestion(Connection c, 
            int userID, String qText) throws SQLException{
        String setQuestion = "insert into question(questionText, creator) values(?, ?)";
        String getQuestion = "select questionID from question where questionText = ?";
        
        ResultSet rs;
        PreparedStatement prepStmt;

        try{
             prepStmt = c.prepareStatement(setQuestion);
             prepStmt.setString(1, qText);
             prepStmt.setInt(2, userID);
             
        } catch (SQLException e){
            throw new SQLException("Can't prep statement.");
        }
        
        try{
            prepStmt.executeUpdate();
            prepStmt.close();
            
        } catch (SQLException e){
            throw new SQLException("Can't execute statement.");
        }
        
        try{
            prepStmt = c.prepareStatement(getQuestion);
            prepStmt.setString(1, qText);
            
            rs = prepStmt.executeQuery();
            if(!rs.next()) throw new SQLException();
            
            return rs.getInt(1);
        } catch (SQLException e){
            throw new SQLException("Can't prep or execute statement.");
        }
    }
    
    public void remove( int userID)throws SQLException, IllegalArgumentException{
        if (!validatePerms(userID)) 
            throw new IllegalArgumentException("removeQuestion : question : perms");
        Topic[] topics = getTopics();
        for(Topic t : topics) removeTopic(userID, t.getID());
        Answer[] answers = getAnswers();
        for(Answer a : answers){
            removeAnswer(userID, a.getID());
            a.remove(userID);
        }
        
        String deleteStr = "delete from question where questionID = ? " ;
        PreparedStatement stmt;
        
        try{
            stmt = c.prepareStatement(deleteStr);
            stmt.setInt(1, questionID);
        }
        catch (SQLException e){
            throw new SQLException("can't prep stmt removeQuestion");
        }
        
        try{
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e){
            throw new SQLException("can't execute stmt removeQuestion");
        }
        
        
    }
    
    public void removeAllTopics(int userID) throws SQLException, IllegalArgumentException{
        if (!validatePerms(userID)) 
            throw new IllegalArgumentException("removeAllTopics : question : perms");
        
        Topic[] topics = getTopics();
        for (Topic t : topics){
            removeTopic(userID, t.getID());
        }
        
    }
}
