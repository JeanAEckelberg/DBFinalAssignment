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
public class CreateQuestionListener implements ActionListener{
   
   
  
    private Window ref;
    private User currentUser;
    
    public CreateQuestionListener(Window ref, User currentUser){
        

        this.ref = ref;
        this.currentUser = currentUser;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
            ref.CreateQuestion(currentUser);
    }
}