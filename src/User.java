/**
 *
 * @author Jean
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
    private int id;
    private String userName;
    
    public User(Connection c, String username, String password) throws SQLException{
        String findUser = "Select UserID from userTable where username = ? and password = ?";
        
        Scanner sc = new Scanner(System.in);
        
        PreparedStatement prepStmt;
        ResultSet rs;
        
        String user;
        String pass;
        try{
             prepStmt = c.prepareStatement(findUser);
        } catch (SQLException e){
            throw new SQLException("Can't prep statement.");
        }
        
        //System.out.print("Enter Username: ");
        //user = sc.nextLine();
        user = username;
        
        //System.out.print("Enter Password: ");
        //pass = sc.nextLine();
        pass = password;
        
        try{
            prepStmt.setString(1, user);
            prepStmt.setString(2, pass);
        } catch (SQLException e) {
            throw new SQLException("Can't set username and password.");
        }   
        
        
        try{
            rs = prepStmt.executeQuery();
            if(!rs.next()) throw new SQLException();
            id = rs.getInt(1);
            rs.close();
        } catch (SQLException e) {
            throw new SQLException("Can't execute query.");
        }
        userName = user;
    }
    
    public int getID(){
        return id;
    }
    
    public String getUserName(){
        return userName;
    }
    
    public static void createUser(Connection c, String username, String password) throws SQLException{
        String insertString = "insert into userTable(username, password) values(?,?)";
        
        
        Scanner sc = new Scanner(System.in);
        
        PreparedStatement prepStmt;
        
        try{
            prepStmt = c.prepareStatement(insertString);
            prepStmt.setString(1, username);
            prepStmt.setString(2, password);
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
    
    public static int getPermissionLevel(Connection c, int id) throws SQLException{
        String getPerm = "Select permissions from userTable where userID = ?";
        
        PreparedStatement prepStmt;
        ResultSet rs;
        
        try{
             prepStmt = c.prepareStatement(getPerm);
        } catch (SQLException e){
            throw new SQLException("Can't prep statement.");
        }
        
        try{
            prepStmt.setInt(1, id);
        } catch (SQLException e) {
            throw new SQLException("Can't set ID.");
        }  
        
        try{
            rs = prepStmt.executeQuery();
            if(!rs.next()) throw new SQLException();
            id = rs.getInt(1);
            rs.close();
            return id;
        } catch (SQLException e) {
            throw new SQLException("Can't execute query.");
        }
    }
}
