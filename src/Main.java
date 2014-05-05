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
    static JFrame containsBox;
    static boolean loading = true;
    public static void main(String[] args) {
        setupUI();

        new Thread(new Loading()).start();
        le.setupWC();
        loading = false;

        containsBox.setVisible(true);
        setupSC();
        setupWP();

    }

    static class Loading implements Runnable{

        @Override
        public void run() {
            JOptionPane.showMessageDialog(containsBox,
                    "Type away, enter to insert auto complete" +
                            "\nGive it a second to load",
                    "Controls",
                    JOptionPane.PLAIN_MESSAGE);
            while (loading){
                JOptionPane.showMessageDialog(containsBox,
                        "Loading...",
                        "Loading...",
                        JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    private static void setupSC() {
    }
    private static void setupWP() {
    }

    private static void setupUI(){
        JTextField box = new JTextField();


        containsBox = new JFrame();

        containsBox.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        containsBox.add(box);
        containsBox.setVisible(false);
        containsBox.setSize(300,100);
        containsBox.setLocation(200,200);

        le = new ListenEngine(box);
        //box.getDocument().addDocumentListener(le);
        box.addKeyListener(le);
    }

}
