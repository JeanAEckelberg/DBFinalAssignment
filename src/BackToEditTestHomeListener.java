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

import javax.swing.JLabel;

public class BackToEditTestHomeListener implements ActionListener{
    private Window ref;
    private User currentUser;
    private Test currentTest;
    public BackToEditTestHomeListener(Window ref, User currentUser, Test currentTest){
        this.ref = ref;
        this.currentUser = currentUser;
        this.currentTest = currentTest;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ref.EditTestHome(this.currentUser, this.currentTest);
    }
    
    
    
}
