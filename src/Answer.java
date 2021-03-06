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
    private int creatorID;
    private Connection c;
    
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
            if(rs.wasNull()) answerText = "";
            creatorID = rs.getInt(3);
            this.c = c;
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
    
    public int getCreator(){
        return creatorID;
    }
    
    private boolean validatePerms(int userID) throws SQLException{
        int permLVL = User.getPermissionLevel(c, userID);
        return permLVL > 1 || userID == creatorID;
    }
    
    public void setText(String text, int userID) throws SQLException, IllegalArgumentException{
        if (!validatePerms(userID)) throw new IllegalArgumentException("answer : setText : permissions");
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
    
    public void remove(int userID) throws SQLException, IllegalArgumentException{
        if (!validatePerms(userID)) 
            throw new IllegalArgumentException("deleteAnswer : answer : perms");
        String breakStr = "delete from answerToQuestion where answerID = " + getID();
        PreparedStatement breakStmt;
        
        try{
            breakStmt = c.prepareStatement(breakStr);
        }
        catch (SQLException e){
            throw new SQLException("can't prep stmt deleteAnswer");
        }
        
        try{
            breakStmt.executeUpdate();
        }
        catch (SQLException e){
            throw new SQLException("can't execute stmt deleteAnswer");
        }
        
        String removeStr = "delete from answer where answerID = " + getID();
        PreparedStatement remove;
        
        try{
            remove = c.prepareStatement(removeStr);
        }
        catch (SQLException e){
            throw new SQLException("can't prep stmt deleteAnswer");
        }
        
        try{
            remove.executeUpdate();
        }
        catch (SQLException e){
            throw new SQLException("cant execute stmt deletAnswer");
        }
        
    }
}
