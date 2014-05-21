import javax.swing.*;
import javax.swing.event.DocumentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Kyle Rosenthal
 * 4/28/14
 */

/**
 * sets up gui and various databases
 */
public class Main{
    static ListenEngine le;
    static JFrame containsBox;
    static JTextField box;
    static boolean loading = true;
    public static void main(String[] args) {
        setupUI();

        new Thread(new Instructions()).start();

        JProgressBar jpb = new JProgressBar();
        jpb.setIndeterminate(true);

        containsBox.add(jpb);
        containsBox.setTitle("Loading...");
        le.setupWC();
        containsBox.remove(jpb);
        containsBox.add(box);
        containsBox.setVisible(false);
        containsBox.setVisible(true); //refresh
        containsBox.requestFocus();
        containsBox.setTitle("Word Predict 2000");




        setupSC();
        setupWP();

    }

    static class Instructions implements Runnable{

        @Override
        public void run() {

            JOptionPane.showMessageDialog(containsBox,
                    "Type away, enter to insert auto complete",
                    "Controls",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }

    private static void setupSC() {
    }
    private static void setupWP() {
    }

    private static void setupUI(){
        box = new JTextField();


        containsBox = new JFrame();

        containsBox.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //containsBox.add(box);
        containsBox.setVisible(true);
        containsBox.setSize(400,80);
        containsBox.setLocation(200,200);
        le = new ListenEngine(box);
        box.addKeyListener(le);
    }

}
