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
    
    /**
     * Search page UI
     * @param currentUser user using the page
     */
    public void SearchPage(User currentUser){
        
        /*
        The idea with this method is to provide a search page that allows a user
         to querey the db and filter the results
        for questions and topics the edit and delete buttons should be diplayed and
        associated with each instance of the topics and questions
        the same for tests, except users can also take tests.
        there will be between 5 ans 10 results on each page and the user will be able to 
        page through the results of their search
        */
        
        frame.setVisible(false);
        frame.remove(currentPane);
        
        JLabel notFound;
        JRadioButton[] filters = new JRadioButton[3];
        JButton takeTest, edit, delete, next, previous;
        JLabel[] results;
        JTextField querey;
        
        // under construction
        JPanel resultsPage = new JPanel();
        resultsPage.setSize(frame.getSize());
        resultsPage.setLayout(null);
        
        JButton enterQuerey = new JButton("Search");
        enterQuerey.setBounds(frame.getWidth()- 150, frame.getHeight()%4 + 2, 100, 30);
        resultsPage.add(enterQuerey);
        
        querey = new JTextField();
        querey.setBounds(1, 1, 500, 20);
        resultsPage.add(querey);
        
        filters[0] = new JRadioButton("Test");
        filters[1] = new JRadioButton("Topic");
        filters[2] = new JRadioButton("Question");
        
        currentPane = resultsPage;
        frame.add(resultsPage);
        frame.setLayout(null);
        frame.setVisible(true);
        
    }
}
