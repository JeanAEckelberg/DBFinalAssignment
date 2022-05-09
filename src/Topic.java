
import java.sql.*;


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
    
    public void remove(Connection c, int id){
        
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
    
    public String getDescription(){
        return description;
    }
    
    public int getID(){
        return topicID;
    }
    public  String getName(Connection c) throws SQLException{
        PreparedStatement prepStmt;
        ResultSet rs;
        String returnedName;
        String findName = "Select topicName from topic where topicID = ?";
        try{
            prepStmt = c.prepareStatement(findName);

        }catch(SQLException e){
            System.err.println("Can't prep statment");
            System.exit(1);
            return null;
        }

        try{
            prepStmt.setInt(1, this.topicID);
        } catch(SQLException e){
            throw new SQLException("Can't set ID");
        }

        try{
            rs = prepStmt.executeQuery();
            if(!rs.next()) throw new SQLException("Nothing in result set");
            returnedName = rs.getString(1);
            rs.close();

            

        }catch(SQLException e){
            throw new SQLException("Can't execute query");
        }
        return returnedName;

    }

    public  void setName(Connection c, String newName) throws SQLException{
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

    public String getDesc(Connection c) throws SQLException{
        PreparedStatement prepStmt;
        ResultSet rs;
        String returnedDesc;
        String findDesc = "Select topicDescription from topic where topicID = ?";
        try{
            prepStmt = c.prepareStatement(findDesc);

        }catch(SQLException e){
            System.err.println("Can't prep statment");
            System.exit(1);
            return null;
        }

        try{
            prepStmt.setInt(1, this.topicID);
        } catch(SQLException e){
            throw new SQLException("Can't set ID");
        }

        try{
            rs = prepStmt.executeQuery();
            if(!rs.next()) throw new SQLException("Nothing in result set");
            returnedDesc = rs.getString(1);
            rs.close();

            

        }catch(SQLException e){
            throw new SQLException("Can't execute query");
        }
        return returnedDesc;

    }


    public void setDesc(Connection c, String newDesc) throws SQLException{
        PreparedStatement prepStmt;
        String setDesc = "update topic set topicDescription = ? where topicID = ?";
        try{
            prepStmt = c.prepareStatement(setDesc);

        }catch(SQLException e){
            System.err.println("Can't prep statment");
            System.exit(1);
            return;
        }

        try{
            prepStmt.setString(1, newDesc);
            prepStmt.setInt(2, this.topicID);

        } catch(SQLException e){
            throw new SQLException("Can't set ID or desc");
        }

        try{
            prepStmt.executeQuery();
        }catch(SQLException e){
            throw new SQLException("Can't execute query");
        }
       
    }


    




  
    
    public int getCreator(){
        return userID;
    }
    
    public String getTopicName(){
        return name;
    }
    
    private boolean validatePerms(Connection c, int userID) throws SQLException{
        int permLVL = User.getPermissionLevel(c, userID);
        return permLVL > 0 || userID == this.userID;
    }
    
    /**
     * Make sure this is called only after all questions have been removed and the topic has been removed from the tests
     * @param c
     * @param userID
     * @throws IllegalArgumentException
     * @throws SQLException 
     */
    public void removeTopic(Connection c, int userID) throws IllegalArgumentException, SQLException{
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
    
    public void setTopicName(Connection c, int userID, String name) throws SQLException, IllegalArgumentException{
        if (!validatePerms(c, userID)) throw new IllegalArgumentException("setTopicName : topic : perms");
        
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
    
    public void setDescription(Connection c, int userID, String desc) throws SQLException, IllegalArgumentException{
        if (!validatePerms(c, userID)) throw new IllegalArgumentException("setDescription : topic : perms");
        
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
