/**
 *
 * @author Jean
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JLabel;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTextArea;

public class TopicEditRemoveListener implements ActionListener{
    private User currentUser;
    private Connection c;
    private Topic currentTopic;
    private JLabel error;

    public TopicEditRemoveListener(Connection c, Topic currentTopic, JLabel error, User currentUser) {
        this.currentUser = currentUser;
        this.c = c;
        this.currentTopic = currentTopic;
        this.error = error;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            currentTopic.remove(currentUser.getID());
            error.setText("Topic Deleted.");
            currentTopic = null;
        } catch (IllegalArgumentException i){
            error.setText("You are not authorized to remove this topic!");
        } catch (SQLException f){
            error.setText("Topic does not exist!");
        }
        
        error.setVisible(true);
    }
}
