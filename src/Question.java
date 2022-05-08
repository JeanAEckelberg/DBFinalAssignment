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
            userID = rs.getInt(3);
            
            rs.close();
        } catch (SQLException e) {
            System.err.println("Can't execute query.");
            System.exit(1);
            return;
        }
        answers = new ArrayList<>();
        topics = new ArrayList<>();
        pullAnswers(c);
        pullTopics(c);
    }
    
    private void pullAnswers(Connection c){
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
    
    private void pullTopics(Connection c){
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
    
    private boolean validatePerms(Connection c, int id) throws SQLException{
        int permLvl = User.getPermissionLevel(c, id);
        return permLvl > 0 || id == userID;
    }
    
    public String getText(){
        return questionText;
    }
    
    public int getID(){
        return questionID;
    }
    
    public void setText(Connection c, int id, String text)throws SQLException{
        
        if (!validatePerms(c, id)) return;
        
        String setText = "update question set questionText = ? where questionID = ?";
        
        PreparedStatement prepStmt;
        
        try{
             prepStmt = c.prepareStatement(setText);
             prepStmt.setString(1, text);
             prepStmt.setInt(2, questionID);
        } catch (SQLException e){
            throw new SQLException("Can't prep statement.");
        }
        
        try{
            prepStmt.executeUpdate();
            prepStmt.close();
        } catch (SQLException e){
            throw new SQLException("Can't execute statement.");
        }
        
        questionText = text;
    }
    
    public void addTopic(Connection c, int userID, int topicID) throws SQLException{
        if (!validatePerms(c, userID)) return;
        
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
    
    public void removeTopic(Connection c, int userID, int topicID) throws SQLException{
        if (!validatePerms(c, userID)) return;
        
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
    
    public Topic[] getTopics(Connection c, int userID) throws SQLException{
        if (!validatePerms(c, userID)) return null;
        Topic[] temp = new Topic[0];
        return topics.toArray(temp);
    }
    
    public void addAnswer(Connection c, int userID, int ansID, boolean correct) throws SQLException {
        if (!validatePerms(c, userID)) return;
        
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
        answers.add(temp);
    }
    
    public void removeAnswer(Connection c, int userID, int ansID) throws  SQLException{
        if (!validatePerms(c, userID)) return;
        
        //This may cause issues if removing isn't working
        Answer temp = null;
        for(Answer a : answers){
            if(a.getID() == ansID) temp = a;
        }
        if(temp == null) return;
        
        
        //Delete from linking table
        String decouple = "delete from answerToQuestion where questionID = ? and answerID = ?";

        PreparedStatement prepStmt = c.prepareStatement(decouple);
        prepStmt.setInt(1, questionID);
        prepStmt.setInt(2, ansID);

        prepStmt.executeUpdate();
        
        answers.remove(temp);
    }
    
    public Answer[] getAnswers(Connection c, int userID) throws SQLException{
        if (!validatePerms(c, userID)) return null;
        Answer[] temp = new Answer[0];
        return answers.toArray(temp);
    }
    
    public Integer getCorrectAns(){
        if(correctIndex == null || correctIndex >= answers.size()) return null;
        return answers.get(correctIndex).getID();
    }
    
    public void remove(Connection c, int id){
        
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
}
