/**
 *
 * @author Jean
 */

import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class QuestionCreateListener implements ActionListener{
    private Connection c;
    private JTextArea qText;
    private JTextField[] ansTexts;
    private JRadioButton[] ansButtons;
    private JLabel error;
    private User currentUser;
    private ArrayList<Topic> allTopics;
    private JList topics;

    public QuestionCreateListener(Connection c, JTextArea qText, JTextField[] ansTexts, JRadioButton[] ansButtons, JList topics, ArrayList<Topic> allTopics, JLabel error, User currentUser) {
        this.c = c;
        this.qText = qText;
        this.ansTexts = ansTexts;
        this.ansButtons = ansButtons;
        this.topics = topics;
        this.allTopics = allTopics;
        this.error = error;
        this.currentUser = currentUser;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        try{
            boolean hasCorrectAnswer = false;
            for(int i = 0; i < ansTexts.length;i++){
                if(ansTexts[i].getText().equals("")) continue;
                if(!(ansButtons[i].isSelected())) continue;
                hasCorrectAnswer = true;
            }
            
            if(!hasCorrectAnswer) throw new IllegalArgumentException();
            
            Question q = new Question(c,Question.createQuestion(c, currentUser.getID(), qText.getText()));
            
            int[] indices = topics.getSelectedIndices();
            for(int i : indices){
                q.addTopic(currentUser.getID(), allTopics.get(i).getID());
            }
            for(int i = 0; i < ansTexts.length; i++){
                if(ansTexts[i].getText().equals("")) continue;
                q.addAnswer( 
                        currentUser.getID(),
                        Answer.createAnswer(c, currentUser.getID(), ansTexts[i].getText()), 
                        ansButtons[i].isSelected());
            }
            if(q.getCorrectAns() == null) throw new IllegalArgumentException();
            error.setText("Question Created!");
        } catch(IllegalArgumentException i){
            error.setText("Must add and select an answer.");
        }catch(Exception f) {
            error.setText("Question Already Exists.");
        } finally {
            error.setVisible(true);
        }
    }
    
    
}
