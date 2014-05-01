import javax.swing.*;
import javax.swing.event.DocumentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Kyle Rosenthal
 * 4/28/14
 */
public class Main{
    static ListenEngine le;
    public static void main(String[] args) {
        setupUI();
        le.setupWC();
        setupSC();
        setupWP();

    }


    private static void setupSC() {
    }
    private static void setupWP() {
    }

    private static void setupUI(){
        JTextField box = new JTextField();


        JFrame containsBox = new JFrame();
        containsBox.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        containsBox.add(box);
        containsBox.setVisible(true);
        containsBox.setSize(300,100);
        containsBox.setLocation(200,200);

        le = new ListenEngine(box);
        //box.getDocument().addDocumentListener(le);
        box.addKeyListener(le);
    }

}
