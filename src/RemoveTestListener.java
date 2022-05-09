
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
public class RemoveTestListener implements ActionListener {
    private Window ref;
    private Connection c;
    private User currentUser;
    private Test currentTest;
    public RemoveTestListener(Window ref, Connection c, User currentUser, Test currentTest){
        this.ref = ref;
        this.c = c;
        this.currentUser = currentUser;
        this.currentTest = currentTest;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            currentTest.removeTest(c, currentUser.getID());
        } catch (SQLException ex) {
            System.err.println("sqlerror");
            

        } catch (IllegalArgumentException ex) {
            System.err.println("Not authorized");
        }
        ref.Dashboard(currentUser);
        
    }
    
}
