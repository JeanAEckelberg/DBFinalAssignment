import java.util.Scanner;
import java.sql.*;
//ill finish this class tomorrow after test
public class Topic {
    private String name;
    private String description;
    private int topicId;
    Topic(Connection c, String desc, String name){
        Scanner sc = new Scanner(System.in);
        PreparedStatement prepStmt;
        ResultSet rs;
        String tempname;
        String tempdesc;

        String findTopic = "Select topicID from userTable where (name = ?, desc = ?)";
        try{
            prepStmt = c.prepareStatement(findTopic);
        } catch (SQLException e){
           System.err.println("Can't prep statement.");
           System.exit(1);
           return;
        }
        System.out.print("Enter Topic name: ");
        tempname = sc.nextLine();
        
        System.out.print("Enter Topic description: ");
        tempdesc = sc.nextLine();

        try{
            prepStmt.setString(1, tempname);
            prepStmt.setString(2, tempdesc);
        } catch (SQLException e) {
            System.err.println("Can't set topic name and topic description.");
            System.exit(1);
            return;
        }   

        
        
        try{
            rs = prepStmt.executeQuery();
            this.topicId = rs.getInt(1);
            rs.close();
        } catch (SQLException e) {
            System.err.println("Can't execute query.");
            System.exit(1);
            return;
        }
        this.name = tempname;
        this.description = tempdesc;


        

    }

    public static void createTopic(Connection c, String name, String description){
        String insertString = "insert into topic(topicName, topicDescription) values(?,?)";
        
        
        PreparedStatement prepStmt;
        
        try{
            prepStmt = c.prepareStatement(insertString);
            prepStmt.setString(1, name);
            prepStmt.setString(2, description);
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
