/**
 *
 * @author Jean
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.*;

public class LogInListener implements ActionListener{

    private JTextField user;
    private JPasswordField pass;
    private JLabel errorMsg;
    private Connection c;
    private Window ref;
    
    public LogInListener(Window ref, Connection c, JLabel error, JTextField user, JPasswordField pass){
        this.c = c;
        this.user = user;
        this.pass = pass;
        this.ref = ref;
        errorMsg = error;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        User currentUser;
        try{
            currentUser = new User(c, user.getText(), pass.getText());
            ref.Dashboard(currentUser);
        } catch (Exception f){
            pass.selectAll();
            errorMsg.setVisible(true);
        }
        
    }
    
    
}
