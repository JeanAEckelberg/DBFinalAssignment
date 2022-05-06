/**
 *
 * @author Jean
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Answer {
    private int answerID;
    private String answerText;
    
    public Answer(Connection c, int id) throws SQLException{
        String getAnswer = "Select * from answer where answerID = ?";
        PreparedStatement prepStmt;
        ResultSet rs;
        
        try{
             prepStmt = c.prepareStatement(getAnswer);
        } catch (SQLException e){
            throw new SQLException("Can't prep statement.");

        }
        
        try{
            prepStmt.setInt(1, id);
        } catch (SQLException e) {
            throw new SQLException("Can't set ID");
        }   
        
        
        try{
            rs = prepStmt.executeQuery();
            if(!rs.next()) throw new SQLException();
            answerID = rs.getInt(1);
            answerText = rs.getString(2);
            
            rs.close();
        } catch (SQLException e) {
            throw new SQLException("Can't execute query.");
        }
    }
    
    public String getText(){
        return answerText;
    }
    
    public int getID(){
        return answerID;
    }
    
    public void setText(Connection c, String text) throws SQLException{
        
        String setText = "update answer set answerText = ? where answerID = ?";
        
        PreparedStatement prepStmt;
        
        try{
             prepStmt = c.prepareStatement(setText);
             prepStmt.setString(1, text);
             prepStmt.setInt(2, answerID);
        } catch (SQLException e){
            throw new SQLException("Can't prep statement.");
        }
        
        try{
            prepStmt.executeUpdate();
            prepStmt.close();
        } catch (SQLException e){
            throw new SQLException("Can't execute statement.");
        }
        
        answerText = text;
    }
    
    public static int createAnswer(Connection c, int userID, String text) throws SQLException{
        String setText = "insert into answer(answerText, creator) values(?, ?)";
        String getText = "select answerID from answer where answerText = ?";
        
        ResultSet rs;
        PreparedStatement prepStmt;
        
        try{
             prepStmt = c.prepareStatement(setText);
             prepStmt.setString(1, text);
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
            prepStmt = c.prepareStatement(getText);
            prepStmt.setString(1, text);
            
            rs = prepStmt.executeQuery();
            if(!rs.next()) throw new SQLException();
            
            return rs.getInt(1);
        } catch (SQLException e){
            throw new SQLException("Can't prep or execute statement.");
        }
    }
}
