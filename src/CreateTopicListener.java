
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.JTextField;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author carso
 */
public class CreateTopicListener implements ActionListener{
   
   
   
    private Window ref;
    private User currentUser;
    
    public CreateTopicListener(Window ref, User u1){
       

        this.ref = ref;
        currentUser = u1;
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
       ref.CreateTopic(currentUser);
    }
}