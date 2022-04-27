/**
 *
 * @author Jean, Greg, Carson
 */

import java.awt.Container;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Window {
    JFrame frame;
    Container currentPane;
    ArrayList<Container> panes;
    
    public Window(String title, int width, int height){
        frame = new JFrame(title);
        frame.setBounds(200, 200, width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        currentPane = frame.getContentPane();
        
        
        
        //to be commented out after testing is completed
        frame.setVisible(true);
    }
    
}
