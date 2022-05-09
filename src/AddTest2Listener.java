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
    private ArrayList<Integer> selectedQuestionIds;
    private ArrayList<Integer> idsOfQuestions;
    private JList questionJLIST;
    private ArrayList<Integer> idsOfTopics;
    public AddTest2Listener(Window ref, Connection c,  User currentUser, String testname,ArrayList<Integer>  idsOfTopics, JList questionJLIST, ArrayList<Integer> idsOfQuestions){
        this.c = c;
        this.testname = testname;
        this.ref = ref;
        this.currentUser = currentUser;
        this.idsOfTopics = idsOfTopics;
        //this.selectedQuestionIds = questionJLIST;
        this.questionJLIST = questionJLIST;
        this.idsOfQuestions = idsOfQuestions;
       
    }
    
    @Override
   public void actionPerformed(ActionEvent e) {
        this.selectedQuestionIds = new ArrayList<Integer>();
        int[] templist = this.questionJLIST.getSelectedIndices();
        for( int i = 0; i < templist.length; i++){
            this.selectedQuestionIds.add(this.idsOfQuestions.get(templist[i]));
        }
        ArrayList<Question> questionList = new ArrayList<Question>();
        for( int i = 0; i < this.selectedQuestionIds.size(); i++){
            questionList.add(new Question(c, this.selectedQuestionIds.get(i)));
        }
        try{
            
            Test.createTest(c, questionList, this.currentUser.getID(), this.testname);
            Test newTest = new Test(c, this.testname);
            newTest.
        }catch(SQLException se){
            System.out.println("couldn't create test");
        }
        
        
        ref.Dashboard(currentUser);
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