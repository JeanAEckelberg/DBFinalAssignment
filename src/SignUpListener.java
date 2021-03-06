/**
 *
 * @author Jean
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignUpListener implements ActionListener{
    private JTextField user;
    private JPasswordField pass;
    private JLabel errorMsg;
    private Connection c;

    
    public SignUpListener(Connection c, JLabel error, JTextField user, JPasswordField pass){
        this.c = c;
        this.user = user;
        this.pass = pass;

        errorMsg = error;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            User.createUser(c, user.getText(), pass.getText());
            errorMsg.setText("Account created successfully.");
        } catch (Exception f){
            pass.selectAll();
            errorMsg.setText("User already exists.");
        } finally {
            errorMsg.setVisible(true);
        }
    }
}
