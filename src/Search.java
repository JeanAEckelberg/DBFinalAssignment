/**
 *
 * @author Jean
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;

public class Search {
    private Connection c;
    
    public Search(Connection c){ this.c = c; }
    
    public ResultSet Topics(String query) throws SQLException{
        String getTopics = "call getTopics(?)";
        
        CallableStatement cStmt = c.prepareCall(getTopics);
        cStmt.setString(1, query);
        ResultSet rs = cStmt.executeQuery();
        //rs.setFetchSize(1000);
        System.out.println(rs.getFetchSize());
        return rs;
    }
    
    public ResultSet Questions(String query) throws SQLException{
        String getQuestions = "select getQuestions(?)";
        
        PreparedStatement prepStmt = c.prepareStatement(getQuestions);
        prepStmt.setString(1, query);
        
        return prepStmt.executeQuery();
    }
    
    public ResultSet Tests(String query) throws SQLException{
        String getTests = "select getTests(?)";
        
        PreparedStatement prepStmt = c.prepareStatement(getTests);
        prepStmt.setString(1, query);
        
        return prepStmt.executeQuery();
    }
    
    public ResultSet QuestionsByTopic(int topicID) throws SQLException{
        String getQuestionsByTopic = "select getQuestionsInTopicByID(?)";
        
        PreparedStatement prepStmt = c.prepareStatement(getQuestionsByTopic);
        prepStmt.setInt(1, topicID);
        
        return prepStmt.executeQuery();
    }
}
