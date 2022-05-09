/**
 *
 * @author Jean
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JLabel;

public class QuestionEditRemoveListener implements ActionListener{
    private User currentUser;
    private Connection c;
    private Question currentQuestion;
    private JLabel error;

    public QuestionEditRemoveListener(Connection c, Question currentQuestion, JLabel error, User currentUser) {
        this.currentUser = currentUser;
        this.c = c;
        this.currentQuestion = currentQuestion;
        this.error = error;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            currentQuestion.remove(c, currentUser.getID());
            error.setText("Topic Deleted.");
            currentQuestion = null;
        } catch (IllegalArgumentException i){
            error.setText("You are not authorized to remove this topic!");
        } catch (SQLException f){
            error.setText("Topic does not exist!");
        }
        
        error.setVisible(true);
    }
}
