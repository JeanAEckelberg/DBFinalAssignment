/**
 *
 * @author Jean, Greg, Carson
 */

import java.sql.*;
import javax.swing.*;

public class Window {
    JFrame frame;
    JPanel currentPane;
    Connection c;
    
    public Window( String title, int width, int height){ //Connection c,
        frame = new JFrame(title);
        frame.setBounds(200, 100, width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.c = c;
        
        currentPane = new JPanel();
        frame.setLayout(null);
        
        //to be commented out after testing is completed
        //frame.setVisible(true);
    }
    
    public void LogIn(){
        frame.setVisible(false);
        //frame.remove(currentPane);
        JLabel user, pass, error;
        JTextField name;
        JPasswordField key;
        
        JPanel logIn = new JPanel();
        logIn.setSize(frame.getSize());
        logIn.setLayout(null);
        
        JButton enter = new JButton("Log In");
        enter.setBounds(frame.getWidth()/2-50, frame.getHeight()/2, 100, 30);
        logIn.add(enter);
        
        user = new JLabel("Username: ");
        user.setBounds(frame.getWidth()/2-100, frame.getHeight()/2-100, 100, 30);
        logIn.add(user);
        
        pass = new JLabel("Password: ");
        pass.setBounds(frame.getWidth()/2-100, frame.getHeight()/2-50, 100, 30);
        logIn.add(pass);
        
        error = new JLabel("Username or Password Invalid!");
        error.setBounds(frame.getWidth()/2-90, frame.getHeight()/2-150, 400, 30);
        error.setVisible(false);
        logIn.add(error);
        
        name = new JTextField();
        name.setBounds(frame.getWidth()/2, frame.getHeight()/2-100, 100, 30);
        logIn.add(name);
        
        key = new JPasswordField();
        key.setBounds(frame.getWidth()/2, frame.getHeight()/2-50, 100, 30);
        logIn.add(key);
        
        enter.addActionListener(new LogInListener(this,c,error,name,key));
        currentPane = logIn;
        frame.add(logIn);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    
    public void Dashboard(User currentUser){
        frame.setVisible(false);
        frame.remove(currentPane);
    }
}
