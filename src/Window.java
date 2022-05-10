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
        
        JButton signUp = new JButton("SignUp");
        signUp.setBounds(frame.getWidth()/2-50, frame.getHeight()/2+50, 100, 30);
        logIn.add(signUp);
        
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
        
        Window temp = this;
        
        enter.addActionListener(new LogInListener(this,c,error,name,key));
        signUp.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                SignUp();
            }
            
        });
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

        back.setBounds(200, frame.getHeight()-300, 100, 30);

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
        
        enter.addActionListener(new SignUpListener(c,error,name,key));

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
        createTopic.addActionListener(new CreateTopicListener(this, currentUser) );
        createTest.addActionListener( new CreateTestListener(this, currentUser) );
        createQuestion.addActionListener(new CreateQuestionListener(this , currentUser) );
        exitbutton.addActionListener(new BackToLogInListener(this));
        currentPane = dashboard;
        frame.add(dashboard);
        frame.setLayout(null);
        frame.setVisible(true);
        
    }
    
    public void Search(JTextField text ){
        frame.setVisible(false);
        frame.remove(currentPane);
    }
    
    public void CreateTopic(User currentUser){
        frame.setVisible(false);
        frame.remove(currentPane);
        JLabel topic, desc, error;
        JTextField name;
        JTextArea description;
        
        JPanel createTopic = new JPanel();
        createTopic.setSize(frame.getSize());
        createTopic.setLayout(null);
        
        JButton enter = new JButton("Create");
        enter.setBounds(frame.getWidth()/2-100, frame.getHeight()/2+100, 100, 30);
        createTopic.add(enter);
        
        JButton back = new JButton("Back");
        back.setBounds(200, frame.getHeight()-300, 100, 30);
        createTopic.add(back);
        
        topic = new JLabel("Set Topic Name: ");
        topic.setBounds(frame.getWidth()/2-200, frame.getHeight()/2-200, 100, 30);
        createTopic.add(topic);
        
        desc = new JLabel("Set Topic Description: ");
        desc.setBounds(frame.getWidth()/2-200, frame.getHeight()/2-100, 200, 100);
        createTopic.add(desc);
        
        error = new JLabel("Name already in use!");
        error.setBounds(frame.getWidth()/2-90, frame.getHeight()/2-300, 400, 30);
        error.setVisible(false);
        createTopic.add(error);
        
        name = new JTextField();
        name.setBounds(frame.getWidth()/2, frame.getHeight()/2-200, 300, 30);
        createTopic.add(name);
        
        description = new JTextArea();
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setBounds(frame.getWidth()/2, frame.getHeight()/2-100, 300, 100);
        createTopic.add(description);
        
        enter.addActionListener(new TopicCreateListener(c,name,description,error,currentUser));
        back.addActionListener(new BackToDashboardListener(this, currentUser));
        currentPane = createTopic;
        frame.add(createTopic);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    
    public void CreateQuestion(User currentUser){
        frame.setVisible(false);
        frame.remove(currentPane);
        Search s = new Search(c);
        JLabel question, error;
        JLabel[] ans = new JLabel[4];
        
        JTextArea questionText;
        JTextField[] ansText = new JTextField[4];
        
        JRadioButton[] ansButtons = new JRadioButton[4];
        ButtonGroup group = new ButtonGroup();
        
        JList topics;
        ResultSet rs;
        ArrayList<Topic> allTopics = new ArrayList<>();
        String[] names;
        
        JPanel createQuestion = new JPanel();
        createQuestion.setSize(frame.getSize());
        createQuestion.setLayout(null);
        
        JButton enter = new JButton("Create");
        enter.setBounds(frame.getWidth()-400, frame.getHeight()-300, 100, 30);
        createQuestion.add(enter);
        
        JButton back = new JButton("Back");
        back.setBounds(200, frame.getHeight()-300, 100, 30);
        createQuestion.add(back);
        
        question = new JLabel("Set Question Text: ");
        question.setBounds(frame.getWidth()/2-300, frame.getHeight()/2-250, 200, 100);
        createQuestion.add(question);
        
        for(int i = 0; i < ans.length; i++){
            ans[i] = new JLabel("Set Answer: ");
            ans[i].setBounds(frame.getWidth()/2-300, frame.getHeight()/2-100+(i*50), 100, 30);
            createQuestion.add(ans[i]);
        }
        
        error = new JLabel("Question already exists!");
        error.setBounds(frame.getWidth()/2-90, frame.getHeight()/2-425, 400, 30);
        error.setVisible(false);
        createQuestion.add(error);
        
        
        try{
            rs = s.Topics("");
            while(rs.next())
                allTopics.add(new Topic(c, rs.getInt(1)));
        } catch (SQLException e) {
            error.setText("Error fetching topics!");
            error.setVisible(true);
        }
        
        names = new String[allTopics.size()];
        
            for(int i = 0; i < names.length; i++)
                names[i] = allTopics.get(i).getName();
        
        topics = new JList<>(names);
        
        topics.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1){
                if (super.isSelectedIndex(index0))
                    super.removeSelectionInterval(index0, index1);
                else
                    super.addSelectionInterval(index0, index1);
            }
        });
        
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(topics);
        topics.setLayoutOrientation(JList.VERTICAL);
        scrollPane.setBounds(frame.getWidth()/2-150, frame.getHeight()/2-400, 300, 100);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        createQuestion.add(scrollPane);
        
        questionText = new JTextArea();
        questionText.setLineWrap(true);
        questionText.setWrapStyleWord(true);
        questionText.setBounds(frame.getWidth()/2-150, frame.getHeight()/2-250, 300, 100);
        createQuestion.add(questionText);
        
        for(int i = 0; i < ansText.length;i++){
            ansText[i] = new JTextField();
            ansText[i].setBounds(frame.getWidth()/2-100, frame.getHeight()/2-100+(i*50), 300, 30);
            createQuestion.add(ansText[i]);
        }
        
        for(int i = 0; i < ansButtons.length;i++){
            ansButtons[i] = new JRadioButton();
            ansButtons[i].setBounds(frame.getWidth()/2-150, frame.getHeight()/2-100+(i*50), 30, 30);
            group.add(ansButtons[i]);
            createQuestion.add(ansButtons[i]);
        }
        ansButtons[0].setSelected(true);
        
        enter.addActionListener(new QuestionCreateListener(c,questionText,ansText,ansButtons,topics,allTopics,error,currentUser));
        back.addActionListener(new BackToDashboardListener(this, currentUser));
        currentPane = createQuestion;
        frame.add(createQuestion);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    
    public void EditTopic(User currentUser, Topic currentTopic){
        frame.setVisible(false);
        frame.remove(currentPane);
        JTextField name;
        JTextArea description;
        JLabel error;
        
        
        JPanel editTopic = new JPanel();
        editTopic.setSize(frame.getSize());
        editTopic.setLayout(null);
        
        JButton enter = new JButton("Save");
        enter.setBounds(frame.getWidth()-400, frame.getHeight()-300, 100, 30);
        editTopic.add(enter);
        
        JButton remove = new JButton("Remove");
        remove.setBounds(frame.getWidth()-600, frame.getHeight()-300, 100, 30);
        editTopic.add(enter);
        
        JButton cancel = new JButton("Cancel");
        cancel.setBounds(frame.getWidth()-800, frame.getHeight()-300, 100, 30);
        editTopic.add(enter);
        
        JButton back = new JButton("Back");
        back.setBounds(200, frame.getHeight()-300, 100, 30);
        editTopic.add(back);
        
        
        name = new JTextField();
        name.setBounds(frame.getWidth()/2-50, frame.getHeight()/2-150, 300, 30);
        // try{                                           Kept for in case null poiter or other issue
            name.setText(currentTopic.getName());
        //} catch (SQLException e){
        //    name.setText("CRITICAL ERROR - TOPIC DNE");
        //}
        editTopic.add(name);
        
        description = new JTextArea();
        description.setBounds(frame.getWidth()/2-50, frame.getHeight()/2-100, 300, 100);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        try{
            description.setText(currentTopic.getDesc());
        } catch (SQLException e){
            description.setText("CRITICAL ERROR - TOPIC DNE");
        }
        editTopic.add(description);
        
        
        error = new JLabel("Question already exists!");
        error.setBounds(frame.getWidth()/2-90, frame.getHeight()/2-400, 400, 30);
        error.setVisible(false);
        editTopic.add(error);
        
        
        
        enter.addActionListener(new TopicEditSaveListener(c,currentTopic,name,description,error,currentUser));
        remove.addActionListener(new TopicEditRemoveListener(c,currentTopic,error,currentUser));
        cancel.addActionListener(new CancelListener(error));
        back.addActionListener(new BackToDashboardListener(this, currentUser));
        
        currentPane = editTopic;
        frame.add(editTopic);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    
    public void EditQuestion(User currentUser, Question currentQuestion){
        frame.setVisible(false);
        frame.remove(currentPane);
        
        Search s = new Search(c);
        
        JTextArea questionText;
        JTextField[] ansText = new JTextField[4];
        
        JRadioButton[] ansButtons = new JRadioButton[4];
        ButtonGroup group = new ButtonGroup();
        
        JLabel error;
        
        JList topics;
        ResultSet rs;
        ArrayList<Topic> allTopics = new ArrayList<>();
        String[] names;
        
        JPanel editQuestion = new JPanel();
        editQuestion.setSize(frame.getSize());
        editQuestion.setLayout(null);
        
        JButton enter = new JButton("Save");
        enter.setBounds(frame.getWidth()-400, frame.getHeight()-300, 100, 30);
        editQuestion.add(enter);
        
        JButton remove = new JButton("Remove");
        remove.setBounds(frame.getWidth()-600, frame.getHeight()-300, 100, 30);
        editQuestion.add(enter);
        
        JButton cancel = new JButton("Cancel");
        cancel.setBounds(frame.getWidth()-800, frame.getHeight()-300, 100, 30);
        editQuestion.add(enter);
        
        JButton back = new JButton("Back");
        back.setBounds(200, frame.getHeight()-300, 100, 30);
        editQuestion.add(back);
        
        
        
        error = new JLabel("Question already exists!");
        error.setBounds(frame.getWidth()/2-90, frame.getHeight()/2+300, 400, 30);
        error.setVisible(false);
        editQuestion.add(error);
        
        // topics multiselect JList
        try{
           rs = s.Topics("");
           while(rs.next()){
               allTopics.add(new Topic(c, rs.getInt(1)));
           }
        } catch (SQLException e) {
            error.setText("Error fetching topics!");
            error.setVisible(true);
        }
        
        names = new String[allTopics.size()];
        Topic[] currentTopics = new Topic[0];
        try{
            for(int i = 0; i < names.length; i++)
                names[i] = allTopics.get(i).getName();
            currentTopics = currentQuestion.getTopics();
        } catch (SQLException e) {
            error.setText("Error fetching topics!");
            error.setVisible(true);
        }
         
        int[] currentTopicIndicies = new int[currentTopics.length];
        int counter = 0;
        for(int i = 0; i < allTopics.size(); i++){
            for(int j = 0; j < currentTopics.length; j++){
                if(allTopics.get(i).getID() != currentTopics[i].getID()) continue;
                
                currentTopicIndicies[counter++] = i;
            }
        }
        
        topics = new JList<>(names);
        
        topics.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1){
                if (super.isSelectedIndex(index0))
                    super.removeSelectionInterval(index0, index1);
                else
                    super.addSelectionInterval(index0, index1);
            }
        });
        
        topics.setSelectedIndices(currentTopicIndicies);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(topics);
        topics.setLayoutOrientation(JList.VERTICAL);
        scrollPane.setBounds(frame.getWidth()/2-150, frame.getHeight()/2-400, 300, 100);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        editQuestion.add(scrollPane);

        questionText = new JTextArea();
        questionText.setLineWrap(true);
        questionText.setWrapStyleWord(true);
        questionText.setBounds(frame.getWidth()/2-150, frame.getHeight()/2-250, 300, 100);
        questionText.setText(currentQuestion.getText());
        editQuestion.add(questionText);
        
        int answerSize = 0;
        Answer[] answers = new Answer[4];
        try{
            answers = currentQuestion.getAnswers();
            answerSize = answers.length;
        } catch(SQLException e){
            error.setText("Error fetching answers!");
            error.setVisible(true);
        }
        
        for(int i = 0; i < ansText.length;i++){
            ansText[i] = new JTextField();
            ansText[i].setBounds(frame.getWidth()/2, frame.getHeight()/2-150+(i*50), 100, 30);
            editQuestion.add(ansText[i]);
            if(i >= answerSize) continue;
            ansText[i].setText(answers[i].getText());
            
        }
        
        for(int i = 0; i < ansButtons.length;i++){
            ansButtons[i] = new JRadioButton();
            ansButtons[i].setBounds(frame.getWidth()/2-150, frame.getHeight()/2-150+(i*50), 30, 30);
            group.add(ansButtons[i]);
            editQuestion.add(ansButtons[i]);
        }
        
        
        
        
        enter.addActionListener(new QuestionEditSaveListener(c,questionText,ansText,ansButtons,topics,allTopics,error,currentQuestion,currentUser));
        remove.addActionListener(new QuestionEditRemoveListener(c,currentQuestion,error,currentUser));
        cancel.addActionListener(new CancelListener(error));
        back.addActionListener(new BackToDashboardListener(this, currentUser));
        
        currentPane = editQuestion;
        frame.add(editQuestion);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    
    public void CreateTest(User currentUser){
        
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
                listModel.addElement( topic.getName());
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
            listModel.addElement(topics[i].getName());
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
               
                    listModel.addElement( topic.getName());
                    
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

                        listModel.addElement( topic.getName());

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
