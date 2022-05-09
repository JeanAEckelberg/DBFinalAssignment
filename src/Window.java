/**
 *
 * @author Jean, Greg, Carson
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        topics.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
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
     public void AddTest2(User currentUser, String testname, ArrayList<Integer> topicIds){
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
           for(int i = 0; i < topicIds.size(); i++){
                ResultSet questionRS = s.QuestionsByTopic(topicIds.get(i));
                while(questionRS.next()){
                    Question q = new Question(c, questionRS.getInt(1));
                    questionIDList.add(questionRS.getInt(1));
                    listModel.addElement(q.getText());

                }
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
        create.addActionListener(new AddTest2Listener(this, c, currentUser, testname, topicIds, questions, questionIDList));
        exitbutton.addActionListener(new BackToDashboardListener(this, currentUser));
        currentPane = addtest2;
        frame.add(addtest2);
        frame.setLayout(null);
        frame.setVisible(true);
     }
    
    
    
    public void EditTestHome(User currentUser, Test currentTest){
        frame.setVisible(false);
        frame.remove(currentPane);
        JButton removeQuestions, addQuestions, removeTopics, addTopics, removeTest, editname  ;
        
        
        JPanel edittesthome = new JPanel();
        edittesthome.setSize(frame.getSize());
        edittesthome.setLayout(null);
        
        
        editname = new JButton("Edit Name");
        editname.setBounds(frame.getWidth()/2 +10,frame.getHeight()/2 -500, 100, 30);
        edittesthome.add(editname);
        
        
        removeQuestions = new JButton("Remove Questions");
        removeQuestions.setBounds(frame.getWidth()/2 +10,frame.getHeight()/2 -300, 100, 30);
        edittesthome.add(removeQuestions);
        
        addQuestions = new JButton("Add Questions");
        addQuestions.setBounds(frame.getWidth()/2 +10,frame.getHeight()/2 -100, 100, 30);
        edittesthome.add(addQuestions);
        
        removeTopics = new JButton("Remove Topics");
        removeTopics.setBounds(frame.getWidth()/2 +10,frame.getHeight()/2 +100, 100, 30);
        edittesthome.add(removeTopics);
        
        addTopics = new JButton("Add Topics");
        addTopics.setBounds(frame.getWidth()/2 +10,frame.getHeight()/2 +300, 100, 30);
        edittesthome.add(addTopics);
        
        removeTest = new JButton("REMOVE TEST");
        removeTest.setBounds(frame.getWidth()/2 +10,frame.getHeight()/2 +500, 100, 30);
        edittesthome.add(removeTest);
        Window ref = this;
     
        
        
        editname.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                ref.EditTestName(currentUser, currentTest);
            }
        
        });
        
        removeQuestions.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                ref.RemoveTestQuestions(currentUser, currentTest);
            }
        
        });
        
        addQuestions.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                ref.AddTestQuestions(currentUser, currentTest);
            }
        
        });
        
        removeTopics.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                ref.RemoveTestTopics(currentUser, currentTest);
            }
        
        });
        
        addTopics.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                ref.AddTestTopics(currentUser, currentTest);
            }
        
        });
        
        removeTest.addActionListener(new RemoveTestListener(this, c, currentUser, currentTest));
        
        currentPane = edittesthome;
        frame.add(edittesthome);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    
    
    public void EditTestName(User currentUser, Test currentTest){
        frame.setVisible(false);
        frame.remove(currentPane);
        JTextField name;
        JButton save;
        
        JPanel edittestname  = new JPanel();
        edittestname.setSize(frame.getSize());
        edittestname.setLayout(null);
        
        name = new JTextField();
        name.setBounds(frame.getWidth()/2-50, frame.getHeight()/2-150, 300, 30);
        name.setText(currentTest.getTestName());
        edittestname.add(name);
        
        
        save = new JButton("Save");
        save.setBounds(frame.getWidth()/2 +150, frame.getHeight()/2-150, 300, 30);
        edittestname.add(save);
        
        
        save.addActionListener(new EditTestNameListener(this, c , currentTest, currentUser, name));
        
        currentPane = edittestname;
        frame.add(edittestname);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    
    public void RemoveTestTopics(User currentUser, Test currentTest){
         frame.setVisible(false);
        frame.remove(currentPane);
        JList testtopics;
        JButton save;
        ArrayList<Integer> topicIds = new ArrayList<Integer>();
                
                
        DefaultListModel listModel = new DefaultListModel();

        JPanel removetesttopics  = new JPanel();
        removetesttopics.setSize(frame.getSize());
        removetesttopics.setLayout(null);

        
        save = new JButton("Save");
        save.setBounds(frame.getWidth()/2 +10,frame.getHeight()/8 -20, 100, 30);
        removetesttopics.add(save);
        
        Topic[] topics = currentTest.getTopics();
        
        for(int i = 0; i < topics.length; i++){
            listModel.addElement(topics[i].getTopicName());
            topicIds.add(topics[i].getID());
        }
        
        testtopics = new JList(listModel);
        testtopics.setBounds(frame.getWidth()/2 ,frame.getHeight()/2 , 400, 400);
        testtopics.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        if(testtopics.getModel().getSize() - 1 >= 0){
            testtopics.setSelectionInterval(0, testtopics.getModel().getSize() -1);
        }
        
        removetesttopics.add(testtopics);
        
       
        save.addActionListener(new RemoveTestTopicsListener(this, c , currentTest, currentUser, testtopics, topicIds));
        currentPane = removetesttopics;
        frame.add(removetesttopics);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    
    
    
    
    
    public void RemoveTestQuestions(User currentUser, Test currentTest){
         frame.setVisible(false);
        frame.remove(currentPane);
        JList testquestions;
        JButton save;
        ArrayList<Integer> questionIds = new ArrayList<Integer>();
                
                
        DefaultListModel listModel = new DefaultListModel();

        JPanel removetestquestions  = new JPanel();
        removetestquestions.setSize(frame.getSize());
        removetestquestions.setLayout(null);

        
        save = new JButton("Save");
        save.setBounds(frame.getWidth()/2 +10,frame.getHeight()/8 -20, 100, 30);
        removetestquestions.add(save);
        
        Question[] questions = currentTest.getQuestions();
        
        for(int i = 0; i < questions.length; i++){
            listModel.addElement(questions[i].getText());
            questionIds.add(questions[i].getID());
        }
        
        testquestions = new JList(listModel);
        testquestions.setBounds(frame.getWidth()/2 ,frame.getHeight()/2 , 400, 400);
        testquestions.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        if(testquestions.getModel().getSize() - 1 >= 0){
            testquestions.setSelectionInterval(0, testquestions.getModel().getSize() -1);
        }
        
        removetestquestions.add(testquestions);
        
       
        save.addActionListener(new RemoveTestTopicsListener(this, c , currentTest, currentUser, testquestions, questionIds));
        currentPane = removetestquestions;
        frame.add(removetestquestions);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    
    public void AddTestTopics(User currentUser, Test currentTest){
        frame.setVisible(false);
        frame.remove(currentPane);
        ArrayList<Integer> idsOfTopics = new ArrayList<Integer>();
        Topic[] topicsInTest = currentTest.getTopics();
        DefaultListModel listModel = new DefaultListModel();

        JPanel addtesttopics  = new JPanel();
        addtesttopics.setSize(frame.getSize());
        addtesttopics.setLayout(null);
        
        
        
        Search s = new Search(c);
        
        try{
            ResultSet topicRS = s.Topics("");
            int count = 0;
            while(topicRS.next()){
                Topic topic = new Topic(c, topicRS.getInt(1));
                Boolean inTest = false;
                for(int i =0; i < topicsInTest.length; i++){
                    if(topic.getID() == topicsInTest[i].getID()){
                        inTest = true;
                    }
                        
                }
                if( !inTest){
                    idsOfTopics.add(topicRS.getInt(1));
               
                    listModel.addElement( topic.getTopicName());
                    
                }
            }
        }catch(SQLException sasd){
            listModel.addElement("No topics found");
        }
        
        
        
        JList topics = new JList(listModel);
        topics.setBounds(frame.getWidth()/2 ,frame.getHeight()/2 , 400, 400);
        topics.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        addtesttopics.add(topics);
        
        JButton save = new JButton("Save");
        save.setBounds(frame.getWidth()/2 +10,frame.getHeight()/8 -20, 100, 30);
        addtesttopics.add(save);
        
        JButton back = new JButton("Back");
        back.setBounds(frame.getWidth()/2 -300,frame.getHeight()/8 -20, 100, 30);
        addtesttopics.add(back);
        
        
        back.addActionListener(new BackToEditTestHomeListener(this, currentUser, currentTest));
        
        
        save.addActionListener(new AddTestTopicsListener(this, c , currentTest, currentUser, topics, idsOfTopics));
        currentPane = addtesttopics;
        frame.add(addtesttopics);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    
    public void AddTestQuestions(User currentUser, Test currentTest){
        frame.setVisible(false);
        frame.remove(currentPane);
        ArrayList<Integer> idsOfQuestions = new ArrayList<Integer>();
        Question[] questionsInTest = currentTest.getQuestions();
        
        Topic[] topicsInTest = currentTest.getTopics();
        DefaultListModel listModel = new DefaultListModel();

        JPanel addtestquestions  = new JPanel();
        addtestquestions.setSize(frame.getSize());
        addtestquestions.setLayout(null);
        
        
        
        Search s = new Search(c);
        
        try{
            for(int i = 0; i < topicsInTest.length; i++){
                ResultSet topicRS = s.QuestionsByTopic(topicsInTest[i].getID());
                
                while(topicRS.next()){
                    Topic topic = new Topic(c, topicRS.getInt(1));
                    Boolean inTest = false;
                    for(int j =0; j < questionsInTest.length; i++){
                        if(topic.getID() == questionsInTest[j].getID()){
                            inTest = true;
                        }

                    }
                    if( !inTest){
                        idsOfQuestions.add(topicRS.getInt(1));

                        listModel.addElement( topic.getTopicName());

                    }
                }
            }
        }catch(SQLException sasd){
            listModel.addElement("No questions found (might not have any topics)");
        }
        
        
        
        JList questions = new JList(listModel);
        questions.setBounds(frame.getWidth()/2 ,frame.getHeight()/2 , 400, 400);
        questions.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        addtestquestions.add(questions);
        
        JButton save = new JButton("Save");
        save.setBounds(frame.getWidth()/2 +10,frame.getHeight()/8 -20, 100, 30);
        addtestquestions.add(save);
        
        JButton back = new JButton("Back");
        back.setBounds(frame.getWidth()/2 -300,frame.getHeight()/8 -20, 100, 30);
        addtestquestions.add(back);
        
        
        back.addActionListener(new BackToEditTestHomeListener(this, currentUser, currentTest));
        save.addActionListener(new AddTestQuestionsListener(this, c , currentTest, currentUser, questions, idsOfQuestions));
        currentPane = addtestquestions;
        frame.add(addtestquestions);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    
}
