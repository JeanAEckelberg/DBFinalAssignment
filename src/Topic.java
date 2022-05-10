
import java.sql.*;


public class Topic {
    private String name;
    private String description;
    private int topicID;
    private int userID;
    Connection c;
    
    
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
            if(rs.wasNull()) name = "";
            description = rs.getString(3);
            if(rs.wasNull()) description = "";
            userID = rs.getInt(4);
            this.c = c;
            rs.close();
        } catch (SQLException e) {
            throw new SQLException("Can't execute query.");
        }    

    }

    public static void createTopic(Connection c, String name, String description, int userID) throws SQLException, IllegalArgumentException{
        if (User.getPermissionLevel(c, userID) < 1) throw new IllegalArgumentException("createTopic : topic : perms");
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
    
    public int getID(){
        return topicID;
    }

    public  String getName() {
        return name;
    }

    /*
    public  void setName(String newName) throws SQLException{
        PreparedStatement prepStmt;
        String setName = "update topic set topicName = ? where topicID = ?";
        try{
            prepStmt = c.prepareStatement(setName);

        }catch(SQLException e){
            System.err.println("Can't prep statment");
            System.exit(1);
            return;
        }

        try{
            prepStmt.setString(1, newName);
            prepStmt.setInt(2, this.topicID);

        } catch(SQLException e){
            throw new SQLException("Can't set ID or name");
        }

        try{
            prepStmt.executeQuery();
        }catch(SQLException e){
            throw new SQLException("Can't execute query");
        }
       
    }
    */

    
    public String getDesc() throws SQLException{
        return description;
    }

    public int getCreator(){
        return userID;
    }
    
    private boolean validatePerms(int userID) throws SQLException{
        int permLVL = User.getPermissionLevel(c, userID);
        return permLVL > 0 || userID == this.userID;
    }
    
    /**
     * Make sure this is called only after all questions have been removed and the topic has been removed from the tests
     * @param userID
     * @throws IllegalArgumentException
     * @throws SQLException 
     */
    public void remove( int userID) throws IllegalArgumentException, SQLException{
        if (userID != this.userID || User.getPermissionLevel(c, userID) < 1) 
            throw new IllegalArgumentException("removeTopic : topic : perms");
        
        String rStr = "delete from topic where topicID = " + getID();
        PreparedStatement rStmt;
        
        try{
            rStmt = c.prepareStatement(rStr);
        }
        catch (SQLException e){
            throw new SQLException("can't prep statement in removeTopic of Topic");
        }
        
        try{
            rStmt.executeUpdate();
        }
        catch (SQLException e){
            throw new SQLException("can't execute statement in removeTopic of Topic");
        }
    }
    
    public void setName(int userID, String name) throws SQLException, IllegalArgumentException{
        if (!validatePerms(userID)) throw new IllegalArgumentException("setTopicName : topic : perms");
        
        String updateStr = "update topic set topicName = ? where topicID = " + getID();
        PreparedStatement update;
        
        try{
            update = c.prepareStatement(updateStr);
        }
        catch (SQLException e){
            throw new SQLException("can't prep stmt in setTopicName in Topic");
        }
        
        try{
            update.setString(1, name);
        }
        catch (SQLException e){
            throw new SQLException("can't set param in stmt in setTopicName in Topic");
        }
        
        try{
            update.executeUpdate();
        }
        catch (SQLException e){
            throw new SQLException("can't execute stmt in setTopicName in Topic");
        }
    }
    
    public void setDesc(int userID, String desc) throws SQLException, IllegalArgumentException{
        if (!validatePerms(userID)) throw new IllegalArgumentException("setDescription : topic : perms");
        
        String updateString = "update topic set topicDescription = ? where topicID = " + getID();
        PreparedStatement update;
        
        try{
            update = c.prepareStatement(updateString);
        }
        catch(SQLException e){
            throw new SQLException("can't prep stmt in setDescription in Topic");
        }
        
        try{
            update.setString(1, description);
        }
        catch(SQLException e){
            throw new SQLException("can't set string in setDescription in Topic");
        }
        
        try{
            update.executeUpdate();
        }
        catch(SQLException e){
            throw new SQLException("can't perform update in setDescription in Topic");
        }
    }
}
