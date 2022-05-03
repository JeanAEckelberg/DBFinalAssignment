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
    
    public User(Connection c, String username, String password){
        String findUser = "Select UserID from userTable where username = ? and password = ?";
        
        Scanner sc = new Scanner(System.in);
        
        PreparedStatement prepStmt;
        ResultSet rs;
        
        String user;
        String pass;
        try{
             prepStmt = c.prepareStatement(findUser);
        } catch (SQLException e){
            System.err.println("Can't prep statement.");
            System.exit(1);
            return;
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
            System.err.println("Can't set username and password.");
            System.exit(1);
            return;
        }   
        
        
        try{
            rs = prepStmt.executeQuery();
            if(!rs.next()) throw new SQLException();
            id = rs.getInt(1);
            rs.close();
        } catch (SQLException e) {
            System.err.println("Can't execute query.");
            System.exit(1);
            return;
        }
        userName = user;
    }
    
    public int getID(){
        return id;
    }
    
    public String getUserName(){
        return userName;
    }
    
    public static void createUser(Connection c, String username, String password){
        String insertString = "insert into userTable(username, password) values(?,?)";
        
        
        Scanner sc = new Scanner(System.in);
        
        PreparedStatement prepStmt;
        
        try{
            prepStmt = c.prepareStatement(insertString);
            prepStmt.setString(1, username);
            prepStmt.setString(2, password);
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
    }
}
