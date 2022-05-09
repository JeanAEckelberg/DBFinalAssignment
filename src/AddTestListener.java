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


public class AddTestListener implements ActionListener{

    private JTextField testname;
    private int topicId;
    private User currentUser;
    private Connection c;
    private Window ref;
    private ArrayList<Integer> idsOfTopics;
    private JList topics;
    public AddTestListener(Window ref, Connection c, JTextField testname, User currentUser, ArrayList<Integer> idsOfTopics,JList topics  ){
        
        this.c = c;
        this.testname = testname;
        this.ref = ref;
        this.currentUser = currentUser;
        this.idsOfTopics = idsOfTopics;
        this.topics = topics;
        
       
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            this.topicId = this.idsOfTopics.get(this.topics.getSelectedIndex());
        }catch(Exception ee){
            System.err.print("Topic not selected");
            ref.AddTest1(currentUser);
            this.topicId = -1;
            
        }
        ref.AddTest2(currentUser, testname.getText(), topicId);
        /*
        User currentUser;
        try{
            T
            currentUser = new User(c, user.getText(), pass.getText());
            ref.Dashboard(currentUser);
        } catch (Exception f){
            pass.selectAll();
            errorMsg.setVisible(true);
        }
*/
        
    
    }
    
}
