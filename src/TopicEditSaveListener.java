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

public class TopicEditSaveListener implements ActionListener{
    private JTextField name;
    private JTextArea desc;
    private JLabel error;
    private Topic currentTopic;
    private User currentUser;
    private Connection c;

    public TopicEditSaveListener(Connection c, Topic currentTopic, JTextField name, JTextArea desc, JLabel error, User currentUser) {
        this.name = name;
        this.desc = desc;
        this.error = error;
        this.currentUser = currentUser;
        this.currentTopic = currentTopic;
        this.c = c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            currentTopic.setName(c, name.getText(),currentUser.getID());
            currentTopic.setDesc(c, desc.getText(),currentUser.getID());
            error.setText("Topic edited successfully!");
        } catch (IllegalArgumentException i){
            error.setText("You are not authorized to edit this topic!");
        } catch(SQLException f){
            error.setText("Topic name already exists!");
        } finally {
            error.setVisible(true);
        }
        
    }
    
    
}
