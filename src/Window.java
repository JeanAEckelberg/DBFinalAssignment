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
        enter.setBounds(frame.getWidth()/2-50, frame.getHeight()/2, 100, 30);
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
        error.setBounds(frame.getWidth()/2-90, frame.getHeight()/2-250, 400, 30);
        error.setVisible(false);
        createTopic.add(error);
        
        name = new JTextField();
        name.setBounds(frame.getWidth()/2+100, frame.getHeight()/2-200, 300, 30);
        createTopic.add(name);
        
        description = new JTextArea();
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setBounds(frame.getWidth()/2+100, frame.getHeight()/2-100, 300, 100);
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
        question.setBounds(frame.getWidth()/2-400, frame.getHeight()/2-250, 100, 100);
        createQuestion.add(question);
        
        for(int i = 0; i < ans.length; i++){
            ans[i] = new JLabel("Set Answer: ");
            ans[i].setBounds(frame.getWidth()/2-150, frame.getHeight()/2-100+(i*50), 100, 30);
            createQuestion.add(ans[i]);
        }
        
        error = new JLabel("Question already exists!");
        error.setBounds(frame.getWidth()/2-90, frame.getHeight()/2-400, 400, 30);
        error.setVisible(false);
        createQuestion.add(error);
        
        
        try{
           rs = Search.Questions("");
           while(rs.next()){
               allTopics.add(new Topic(c, rs.getInt(1)));
           }
        } catch (SQLException e) {
            error.setText("Error fetching topics!");
            error.setVisible(true);
        }
        
        names = new String[allTopics.size()];
        
        try{
            for(int i = 0; i < names.length; i++)
                names[i] = allTopics.get(i).getName(c);
        } catch (SQLException e) {
            error.setText("Error fetching topics!");
            error.setVisible(true);
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
        
        topics.setBounds(frame.getWidth()/2-90, frame.getHeight()/2-400, 100, 30);
        createQuestion.add(topics);
        
        questionText = new JTextArea();
        questionText.setLineWrap(true);
        questionText.setWrapStyleWord(true);
        questionText.setBounds(frame.getWidth()/2-150, frame.getHeight()/2-250, 300, 100);
        createQuestion.add(questionText);
        
        for(int i = 0; i < ansText.length;i++){
            ansText[i] = new JTextField();
            ansText[i].setBounds(frame.getWidth()/2+150, frame.getHeight()/2-150+(i*50), 100, 30);
            createQuestion.add(ansText[i]);
        }
        
        for(int i = 0; i < ansButtons.length;i++){
            ansButtons[i] = new JRadioButton();
            ansButtons[i].setBounds(frame.getWidth()/2, frame.getHeight()/2-150+(i*50), 100, 30);
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
        try{
            name.setText(currentTopic.getName(c));
        } catch (SQLException e){
            name.setText("CRITICAL ERROR - TOPIC DNE");
        }
        editTopic.add(name);
        
        description = new JTextArea();
        description.setBounds(frame.getWidth()/2-50, frame.getHeight()/2-100, 300, 100);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        try{
            description.setText(currentTopic.getDesc(c));
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
           rs = Search.Questions("");
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
                names[i] = allTopics.get(i).getName(c);
            currentTopics = currentQuestion.getTopics(c, currentUser.getID());
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
        topics.setBounds(frame.getWidth()/2-90, frame.getHeight()/2-400, 100, 30);
        editQuestion.add(topics);
        
        questionText = new JTextArea();
        questionText.setLineWrap(true);
        questionText.setWrapStyleWord(true);
        questionText.setBounds(frame.getWidth()/2-150, frame.getHeight()/2-250, 300, 100);
        questionText.setText(currentQuestion.getText());
        editQuestion.add(questionText);
        
        int answerSize = 0;
        Answer[] answers = new Answer[4];
        try{
            answers = currentQuestion.getAnswers(c);
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
            ansButtons[i].setBounds(frame.getWidth()/2-150, frame.getHeight()/2-150+(i*50), 100, 30);
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
}
