import java.util.Scanner;
import java.sql.*;
//ill finish this class tomorrow after test
public class Topic {
    private String name;
    private String description;
    private int topicID;
    private int userID;
    
    
    Topic(Connection c, int id) throws SQLException{
        PreparedStatement prepStmt;
        ResultSet rs;

        String findTopic = "Select * from topic where topicID = ?";
        try{
            prepStmt = c.prepareStatement(findTopic);
        } catch (SQLException e){
           System.err.println("Can't prep statement.");
           System.exit(1);
           return;
        }

        try{
            prepStmt.setInt(1, id);
        } catch (SQLException e) {
            throw new SQLException("Can't set ID.");
        }   

        
        
        try{
            rs = prepStmt.executeQuery();
            
            if(!rs.next()) throw new SQLException();
            
            topicID = rs.getInt(1);
            name = rs.getString(2);
            description = rs.getString(3);
            userID = rs.getInt(4);
            
            rs.close();
        } catch (SQLException e) {
            throw new SQLException("Can't execute query.");
        }    

    }

    public static void createTopic(Connection c, String name, String description, int userID) throws SQLException{
        String insertString = "insert into topic(topicName, topicDescription, creator) values(?,?,?)";
        
        
        PreparedStatement prepStmt;
        
        try{
            prepStmt = c.prepareStatement(insertString);
            prepStmt.setString(1, name);
            prepStmt.setString(2, description);
            prepStmt.setInt(3, userID);
        } catch (SQLException e){
            throw new SQLException("Can't prep statement.");
        }
        
        try{
            prepStmt.executeUpdate();
            prepStmt.close();
        } catch (SQLException e){
            throw new SQLException("Can't execute statement.");
        }
    }
    
    public String getDescription(){
        return description;
    }
    
    public int getID(){
        return topicID;
    }
    
}
