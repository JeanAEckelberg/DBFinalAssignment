/**
 *
 * @author Jean, Greg, Carson
 */

import java.sql.*;
import java.util.ArrayList;
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
        JLabel search, greeting, menu;
        JTextField query;
        JPanel dashboard = new JPanel();
        dashboard.setSize(frame.getSize());
        dashboard.setLayout(null);
        
        greeting = new JLabel("Hello "+ currentUser.getUserName() + "!");
        greeting.setBounds(frame.getWidth()/2-500, frame.getHeight()/20 -20, 100, 30);
        dashboard.add(greeting);
        
        JButton searchbutton = new JButton("Search");
        searchbutton.setBounds(frame.getWidth()/2 +10,frame.getHeight()/8 -20, 100, 30);
        dashboard.add(searchbutton);
        
        search = new JLabel("Search Query ");
        search.setBounds(frame.getWidth()/2-500, frame.getHeight()/8 -20, 100, 30);
        dashboard.add(search);
        
        query = new JTextField();
        query.setBounds(frame.getWidth()/2 -400,frame.getHeight()/8 -20, 400, 30);
        dashboard.add(query);
        
        menu = new JLabel("Menu");
        menu.setBounds(frame.getWidth()/2 +20, frame.getHeight()/4 -20, 100, 30);
        dashboard.add(menu);
        
        
        JButton createTopic = new JButton("Create Topic");
        createTopic.setBounds(frame.getWidth()/2 +80,frame.getHeight()/3 -20, 150, 30);
        dashboard.add(createTopic);
        
        JButton createTest = new JButton("Create Test");
        createTest.setBounds(frame.getWidth()/2 -160,frame.getHeight()/3 -20, 150, 30);
        dashboard.add(createTest);
        
        JButton createQuestion = new JButton("Create Question");
        createQuestion.setBounds(frame.getWidth()/2 -40,frame.getHeight()/3 +30, 150, 30);
        dashboard.add(createQuestion);
        
        JButton exitbutton = new JButton("Exit");
        exitbutton.setBounds(frame.getWidth() -350,frame.getHeight()/20 , 150, 30);
        dashboard.add(exitbutton);
        
        searchbutton.addActionListener(new SearchListener(this,c, query));
        createTopic.addActionListener(new CreateTopicListener(this));
        createTest.addActionListener(new CreateTestListener(this));
        createQuestion.addActionListener(new CreateQuestionListener(this));
        exitbutton.addActionListener(new BackToLogInListener(this));
        currentPane = dashboard;
        frame.add(dashboard);
        frame.setLayout(null);
        frame.setVisible(true);
        
    }
   
    public void AddTest1(User currentUser){
        frame.setVisible(false);
        frame.remove(currentPane);
        JLabel test, error;
        JList topics;
        JTextField testname;
        JButton addquestion, nextpage;
        ArrayList<Integer> idsOfTopics = new ArrayList<Integer>();
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
        
        //temp
        Search s = new Search(c);
        DefaultListModel listModel = new DefaultListModel();
        try{
            ResultSet topicRS = s.Topics("");
            int count = 0;
            while(topicRS.next()){
                idsOfTopics.add(topicRS.getInt(1));
                Topic topic = new Topic(c, topicRS.getInt(1));
                listModel.addElement( topic.getTopicName());
                count++;
            }
        }catch(SQLException sasd){
            listModel.addElement("No topics found");
        }
        
        
        
        topics = new JList(listModel);
        topics.setBounds(frame.getWidth()/2 ,frame.getHeight()/2 , 400, 400);
        addtest1.add(topics);
        /*
        topics = new JList(Search.search(topics));
        topics.setBounds(frame.getWidth()/2 ,frame.getHeight()/2 , 400, 400);
        addtest1.add(topics);
        */
        
        JButton exitbutton = new JButton("Exit");
        exitbutton.setBounds(frame.getWidth() -350,frame.getHeight()/20 , 150, 30);
        addtest1.add(exitbutton);
        
        nextpage.addActionListener(new AddTestListener(this, c, testname, currentUser, idsOfTopics, topics));
        exitbutton.addActionListener(new BackToDashboardListener(this, currentUser));
        currentPane = addtest1;
        frame.add(addtest1);
        frame.setLayout(null);
        frame.setVisible(true);
        
        
        
    }
     public void AddTest2(User currentUser, String testname, int topicId){
        frame.setVisible(false);
        frame.remove(currentPane);
        
        JPanel addtest2 = new JPanel();
        addtest2.setSize(frame.getSize());
        addtest2.setLayout(null);
        JButton create;
        JList questions;
        ArrayList<Integer> questionIDList = new ArrayList<Integer>();
        

        //temporary
        Search s = new Search(c);
        DefaultListModel listModel = new DefaultListModel();
        try{
            System.out.println(topicId);
            ResultSet questionRS = s.QuestionsByTopic(topicId);
            while(questionRS.next()){
                Question q = new Question(c, questionRS.getInt(1));
                questionIDList.add(questionRS.getInt(1));
                listModel.addElement(q.getText());
                
            }
        }catch(SQLException e){}
        
        
        questions = new JList(listModel);
        questions.setBounds(frame.getWidth()/2 ,frame.getHeight()/2 , 400, 400);
        questions.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        addtest2.add(questions);
        
        create = new JButton("Create");
        create.setBounds(frame.getWidth()/2 +10,frame.getHeight()/8 -20, 100, 30);
        addtest2.add(create);
        
         JButton exitbutton = new JButton("Exit");
        exitbutton.setBounds(frame.getWidth() -350,frame.getHeight()/20 , 150, 30);
        addtest2.add(exitbutton);
        
        JLabel selectquestions = new JLabel("Select Questions");
        selectquestions.setBounds(frame.getWidth()/2 -500,frame.getHeight()/8 -20, 100, 30);
        addtest2.add(selectquestions);
        
        //will go back to dashboard later
        
        exitbutton.addActionListener(new BackToDashboardListener(this, currentUser));
        create.addActionListener(new AddTest2Listener(this, c, currentUser, testname, topicId, questions, questionIDList));
        exitbutton.addActionListener(new BackToDashboardListener(this, currentUser));
        currentPane = addtest2;
        frame.add(addtest2);
        frame.setLayout(null);
        frame.setVisible(true);
     }
    
    
    
    public void EditTest(User currentUser, Test currentTest){
        frame.setVisible(false);
        frame.remove(currentPane);
    }
    
}
