/**
 *
 * @author Jean
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

public class TopicEditCancelListener implements ActionListener{
    private JLabel error;
    
    public TopicEditCancelListener(JLabel error){ this.error = error; }

    @Override
    public void actionPerformed(ActionEvent e) {
        error.setText("Edit cancelled, press back to exit!");
        error.setVisible(true);
    }
    
    
    
}
