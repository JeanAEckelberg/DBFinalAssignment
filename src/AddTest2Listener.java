/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author carso
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;


public class AddTest2Listener implements ActionListener{

    private String testname;
    private int topicId;
    private User currentUser;
    private Connection c;
    private Window ref;
    private JList questionsJLIST;
    public AddTest2Listener(Window ref, Connection c,  User currentUser, String testname, int topicId, JList questionJLIST){
        this.c = c;
        this.testname = testname;
        this.ref = ref;
        this.currentUser = currentUser;
        this.topicId = topicId;
        this.questionsJLIST = questionJLIST;
       
    }
    
    @Override
   public void actionPerformed(ActionEvent e) {
        
        ArrayList<Question> questionList = new ArrayList<Question>();
        //QuestionList
            
            
            
            //Test t = new Test(c,Question.createQuestion(c, currentUser.getID(), qText.getText()));
            /*
            for(int i = 0; i < ansTexts.length; i++){
                if(ansTexts[i].getText().equals("")) continue;
                q.addAnswer(c, 
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
*/
    }
    
    
}