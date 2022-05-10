
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author carso
 */
public class EditTestNameListener implements ActionListener{
    private Window ref;
    private Connection c;
    private Test currentTest;
    private JTextField name;
    private User currentUser;
    public EditTestNameListener(Window ref, Connection c, Test currentTest, User currentUser, JTextField name){
        this.ref = ref;
        this.c = c;
        this.currentTest = currentTest;
        this.name = name;
        this.currentUser = currentUser;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
            try {
                this.currentTest.setTestName(this.name.getText(),this.currentUser.getID());
            } catch (SQLException ex) {
               System.err.println("SQL error");
            } catch (IllegalArgumentException ex) {
                System.err.println("Not authorized");
            }
        ref.EditTestHome(currentUser, currentTest);
        
    }
    
}
