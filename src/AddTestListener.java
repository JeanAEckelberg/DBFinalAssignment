/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author carso
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.*;

public class AddTestListener implements ActionListener{

    private JTextField testname;
 
    private User currentUser;
    private Connection c;
    private Window ref;
    
    public AddTestListener(Window ref, Connection c, JTextField testname, User currentUser){
        this.c = c;
        this.testname = testname;
        this.ref = ref;
        this.currentUser = currentUser;
       
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        /*
        User currentUser;
        try{
            currentUser = new User(c, user.getText(), pass.getText());
            ref.Dashboard(currentUser);
        } catch (Exception f){
            pass.selectAll();
            errorMsg.setVisible(true);
        }
*/
        
    }
    
    
}
