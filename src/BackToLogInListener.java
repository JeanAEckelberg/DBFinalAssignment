/**
 *
 * @author Jean
 */


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BackToLogInListener implements ActionListener{
    private Window ref;
    
    public BackToLogInListener(Window ref){
        this.ref = ref;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        ref.LogIn();
    }
}
