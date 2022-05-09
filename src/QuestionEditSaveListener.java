/**
 *
 * @author Jean
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class QuestionEditSaveListener implements ActionListener{
    private Connection c;
    private JTextArea qText;
    private JTextField[] ansTexts;
    private JRadioButton[] ansButtons;
    private JLabel error;
    private User currentUser;
    private Question currentQuestion;
    private ArrayList<Topic> allTopics;
    private JList topics;

    public QuestionEditSaveListener(Connection c, JTextArea qText, JTextField[] ansTexts, JRadioButton[] ansButtons, JList topics, ArrayList<Topic> allTopics, JLabel error, Question currentQuestion, User currentUser) {
        this.c = c;
        this.qText = qText;
        this.ansTexts = ansTexts;
        this.ansButtons = ansButtons;
        this.topics = topics;
        this.allTopics = allTopics;
        this.error = error;
        this.currentUser = currentUser;
        this.currentQuestion = currentQuestion;
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
            
            currentQuestion.removeAllTopics(currentUser.getID());
            int[] indices = topics.getSelectedIndices();
            for(int i : indices){
                currentQuestion.addTopic(currentUser.getID(), allTopics.get(i).getID());
            }
            
            Answer[] answers = currentQuestion.getAnswers();
            
            for(int i = 0; i < answers.length; i++){
                currentQuestion.removeAnswer(currentUser.getID(), answers[i].getID());
                //answers[i].remove(currentUser.getID()); // comented for redundancy
            }
            
            for(int i = 0; i < ansTexts.length; i++){
                if(ansTexts[i].getText().equals("")) continue;
                currentQuestion.addAnswer( 
                        currentUser.getID(),
                        Answer.createAnswer(c, currentUser.getID(), ansTexts[i].getText()), 
                        ansButtons[i].isSelected());
            }
            if(currentQuestion.getCorrectAns() == null) throw new IllegalArgumentException();
            error.setText("Edit Saved Successfully!");
        } catch(IllegalArgumentException i){
            error.setText("Must add and select an answer or you are not authorized.");
        }catch(Exception f) {
            error.setText("Question Already Exists.");
        } finally {
            error.setVisible(true);
        }
    }
}
