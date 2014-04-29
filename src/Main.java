import javax.swing.*;
import javax.swing.event.DocumentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Kyle Rosenthal
 * 4/28/14
 */
public class Main{
    public static void main(String[] args) {

        JTextField box = new JTextField();


        JFrame containsBox = new JFrame();

        containsBox.add(box);
        containsBox.setVisible(true);
        containsBox.setSize(300,100);

        containsBox.setLocation(200,200);

        ListenEngine le = new ListenEngine();
        box.addKeyListener(le);
    }

}
