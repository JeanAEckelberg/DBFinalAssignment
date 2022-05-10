/**
 *
 * @author Jean
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

public class CancelListener implements ActionListener{
    private JLabel error;
    
    public CancelListener(JLabel error){ this.error = error; }

    @Override
    public void actionPerformed(ActionEvent e) {
        error.setText("Edit cancelled, press back to exit.");
        error.setVisible(true);
    }
    
    
    
}
