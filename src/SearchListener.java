
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author carso
 */
public class SearchListener implements ActionListener{
    private JTextField query;
   
    private Connection c;
    private Window ref;
    
    public SearchListener(Window ref, Connection c, JTextField query){
        this.c = c;
        this.query = query;
        this.ref = ref;
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        try{
            
        } catch (Exception f){
            
        } finally {
           
        }
    }
}