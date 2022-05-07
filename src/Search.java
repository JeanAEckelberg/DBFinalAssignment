/**
 *
 * @author Jean
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.Types;
import java.sql.ResultSet;

public class Search {
    private Connection c;
    
    public Search(Connection c){ this.c = c; }
    
    public ResultSet Topics(String query) throws SQLException{
        String getTopics = "call getTopics(?)";
        
        CallableStatement cstmt = c.prepareCall(getTopics);
        cstmt.setString(1, query);
        
        return cstmt.executeQuery();
    }
    
    public ResultSet Questions(String query) throws SQLException{
        String getQuestions = "call getQuestions(?)";
        
        CallableStatement cstmt = c.prepareCall(getQuestions);
        cstmt.setString(1, query);
        
        return cstmt.executeQuery();
    }
}
