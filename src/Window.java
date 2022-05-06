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
    
    public Window( String title, int width, int height, Connection c){ //Connection c,
        frame = new JFrame(title);
        frame.setBounds(200, 100, width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.c = c;
        
        currentPane = new JPanel();
        frame.setLayout(null);
        
        //to be commented out after testing is completed
        //frame.setVisible(true);
    }
    
    public void LogIn(){
        frame.setVisible(false);
        frame.remove(currentPane);
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
    
    public void SignUp(){
        frame.setVisible(false);
        frame.remove(currentPane);
        JLabel user, pass, error;
        JTextField name;
        JPasswordField key;
        
        JPanel signUp = new JPanel();
        signUp.setSize(frame.getSize());
        signUp.setLayout(null);
        
        JButton enter = new JButton("Sign Up");
        enter.setBounds(frame.getWidth()/2-50, frame.getHeight()/2, 100, 30);
        signUp.add(enter);
        
        JButton back = new JButton("Back");
        enter.setBounds(50, frame.getHeight()-50, 100, 30);
        signUp.add(back);
        
        user = new JLabel("Set Username: ");
        user.setBounds(frame.getWidth()/2-100, frame.getHeight()/2-100, 100, 30);
        signUp.add(user);
        
        pass = new JLabel("Set Password: ");
        pass.setBounds(frame.getWidth()/2-100, frame.getHeight()/2-50, 100, 30);
        signUp.add(pass);
        
        error = new JLabel("Username or Password Invalid!");
        error.setBounds(frame.getWidth()/2-90, frame.getHeight()/2-150, 400, 30);
        error.setVisible(false);
        signUp.add(error);
        
        name = new JTextField();
        name.setBounds(frame.getWidth()/2, frame.getHeight()/2-100, 100, 30);
        signUp.add(name);
        
        key = new JPasswordField();
        key.setBounds(frame.getWidth()/2, frame.getHeight()/2-50, 100, 30);
        signUp.add(key);
        
        enter.addActionListener(new SignUpListener(this,c,error,name,key));
        back.addActionListener(new BackToLogInListener(this));
        currentPane = signUp;
        frame.add(signUp);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    
    public void Dashboard(User currentUser){
        frame.setVisible(false);
        frame.remove(currentPane);
    }
   
    public void AddTest1(User currentUser){
        frame.setVisible(false);
        frame.remove(currentPane);
        JLabel test, error;
        JList topics;
        JTextField testname;
        JButton addquestion, nextpage;
       
        JPanel addtest1 = new JPanel();
        addtest1.setSize(frame.getSize());
        addtest1.setLayout(null);
        
        
        nextpage = new JButton("Next");
        nextpage.setBounds(frame.getWidth()/2 +10,frame.getHeight()/8 -20, 100, 30);
        addtest1.add(nextpage);
        
        test = new JLabel("Test Name: ");
        test.setBounds(frame.getWidth()/2-500, frame.getHeight()/8 -20, 100, 30);
        addtest1.add(test);
        
        testname = new JTextField();
        testname.setBounds(frame.getWidth()/2 -400,frame.getHeight()/8 -20, 400, 30);
        addtest1.add(testname);
        /*
        topics = new JList(Search.search(topics));
        topics.setBounds(frame.getWidth()/2 ,frame.getHeight()/2 , 400, 400);
        addtest1.add(topics);
        */
        
        JButton exitbutton = new JButton("Exit");
        exitbutton.setBounds(frame.getWidth() -350,frame.getHeight()/20 , 150, 30);
        addtest1.add(exitbutton);
        
        nextpage.addActionListener(new AddTestListener(this, c, testname, currentUser));
        exitbutton.addActionListener(new BackToLogInListener(this));
        currentPane = addtest1;
        frame.add(addtest1);
        frame.setLayout(null);
        frame.setVisible(true);
        
        
        
    }
    
    
    public void EditTest(User currentUser, Test currentTest){
        frame.setVisible(false);
        frame.remove(currentPane);
    }
    
}
