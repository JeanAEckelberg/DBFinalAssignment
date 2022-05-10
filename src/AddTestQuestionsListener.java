
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JList;
import javax.swing.JTextField;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author carso
 */
public class AddTestQuestionsListener implements ActionListener{
    private Window ref;
    private Connection c;
    private Test currentTest;
   
    private User currentUser;
    private JList questions;
    private ArrayList<Integer> questionIds;
    public AddTestQuestionsListener(Window ref, Connection c, Test currentTest, User currentUser, JList questions, ArrayList<Integer> questionIds){
        this.ref = ref;
        this.c = c;
        this.currentTest = currentTest;
        this.currentUser = currentUser;
        this.questionIds = questionIds;
        this.questions = questions;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            ArrayList<Question> selectedQuestions = new ArrayList<Question>();
            
            
            int[] temp = questions.getSelectedIndices();
            
            for(int i = 0; i < temp.length; i++){
                selectedQuestions.add(new Question(c, questionIds.get(temp[i])));
            }
            
            for( Question question : selectedQuestions){
                currentTest.addQuestionToTest(question.getID(), currentUser.getID());
            }
        }catch(SQLException sqe){
            System.err.println("sqlerror");
        }catch(IllegalArgumentException iae){
            System.err.println("Not authorized");
        }
            
        ref.EditTestHome(currentUser, currentTest);
        
    }
    
}
