
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
    
    public CreateTopicListener(Window ref){
       

        this.ref = ref;
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
       //ref.AddTopic();
    }
}