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

public class BackToDashboardListener implements ActionListener{
    private Window ref;
    private User currentUser;
    public BackToDashboardListener(Window ref, User currentUser){
        this.ref = ref;
        this.currentUser = currentUser;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        ref.Dashboard(currentUser);
    }
}

