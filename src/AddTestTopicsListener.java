
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
public class AddTestTopicsListener implements ActionListener{
    private Window ref;
    private Connection c;
    private Test currentTest;
   
    private User currentUser;
    private JList topics;
    private ArrayList<Integer> topicIds;
    public AddTestTopicsListener(Window ref, Connection c, Test currentTest, User currentUser, JList topics, ArrayList<Integer> topicIds){
        this.ref = ref;
        this.c = c;
        this.currentTest = currentTest;
        this.currentUser = currentUser;
        this.topicIds = topicIds;
        this.topics = topics;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            ArrayList<Topic> selectedTopics = new ArrayList<Topic>();
            
            
            int[] temp = topics.getSelectedIndices();
            
            for(int i = 0; i < temp.length; i++){
                selectedTopics.add(new Topic(c, topicIds.get(temp[i])));
            }
            
            for( Topic topic : selectedTopics){
                currentTest.addTopicToTest(topic, currentUser.getID());
            }
        }catch(SQLException sqe){
            System.err.println("sqlerror");
        }
            
            
        
    }
    
}
