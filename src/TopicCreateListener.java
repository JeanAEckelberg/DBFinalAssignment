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

public class TopicCreateListener implements ActionListener{
    private Connection c;
    private User currentUser;
    private JTextField name;
    private JTextArea desc;
    private JLabel error;
    
    public TopicCreateListener(
            Connection c, 
            JTextField name, 
            JTextArea desc, 
            JLabel error, 
            User currentUser){
        this.c = c;
        this.name = name;
        this.desc = desc;
        this.error = error;
        this.currentUser = currentUser;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        try{
            Topic.createTopic(c, name.getText(), desc.getText(), currentUser.getID());
            error.setText("Topic Created!");
        } catch (IllegalArgumentException a){
            error.setText("You are not authorized!");
        } catch(SQLException f) {
            error.setText("Topic Already Exists");
        } finally {
            error.setVisible(true);
        }
    }
    
    
}
